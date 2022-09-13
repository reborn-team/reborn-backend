package com.reborn.reborn.service;

import com.reborn.reborn.dto.FileDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocalUploadService implements UploadService {

    @Value("${file.upload.directory}")
    private String directory;

    @Override
    public List<FileDto> uploadFile(List<MultipartFile> multipartFile) {
        List<FileDto> files = new ArrayList<>();
        for (MultipartFile file : multipartFile) {
            String originFileName = file.getOriginalFilename();
            String uploadFileName = createFileName(originFileName);
            try {
                file.transferTo(new File(getFullPath(uploadFileName)));
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

    private String getFullPath(String uploadFileName) {
        return directory + uploadFileName;
    }
}
