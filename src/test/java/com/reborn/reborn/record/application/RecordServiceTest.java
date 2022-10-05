package com.reborn.reborn.record.application;

import com.reborn.reborn.record.presentation.dto.RecordRequest;
import com.reborn.reborn.member.domain.Member;
import com.reborn.reborn.myworkout.domain.MyWorkout;
import com.reborn.reborn.record.domain.Record;
import com.reborn.reborn.workout.domain.Workout;
import com.reborn.reborn.myworkout.domain.repository.MyWorkoutRepository;
import com.reborn.reborn.record.domain.repository.RecordRepository;
import com.reborn.reborn.workout.domain.WorkoutCategory;
import org.assertj.core.api.Assertions;
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
        list.add(new RecordRequest(1L, 10, WorkoutCategory.BACK));
        Workout workout = Workout.builder().build();
        Member member = Member.builder().build();
        MyWorkout myWorkout = new MyWorkout(workout, member);

        given(myWorkoutRepository.findById(any())).willReturn(Optional.of(myWorkout));
        given(recordRepository.findByToday(any())).willReturn(Optional.empty());

        recordService.create(new RecordRequestList(list));

        verify(recordRepository).save(any());

    }
    @Test
    @DisplayName("요청을 받아 오늘 날짜기준으로 이미 저장되어있다면 무게만 더한다.")
    void createTwice() {
        List<RecordRequest> list = new ArrayList<>();
        RecordRequest recordRequest = new RecordRequest(1L, 10, WorkoutCategory.BACK);
        list.add(recordRequest);
        Workout workout = Workout.builder().build();
        Member member = Member.builder().build();
        MyWorkout myWorkout = new MyWorkout(workout, member);

        Record record = new Record(myWorkout, 10, WorkoutCategory.BACK);
        given(myWorkoutRepository.findById(any())).willReturn(Optional.of(myWorkout));
        given(recordRepository.findByToday(any())).willReturn(Optional.of(record));

        recordService.create(new RecordRequestList(list));

        verify(recordRepository, never()).save(any());

        Assertions.assertThat(record.getTotal()).isEqualTo(20);

    }
}