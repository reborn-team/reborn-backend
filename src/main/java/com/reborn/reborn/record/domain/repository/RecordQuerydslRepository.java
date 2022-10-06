package com.reborn.reborn.record.domain.repository;

import com.reborn.reborn.record.domain.Record;

import java.util.List;
import java.util.Optional;

public interface RecordQuerydslRepository {

    Optional<Record> findTodayRecordByMyWorkoutId(Long myWorkoutId);

    List<Record> findTodayRecordByMemberId(Long memberId);
}
