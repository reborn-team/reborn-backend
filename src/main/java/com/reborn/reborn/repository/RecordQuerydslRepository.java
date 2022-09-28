package com.reborn.reborn.repository;

import com.reborn.reborn.entity.Record;

import java.util.Optional;

public interface RecordQuerydslRepository {

    Optional<Record> findByToday(Long workoutId);
}
