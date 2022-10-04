package com.reborn.reborn.common.application;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.reborn.reborn.common.exception.FileDownloadException;
import com.reborn.reborn.common.exception.FileUploadException;
import com.reborn.reborn.common.presentation.dto.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Profile("!dev")
@Service
@RequiredArgsConstructor
public class S3UploadService implements FileService {


    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${file.upload.directory}")
    private String directory;

    private final AmazonS3 amazonS3;

    public List<FileDto> uploadFile(List<MultipartFile> multipartFile) {
        List<FileDto> files = new ArrayList<>();

        for (MultipartFile file : multipartFile) {
            String originFileName = file.getOriginalFilename();
            String uploadFileName = createUploadFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                amazonS3.putObject(new PutObjectRequest(bucket, uploadFileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                throw new FileUploadException(e.getMessage());
            }

            files.add(new FileDto(originFileName, uploadFileName));
        }

        return files;
    }

    public boolean deleteFile(String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
        return true;
    }

    @Override
    public String getFullPath(String uploadFileName) {
        return directory + uploadFileName;
    }

    @Override
    public Resource downloadFile(String filename) {
        try {
            return new UrlResource(getFullPath(filename));
        } catch (Exception e) {
            throw new FileDownloadException("파일 다운로드에 실패하였습니다.");
        }
    }

}
