package com.reborn.reborn.record.presentation;

import com.reborn.reborn.record.application.RecordService;
import com.reborn.reborn.record.presentation.dto.RecordTodayResponse;
import com.reborn.reborn.record.presentation.dto.RecordWeekResponse;
import com.reborn.reborn.security.domain.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.LocalDate;

import static com.reborn.reborn.record.presentation.dto.RecordRequest.*;

@RestController
@RequestMapping("/api/v1/records")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @PostMapping
    public ResponseEntity<Void> createRecord(@RequestBody @Valid RecordRequestList list) {
        recordService.create(list);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/day")
    public ResponseEntity<RecordTodayResponse> getTodayRecord(@LoginMember Long memberId,
                                                              @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                              @RequestParam(value = "date", required = false) LocalDate date) {
        RecordTodayResponse response = recordService.getTodayRecord(memberId, date);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/week")
    public ResponseEntity<RecordWeekResponse> getWeekRecord(@LoginMember Long memberId,
                                                            @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                            @RequestParam(value = "date", required = false) LocalDate date) {
        RecordWeekResponse response = recordService.getWeekRecord(memberId, date);
        return ResponseEntity.ok(response);
    }
}
