package com.reborn.reborn.service;

import com.reborn.reborn.dto.FileDto;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

public interface FileService {

    List<FileDto> uploadFile(List<MultipartFile> multipartFile);

    boolean deleteFile(String fileName);

    String getFullPath(String uploadFileName);

<<<<<<< HEAD

=======
    Resource downloadFile(String filename);
>>>>>>> 113f9386f9f64211c2345b78b02f41d117e6e735

    default String createUploadFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    default String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }
}
