package com.reborn.reborn.myworkout.application;

import com.reborn.reborn.myworkout.exception.MyWorkoutNotFoundException;
import com.reborn.reborn.myworkout.presentation.dto.MyWorkoutResponse;
import com.reborn.reborn.common.presentation.dto.Slice;
import com.reborn.reborn.member.domain.Member;
import com.reborn.reborn.myworkout.domain.MyWorkout;
import com.reborn.reborn.workout.domain.Workout;
import com.reborn.reborn.workout.domain.WorkoutCategory;
import com.reborn.reborn.member.domain.repository.MemberRepository;
import com.reborn.reborn.myworkout.domain.repository.MyWorkoutRepository;
import com.reborn.reborn.workout.domain.repository.WorkoutRepository;
import com.reborn.reborn.workout.domain.repository.custom.WorkoutQuerydslRepository;
import com.reborn.reborn.workout.domain.repository.custom.WorkoutSearchCondition;
import com.reborn.reborn.workout.exception.WorkoutAlreadyExistException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.reborn.reborn.myworkout.presentation.dto.MyWorkoutResponse.*;
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
    @DisplayName("내 운동 목록에 추가하고 addCount를 1 더한다")
    void plusWorkoutAddCount() {
        Workout workout = Workout.builder().build();
        Member member = Member.builder().build();
        MyWorkout myWorkout = new MyWorkout(workout, member);
        given(myWorkoutRepository.save(any())).willReturn(myWorkout);
        given(workoutRepository.findById(any())).willReturn(Optional.of(workout));
        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        myWorkoutService.addMyWorkout(member.getId(), workout.getId());

        verify(myWorkoutRepository).save(any());
        verify(workoutRepository).findById(any());

        assertThat(workout.getAddCount()).isEqualTo(1L);
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
                () -> myWorkoutService.addMyWorkout(1L, 1L)
        ).isInstanceOf(WorkoutAlreadyExistException.class);

        verify(workoutRepository).findById(any());
        verify(memberRepository).findById(any());

        assertThat(workout.getAddCount()).isEqualTo(0L);
    }

    @Test
    @DisplayName("내 운동 목록에 추가되어 있다면 삭제한다.")
    void delete() {
        Workout workout = Workout.builder().build();
        Member member = Member.builder().build();
        MyWorkout myWorkout = new MyWorkout(workout, member);

        given(myWorkoutRepository.findByWorkoutIdAndMemberId(anyLong(), anyLong())).willReturn(Optional.of(myWorkout));
        given(workoutRepository.findById(any())).willReturn(Optional.of(workout));

        myWorkoutService.deleteMyWorkout(anyLong(), anyLong());

        verify(myWorkoutRepository).delete(any());
    }

    @Test
    @DisplayName("내 운동을 삭제하면 addCount가 1 줄어든다.")
    void minusWorkoutAddCount() {
        Workout workout = Workout.builder().build();
        Member member = Member.builder().build();
        MyWorkout myWorkout = new MyWorkout(workout, member);

        given(myWorkoutRepository.findByWorkoutIdAndMemberId(anyLong(), anyLong())).willReturn(Optional.of(myWorkout));
        given(workoutRepository.findById(any())).willReturn(Optional.of(workout));

        myWorkoutService.deleteMyWorkout(anyLong(), anyLong());

        assertThat(workout.getAddCount()).isEqualTo(-1);
    }

    @Test
    @DisplayName("내 운동 목록에 추가되어 없다면 삭제하지 않는다.")
    void deleteNonExist() {
        Workout workout = Workout.builder().build();

        given(myWorkoutRepository.findByWorkoutIdAndMemberId(anyLong(), anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> myWorkoutService.deleteMyWorkout(1L, 1L)).isInstanceOf(MyWorkoutNotFoundException.class);

        verify(myWorkoutRepository, never()).delete(any());
        assertThat(workout.getAddCount()).isEqualTo(0L);
    }

    @Test
    @DisplayName("내 운동 목록을 검색조건에 따라 결과가 10개면 true를 출력한다")
    void sliceResultTenWorkout() {
        List<MyWorkoutResponse> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new MyWorkoutResponse((long) i, "name", ""));
        }
        WorkoutSearchCondition cond = new WorkoutSearchCondition();
        given(workoutQuerydslRepository.pagingMyWorkoutWithSearchCondition(cond, 1L))
                .willReturn(list);

        Slice<MyWorkoutResponse> slice = myWorkoutService.getMyWorkoutList(cond, 1L);
        verify(workoutQuerydslRepository).pagingMyWorkoutWithSearchCondition(any(), any());


        assertThat(list.size()).isEqualTo(slice.getPage().size());
        assertThat(slice.hasNext()).isTrue();
    }

    @Test
    @DisplayName("내 운동 목록을 검색조건에 따라 결과가 10개 미만이면 false를 출력한다")
    void sliceResultNotTenWorkout() {
        List<MyWorkoutResponse> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            list.add(new MyWorkoutResponse((long) i, "name", ""));
        }
        WorkoutSearchCondition cond = new WorkoutSearchCondition();
        given(workoutQuerydslRepository.pagingMyWorkoutWithSearchCondition(cond, 1L))
                .willReturn(list);

        Slice<MyWorkoutResponse> slice = myWorkoutService.getMyWorkoutList(cond, 1L);
        verify(workoutQuerydslRepository).pagingMyWorkoutWithSearchCondition(any(), any());


        assertThat(list.size()).isEqualTo(slice.getPage().size());
        assertThat(slice.hasNext()).isFalse();
    }

    @Test
    @DisplayName("내 프로그램 목록을 반환한다.")
    void getMyProgram() {
        List<MyWorkoutResponse> list = new ArrayList<>();

        given(workoutQuerydslRepository.getMyWorkoutDto(anyLong(), any())).willReturn(list);

        MyWorkoutList myProgram = myWorkoutService.getMyProgram(1L, WorkoutCategory.BACK);

        assertThat(myProgram.getList()).isEqualTo(list);
    }
}