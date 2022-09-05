package com.reborn.reborn.service;

import com.reborn.reborn.dto.MemberRequestDto;
import com.reborn.reborn.dto.WorkoutDetailResponseDto;
import com.reborn.reborn.dto.WorkoutRequestDto;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.entity.WorkoutCategory;
import com.reborn.reborn.repository.WorkoutRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkoutServiceTest {

    @InjectMocks
    private WorkoutServiceImpl workoutService;
    @Mock
    WorkoutRepository workoutRepository;

    @Test
    @DisplayName("운동 정보를 생성하고 Id 를 리턴한다.")
    void create() {
        WorkoutRequestDto requestDto = WorkoutRequestDto.builder()
                .workoutCategory("BACK").build();
        Workout workout = Workout.builder().build();
        Member member = Member.builder().build();

        given(workoutRepository.save(any())).willReturn(workout);

        //when
        Long saveWorkoutId = workoutService.create(member, requestDto);
        //then
        verify(workoutRepository).save(any());

        Assertions.assertThat(saveWorkoutId).isEqualTo(workout.getId());

    }

    @Test
    @DisplayName("내 운동 정보를 조회한다.")
    void getMyWorkout() {
        Workout workout = Workout.builder().workoutCategory(WorkoutCategory.BACK).build();
        Member member = Member.builder().build();

        given(workoutRepository.findByIdAndMemberId(any(),any())).willReturn(Optional.of(workout));

        //when
        WorkoutDetailResponseDto myWorkout = workoutService.getMyWorkout(member, workout.getId());
        //then
        verify(workoutRepository).findByIdAndMemberId(any(),any());

        Assertions.assertThat(workout.getWorkoutCategory()).isEqualTo(myWorkout.getWorkoutCategory());

    }



}