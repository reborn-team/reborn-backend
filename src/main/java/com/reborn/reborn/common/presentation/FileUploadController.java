package com.reborn.reborn.common.presentation;

import com.reborn.reborn.common.presentation.dto.FileDto;
import com.reborn.reborn.common.application.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/api/v1/files")
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
    public ResponseEntity<Resource> downloadImage(@RequestParam String filename) {
        Resource resource = fileService.downloadFile(filename);
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES)).body(resource);
    }
}
