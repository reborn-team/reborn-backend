package com.reborn.reborn.service;

import com.reborn.reborn.dto.FileDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocalFileService implements FileService {

    @Value("${file.upload.directory}")
    private String directory;

    @Override
    public List<FileDto> uploadFile(List<MultipartFile> multipartFile) {
        List<FileDto> files = new ArrayList<>();
        for (MultipartFile file : multipartFile) {
            String originFileName = file.getOriginalFilename();
            String uploadFileName = createFileName(originFileName);
            String fullPath = getFullPath(uploadFileName);
            try {
                file.transferTo(new File(fullPath));
            } catch (IOException e) {
                //TODO 예외처리 해야함
                throw new RuntimeException(e);
            }
            files.add(new FileDto(originFileName, uploadFileName));
        }
        return files;
    }

    @Override
    public void deleteFile(String uploadFilename) {
        File file = new File(getFullPath(uploadFilename));
        file.delete();
    }
    @Override
    public String getFullPath(String uploadFileName) {
        return directory + uploadFileName;
    }
}
