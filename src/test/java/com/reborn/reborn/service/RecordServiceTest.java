package com.reborn.reborn.service;

import com.reborn.reborn.dto.RecordRequest;
import com.reborn.reborn.dto.RecordRequestList;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.MyWorkout;
import com.reborn.reborn.entity.Record;
import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.repository.MyWorkoutRepository;
import com.reborn.reborn.repository.RecordRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        list.add(new RecordRequest(1L, 10));
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
        RecordRequest recordRequest = new RecordRequest(1L, 10);
        list.add(recordRequest);
        Workout workout = Workout.builder().build();
        Member member = Member.builder().build();
        MyWorkout myWorkout = new MyWorkout(workout, member);

        Record record = new Record(myWorkout, 10);
        given(myWorkoutRepository.findById(any())).willReturn(Optional.of(myWorkout));
        given(recordRepository.findByToday(any())).willReturn(Optional.of(record));

        recordService.create(new RecordRequestList(list));

        verify(recordRepository, never()).save(any());

        Assertions.assertThat(record.getTotal()).isEqualTo(20);

    }
}