package com.reborn.reborn.service;

import com.reborn.reborn.dto.WorkoutListDto;
import com.reborn.reborn.dto.WorkoutRequestDto;
import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.entity.WorkoutCategory;
import com.reborn.reborn.repository.WorkoutRepository;
import com.reborn.reborn.repository.custom.WorkoutSearchCondition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkoutServiceTest {

    @InjectMocks
    private WorkoutService workoutService;
    @Mock
    WorkoutRepository workoutRepository;

    @Test
    @DisplayName("운동 정보를 생성하고 Id 를 리턴한다.")
    void create() {
        WorkoutRequestDto requestDto = WorkoutRequestDto.builder()
                .workoutCategory("BACK").build();
        Workout workout = Workout.builder().build();

        given(workoutRepository.save(any())).willReturn(workout);

        //when
        Long saveWorkoutId = workoutService.create(requestDto);
        //then
        verify(workoutRepository).save(any());

        assertThat(saveWorkoutId).isEqualTo(workout.getId());

    }

    @Test
    @DisplayName("운동 정보를 조회한다.")
    void getMyWorkout() {
        Workout workout = Workout.builder().workoutCategory(WorkoutCategory.BACK).build();

        given(workoutRepository.findById(any())).willReturn(Optional.of(workout));

        //when
        Workout findWorkout = workoutService.findWorkoutById(workout.getId());
        //then
        verify(workoutRepository).findById(any());

        assertThat(workout.getWorkoutCategory()).isEqualTo(findWorkout.getWorkoutCategory());

    }

    @Test
    @DisplayName("운동 정보를 검색조건에 따라 10개씩 출력한다")
    void pagingWorkout(){
        List<WorkoutListDto> list = new ArrayList<>();
        WorkoutSearchCondition cond = new WorkoutSearchCondition();
        given(workoutRepository.paginationWorkoutList(cond))
                .willReturn(list);

        List<WorkoutListDto> findList = workoutService.pagingWorkout(cond);
        verify(workoutRepository).paginationWorkoutList(any());

        assertThat(list.size()).isEqualTo(findList.size());
    }



}