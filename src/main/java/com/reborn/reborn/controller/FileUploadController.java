package com.reborn.reborn.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.reborn.reborn.dto.FileDto;
import com.reborn.reborn.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileService fileService;

    @PostMapping
    public ResponseEntity<List<FileDto>> saveFile(@RequestBody List<MultipartFile> file) {
        return ResponseEntity.ok().body(fileService.uploadFile(file));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> delete(@RequestParam String filename) {
        boolean isDelete = fileService.deleteFile(filename);
        return ResponseEntity.ok().body(isDelete);
    }

    @GetMapping("/images")
    public Resource downloadImage(@RequestParam String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileService.getFullPath(filename));
    }
}
