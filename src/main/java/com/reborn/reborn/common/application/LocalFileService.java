package com.reborn.reborn.common.application;

import com.reborn.reborn.common.presentation.dto.FileDto;
import com.reborn.reborn.common.exception.FileDownloadException;
import com.reborn.reborn.common.exception.FileUploadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Profile("local")
@Service
@Slf4j
public class LocalFileService implements FileService {

    @Value("${file.upload.directory}")
    private String directory;

    @Override
    public List<FileDto> uploadFile(List<MultipartFile> multipartFile) {
        List<FileDto> files = new ArrayList<>();
        multipartFile.forEach(file -> {
            String originFileName = file.getOriginalFilename();
            String uploadFileName = createUploadFileName(originFileName);
            String fullPath = getFullPath(uploadFileName);

            try {
                file.transferTo(new File(fullPath));
            } catch (IOException e) {
                throw new FileUploadException(e.getMessage());
            }

            files.add(new FileDto(originFileName, uploadFileName));
        });
        return files;
    }

    @Override
    public boolean deleteFile(String uploadFilename) {
        File file = new File(getFullPath(uploadFilename));
        return file.delete();
    }

    @Override
    public String getFullPath(String uploadFileName) {
        return directory + uploadFileName;
    }

    @Override
    public Resource downloadFile(String filename) {
        try {
            return new UrlResource("file:" + getFullPath(filename));
        } catch (Exception e) {
            throw new FileDownloadException("파일 다운로드에 실패했습니다.");
        }
    }
}
