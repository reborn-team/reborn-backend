package com.reborn.reborn.record.presentation;

import com.reborn.reborn.record.application.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.reborn.reborn.record.presentation.dto.RecordRequest.*;

@RestController
@RequestMapping("/api/v1/record")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @PostMapping
    public ResponseEntity<Void> createRecord(@RequestBody RecordRequestList list) {
        recordService.create(list);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
