package com.reborn.reborn.service;

import com.reborn.reborn.dto.MyProgramList;
import com.reborn.reborn.dto.MyWorkoutDto;
import com.reborn.reborn.dto.WorkoutListDto;
import com.reborn.reborn.dto.WorkoutSliceDto;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.MyWorkout;
import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.entity.WorkoutCategory;
import com.reborn.reborn.repository.MemberRepository;
import com.reborn.reborn.repository.MyWorkoutRepository;
import com.reborn.reborn.repository.WorkoutRepository;
import com.reborn.reborn.repository.custom.WorkoutQuerydslRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MyWorkoutServiceTest {

    @InjectMocks
    private MyWorkoutService myWorkoutService;
    @Mock
    private MyWorkoutRepository myWorkoutRepository;
    @Mock
    private WorkoutRepository workoutRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private WorkoutQuerydslRepository workoutQuerydslRepository;

    @Test
    @DisplayName("내 운동 목록에 추가한다.")
    void addWorkoutList() {
        Workout workout = Workout.builder().build();
        Member member = Member.builder().build();
        MyWorkout myWorkout = new MyWorkout(workout, member);
        given(myWorkoutRepository.save(any())).willReturn(myWorkout);
        given(workoutRepository.findById(any())).willReturn(Optional.of(workout));
        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        Long myWorkoutId = myWorkoutService.addMyWorkout(member.getId(), workout.getId());

        verify(myWorkoutRepository).save(any());
        verify(workoutRepository).findById(any());

        assertThat(myWorkoutId).isEqualTo(myWorkout.getId());
    }

    @Test
    @DisplayName("내 운동 목록에 추가되어 있다면 예외를 반환한다.")
    void addWorkoutListEx() {
        Workout workout = Workout.builder().build();
        Member member = Member.builder().build();

        given(workoutRepository.findById(any())).willReturn(Optional.of(workout));
        given(memberRepository.findById(any())).willReturn(Optional.of(member));
        given(myWorkoutRepository.existsByWorkoutIdAndMemberId(anyLong(), anyLong())).willReturn(true);

        assertThatThrownBy(
                () -> myWorkoutService.addMyWorkout(member.getId(), workout.getId())
        ).isInstanceOf(RuntimeException.class);

        verify(workoutRepository).findById(any());
        verify(memberRepository).findById(any());

    }

    @Test
    @DisplayName("내 운동 목록에 추가되어 있다면 삭제한다.")
    void delete() {
        Workout workout = Workout.builder().build();
        Member member = Member.builder().build();
        MyWorkout myWorkout = new MyWorkout(workout, member);

        given(myWorkoutRepository.findByWorkoutIdAndMemberId(anyLong(), anyLong())).willReturn(Optional.of(myWorkout));

        myWorkoutService.deleteMyWorkout(1L, 1L);

        verify(myWorkoutRepository).delete(any());

    }

    @Test
    @DisplayName("내 운동 목록에 추가되어 없다면 삭제하지 않는다.")
    void deleteNonExist() {
        Workout workout = Workout.builder().build();
        Member member = Member.builder().build();
        MyWorkout myWorkout = new MyWorkout(workout, member);

        given(myWorkoutRepository.findByWorkoutIdAndMemberId(anyLong(), anyLong())).willReturn(Optional.empty());

        myWorkoutService.deleteMyWorkout(1L, 1L);

        verify(myWorkoutRepository, never()).delete(any());

    }

    @Test
    @DisplayName("내 운동 목록을 검색조건에 따라 결과가 10개면 true를 출력한다")
    void sliceResultTenWorkout() {
        List<WorkoutListDto> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new WorkoutListDto((long) i, "name", ""));
        }
        WorkoutSearchCondition cond = new WorkoutSearchCondition();
        given(workoutQuerydslRepository.pagingMyWorkoutWithSearchCondition(cond, 1L))
                .willReturn(list);

        WorkoutSliceDto slice = myWorkoutService.getMyWorkoutList(cond, 1L);
        verify(workoutQuerydslRepository).pagingMyWorkoutWithSearchCondition(any(), any());


        assertThat(list.size()).isEqualTo(slice.getPage().size());
        assertThat(slice.hasNext()).isTrue();
    }

    @Test
    @DisplayName("내 운동 목록을 검색조건에 따라 결과가 10개 미만이면 false를 출력한다")
    void sliceResultNotTenWorkout() {
        List<WorkoutListDto> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            list.add(new WorkoutListDto((long) i, "name", ""));
        }
        WorkoutSearchCondition cond = new WorkoutSearchCondition();
        given(workoutQuerydslRepository.pagingMyWorkoutWithSearchCondition(cond, 1L))
                .willReturn(list);

        WorkoutSliceDto slice = myWorkoutService.getMyWorkoutList(cond, 1L);
        verify(workoutQuerydslRepository).pagingMyWorkoutWithSearchCondition(any(), any());


        assertThat(list.size()).isEqualTo(slice.getPage().size());
        assertThat(slice.hasNext()).isFalse();
    }

    @Test
    @DisplayName("내 프로그램 목록을 반환한다.")
    void getMyProgram() {
        List<MyWorkoutDto> list = new ArrayList<>();

        given(workoutQuerydslRepository.getMyWorkoutDto(anyLong(), any())).willReturn(list);

        MyProgramList myProgram = myWorkoutService.getMyProgram(1L, WorkoutCategory.BACK);

        assertThat(myProgram.getList()).isEqualTo(list);
    }
}