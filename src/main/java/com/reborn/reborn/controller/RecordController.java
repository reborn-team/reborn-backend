package com.reborn.reborn.controller;

import com.reborn.reborn.dto.RecordRequest;
import com.reborn.reborn.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/record")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @PostMapping
    public ResponseEntity createRecord(@RequestBody List<RecordRequest> list) {
        recordService.create(list);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
