package com.reborn.reborn.controller;

import com.reborn.reborn.dto.FileDto;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TestController {

    private final UploadService uploadService;
    @GetMapping("/upload")
    public String  hello() {
        return "index";
    }

    @PostMapping("/upload")
    public String saveFile(@RequestParam List<MultipartFile> file,
                           HttpServletRequest request) {

        log.info("request={}", request);
        log.info("multipartFile={}", file);
        List<FileDto> fileDtos = uploadService.uploadFile(file);
        for (FileDto fileDto : fileDtos) {
            log.info("fileDto ={}",fileDto);
        }
        return "index";
    }

    @PostMapping("/delete")
    public String delete(String file){
        uploadService.deleteFile(file);
        log.info("filename={}",file);
        return "index";
    }
}
