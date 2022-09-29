package com.reborn.reborn.record.domain.repository;

import com.reborn.reborn.record.domain.Record;

import java.util.Optional;

public interface RecordQuerydslRepository {

    Optional<Record> findByToday(Long workoutId);
}
