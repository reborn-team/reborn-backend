package com.reborn.reborn.record.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.reborn.reborn.record.domain.Record;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static com.reborn.reborn.record.domain.QRecord.record;


@RequiredArgsConstructor
public class RecordQuerydslRepositoryImpl implements RecordQuerydslRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Record> findByToday(Long workoutId) {
        LocalDate today = LocalDate.now();
        LocalTime start = LocalTime.of(0, 0, 0);
        LocalTime last = LocalTime.of(23, 59, 59);
        Record result = queryFactory
                .selectFrom(record)
                .where(
                        record.myWorkout.id.eq(workoutId),
                        record.createdDate.between(LocalDateTime.of(today, start), LocalDateTime.of(today, last))
                ).fetchOne();
        return Optional.ofNullable(result);
    }
}
