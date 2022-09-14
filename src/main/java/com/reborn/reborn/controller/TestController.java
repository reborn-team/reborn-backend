package com.reborn.reborn.controller;

import com.reborn.reborn.dto.FileDto;
import com.reborn.reborn.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TestController {

    private final FileService fileService;

    @GetMapping("/upload")
    public String hello() {
        return "index";
    }

    @PostMapping("/upload")
    public String saveFile(@RequestParam List<MultipartFile> file,
                           HttpServletRequest request) {

        log.info("request={}", request);
        log.info("multipartFile={}", file);
        List<FileDto> fileDtos = fileService.uploadFile(file);
        for (FileDto fileDto : fileDtos) {
            log.info("fileDto ={}", fileDto);
        }
        return "index";
    }

    @PostMapping("/delete")
    public String delete(String file) {
        fileService.deleteFile(file);
        log.info("filename={}", file);
        return "index";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        //file:/Users/.../.../.../filename.png
        return new UrlResource("file:" + fileService.getFullPath(filename));
    }
}
