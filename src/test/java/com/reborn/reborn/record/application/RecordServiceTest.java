package com.reborn.reborn.record.application;

import com.reborn.reborn.record.presentation.dto.RecordRequest;
import com.reborn.reborn.member.domain.Member;
import com.reborn.reborn.myworkout.domain.MyWorkout;
import com.reborn.reborn.record.domain.Record;
import com.reborn.reborn.record.presentation.dto.RecordTodayResponse;
import com.reborn.reborn.record.presentation.dto.RecordWeekResponse;
import com.reborn.reborn.workout.domain.Workout;
import com.reborn.reborn.myworkout.domain.repository.MyWorkoutRepository;
import com.reborn.reborn.record.domain.repository.RecordRepository;
import com.reborn.reborn.workout.domain.WorkoutCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.reborn.reborn.record.presentation.dto.RecordRequest.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class RecordServiceTest {

    @InjectMocks
    private RecordService recordService;
    @Mock
    private RecordRepository recordRepository;
    @Mock
    private MyWorkoutRepository myWorkoutRepository;

    @Test
    @DisplayName("요청을 받아 엔티티로 변환 후 전체 저장한다.")
    void create() {
        List<RecordRequest> list = new ArrayList<>();
        list.add(new RecordRequest(1L, 10L, WorkoutCategory.BACK));
        Workout workout = Workout.builder().build();
        Member member = Member.builder().build();
        MyWorkout myWorkout = new MyWorkout(workout, member);

        given(myWorkoutRepository.findById(any())).willReturn(Optional.of(myWorkout));
        given(recordRepository.findTodayRecordByMyWorkoutId(any())).willReturn(Optional.empty());

        recordService.create(new RecordRequestList(list));

        verify(recordRepository).save(any());

    }

    @Test
    @DisplayName("요청을 받아 오늘 날짜기준으로 이미 저장되어있다면 무게만 더한다.")
    void createTwice() {
        List<RecordRequest> list = new ArrayList<>();
        RecordRequest recordRequest = new RecordRequest(1L, 10L, WorkoutCategory.BACK);
        list.add(recordRequest);
        Workout workout = Workout.builder().build();
        Member member = Member.builder().build();
        MyWorkout myWorkout = new MyWorkout(workout, member);

        Record record = new Record(myWorkout, 10L, WorkoutCategory.BACK);
        given(myWorkoutRepository.findById(any())).willReturn(Optional.of(myWorkout));
        given(recordRepository.findTodayRecordByMyWorkoutId(any())).willReturn(Optional.of(record));

        recordService.create(new RecordRequestList(list));

        verify(recordRepository, never()).save(any());

        assertThat(record.getTotal()).isEqualTo(20);

    }

    @Test
    @DisplayName("회원과 오늘 날짜 기준으로 카테고리별 무게 총합을 출력한다")
    void getTodayRecord() {
        List<Record> records = new ArrayList<>();
        Workout workout = Workout.builder().build();
        Member member = Member.builder().build();
        MyWorkout myWorkout = new MyWorkout(workout, member);
        records.add(new Record(myWorkout, 10L, WorkoutCategory.BACK));
        records.add(new Record(myWorkout, 10L, WorkoutCategory.BACK));
        records.add(new Record(myWorkout, 10L, WorkoutCategory.LOWER_BODY));
        records.add(new Record(myWorkout, 50L, WorkoutCategory.CORE));

        given(recordRepository.findTodayRecordByMemberId(1L)).willReturn(records);

        RecordTodayResponse todayRecord = recordService.getTodayRecord(1L);

        assertAll(
                () -> assertThat(todayRecord.getBack()).isEqualTo(20),
                () -> assertThat(todayRecord.getChest()).isEqualTo(0),
                () -> assertThat(todayRecord.getCore()).isEqualTo(50),
                () -> assertThat(todayRecord.getLowerBody()).isEqualTo(10)
        );

    }

    @Test
    @DisplayName("이번 주 기준으로 record를 반환한다.")
    void getWeekRecord() {

        given(recordRepository.findWeekMyRecord(any(), any())).willReturn(Optional.of(new RecordWeekResponse()));

        RecordWeekResponse weekRecord = recordService.getWeekRecord(1L, null);

        assertThat(weekRecord.getFri()).isEqualTo(0);
    }

    @Test
    @DisplayName("이번 주 기준으로 record가 없으면 값이 0인 dto를 반환한다.")
    void getWeekRecordIfNull() {

        given(recordRepository.findWeekMyRecord(any(), any())).willReturn(Optional.empty());

        RecordWeekResponse weekRecord = recordService.getWeekRecord(1L, null);

        assertAll(
                () -> assertThat(weekRecord.getMon()).isEqualTo(0),
                () -> assertThat(weekRecord.getTue()).isEqualTo(0),
                () -> assertThat(weekRecord.getWed()).isEqualTo(0),
                () -> assertThat(weekRecord.getThu()).isEqualTo(0),
                () -> assertThat(weekRecord.getFri()).isEqualTo(0),
                () -> assertThat(weekRecord.getSat()).isEqualTo(0),
                () -> assertThat(weekRecord.getSun()).isEqualTo(0)
        );
    }
}