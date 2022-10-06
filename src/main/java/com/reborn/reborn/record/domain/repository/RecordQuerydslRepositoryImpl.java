package com.reborn.reborn.record.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.reborn.reborn.record.domain.Record;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.reborn.reborn.myworkout.domain.QMyWorkout.myWorkout;
import static com.reborn.reborn.record.domain.QRecord.record;


@RequiredArgsConstructor
public class RecordQuerydslRepositoryImpl implements RecordQuerydslRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Record> findTodayRecordByMyWorkoutId(Long myWorkoutId) {
        Record result = queryFactory
                .selectFrom(record)
                .where(
                        record.myWorkout.id.eq(myWorkoutId),
                        betweenToday()
                ).fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public List<Record> findTodayRecordByMemberId(Long memberId) {
        return queryFactory
                .selectFrom(record)
                .where(
                        myWorkout.member.id.eq(memberId),
                        betweenToday()
                )
                .innerJoin(record.myWorkout, myWorkout).fetch();
    }

    private BooleanExpression betweenToday() {
        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
        LocalDateTime last = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));
        return record.createdDate.between(start, last);
    }


}
