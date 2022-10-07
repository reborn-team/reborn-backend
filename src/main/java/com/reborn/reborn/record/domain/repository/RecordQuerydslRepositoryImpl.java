package com.reborn.reborn.record.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.reborn.reborn.record.domain.Record;
import com.reborn.reborn.record.domain.Week;
import com.reborn.reborn.record.presentation.dto.QRecordWeekResponse;
import com.reborn.reborn.record.presentation.dto.RecordWeekResponse;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.reborn.reborn.myworkout.domain.QMyWorkout.myWorkout;
import static com.reborn.reborn.record.domain.QRecord.record;
import static com.reborn.reborn.record.domain.Week.*;


@RequiredArgsConstructor
public class RecordQuerydslRepositoryImpl implements RecordQuerydslRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Record> findTodayRecordByMyWorkoutId(Long myWorkoutId) {
        Record result = queryFactory
                .selectFrom(record)
                .where(
                        record.myWorkout.id.eq(myWorkoutId),
                        betweenDate(LocalDate.now(), LocalDate.now())
                ).fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public List<Record> findTodayRecordByMemberId(Long memberId) {
        return queryFactory
                .selectFrom(record)
                .where(
                        myWorkout.member.id.eq(memberId),
                        betweenDate(LocalDate.now(), LocalDate.now())
                )
                .innerJoin(record.myWorkout, myWorkout).fetch();
    }

    @Override
    public RecordWeekResponse findWeekMyRecord(Long memberId, LocalDate date) {

        LocalDate localDate = dateIfNullReturnNow(date);

        RecordWeekResponse response = queryFactory
                .select(new QRecordWeekResponse(
                        getDayOfWeekTotalByMember(memberId, MONDAY, localDate),
                        getDayOfWeekTotalByMember(memberId, TUESDAY, localDate),
                        getDayOfWeekTotalByMember(memberId, WEDNESDAY, localDate),
                        getDayOfWeekTotalByMember(memberId, THURSDAY, localDate),
                        getDayOfWeekTotalByMember(memberId, FRIDAY, localDate),
                        getDayOfWeekTotalByMember(memberId, SATURDAY, localDate),
                        getDayOfWeekTotalByMember(memberId, SUNDAY, localDate)
                ))
                .from(record).join(record.myWorkout, myWorkout)
                .where(
                        betweenDate(getLocalDateThisWeekOfDay(SUNDAY, localDate), getLocalDateThisWeekOfDay(SATURDAY, localDate)),
                        myWorkout.member.id.eq(memberId)
                )
                .groupBy(myWorkout.member.id)
                .fetchOne();

        return ifIsNull(response);
    }


    private JPQLQuery<Long> getDayOfWeekTotalByMember(Long memberId, Week day, LocalDate localDate) {
        return JPAExpressions
                .select(record.total.sum())
                .from(record)
                .join(record.myWorkout, myWorkout)
                .where(
                        myWorkout.member.id.eq(memberId),
                        betweenDate(getLocalDateThisWeekOfDay(day, localDate), getLocalDateThisWeekOfDay(day, localDate))
                );
    }


    private BooleanExpression betweenDate(LocalDate start, LocalDate end) {
        LocalDateTime first = LocalDateTime.of(start, LocalTime.of(0, 0, 0));
        LocalDateTime last = LocalDateTime.of(end, LocalTime.of(23, 59, 59));
        return record.createdDate.between(first, last);
    }

    private LocalDate getLocalDateThisWeekOfDay(Week day, LocalDate localDate) {
        TemporalField fieldISO = WeekFields.of(Locale.KOREA).dayOfWeek();
        return localDate.with(fieldISO, day.getValue());
    }

    private LocalDate dateIfNullReturnNow(LocalDate date) {
        return date == null ? LocalDate.now() : date;
    }

    private RecordWeekResponse ifIsNull(RecordWeekResponse response) {
        return response == null ? new RecordWeekResponse() : response;
    }

}
