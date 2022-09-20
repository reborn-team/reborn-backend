package com.reborn.reborn.service;

import com.reborn.reborn.dto.WorkoutListDto;
import com.reborn.reborn.dto.WorkoutRequestDto;
<<<<<<< HEAD
=======
import com.reborn.reborn.dto.WorkoutSliceDto;
import com.reborn.reborn.entity.Member;
>>>>>>> 113f9386f9f64211c2345b78b02f41d117e6e735
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
        Member member = Member.builder().build();

        given(workoutRepository.save(any())).willReturn(workout);

        //when
        Long saveWorkoutId = workoutService.create(member,requestDto);
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
<<<<<<< HEAD
    @DisplayName("운동 정보를 검색조건에 따라 10개씩 출력한다")
    void pagingWorkout(){
        List<WorkoutListDto> list = new ArrayList<>();
=======
    @DisplayName("운동 정보를 검색조건에 따라 결과가 10개면 true를 출력한다")
    void sliceResultTenWorkout(){
        List<WorkoutListDto> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new WorkoutListDto((long)i,"name",""));
        }
>>>>>>> 113f9386f9f64211c2345b78b02f41d117e6e735
        WorkoutSearchCondition cond = new WorkoutSearchCondition();
        given(workoutRepository.paginationWorkoutList(cond))
                .willReturn(list);

        List<WorkoutListDto> findList = workoutService.pagingWorkout(cond);
        verify(workoutRepository).paginationWorkoutList(any());

        WorkoutSliceDto workoutSliceDto = new WorkoutSliceDto(findList);

        assertThat(list.size()).isEqualTo(findList.size());
        assertThat(workoutSliceDto.hasNext()).isTrue();
    }
    @Test
    @DisplayName("운동 정보를 검색조건에 따라 결과가 10개 미만이면 false를 출력한다")
    void sliceResultNotTenWorkout(){
        List<WorkoutListDto> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            list.add(new WorkoutListDto((long)i,"name",""));
        }
        WorkoutSearchCondition cond = new WorkoutSearchCondition();
        given(workoutRepository.paginationWorkoutList(cond))
                .willReturn(list);

        List<WorkoutListDto> findList = workoutService.pagingWorkout(cond);
        verify(workoutRepository).paginationWorkoutList(any());

        WorkoutSliceDto workoutSliceDto = new WorkoutSliceDto(findList);

        assertThat(list.size()).isEqualTo(findList.size());
        assertThat(workoutSliceDto.hasNext()).isFalse();
    }



}