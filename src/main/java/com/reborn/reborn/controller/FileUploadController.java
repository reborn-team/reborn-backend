package com.reborn.reborn.controller;

import com.reborn.reborn.dto.FileDto;
import com.reborn.reborn.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController("/api/v1/file")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileService fileService;


//    @GetMapping
//    public String download(@RequestParam String filename){
//
//    }
    @PostMapping
    public List<FileDto> upload(List<MultipartFile> files){
        return fileService.uploadFile(files);
    }
}
