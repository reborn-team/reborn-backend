package com.reborn.reborn.record.domain.repository;

import com.reborn.reborn.record.domain.Record;
import com.reborn.reborn.record.presentation.dto.RecordWeekResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RecordQuerydslRepository {

    Optional<Record> findTodayRecordByMyWorkoutId(Long myWorkoutId);

    List<Record> findTodayRecordByMemberId(Long memberId, LocalDate localDate);

    Optional<RecordWeekResponse> findWeekMyRecord(Long memberId, LocalDate localDate);
}
