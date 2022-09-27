package com.reborn.reborn.service;

import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.MyWorkoutList;
import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.repository.MemberRepository;
import com.reborn.reborn.repository.MyWorkoutRepository;
import com.reborn.reborn.repository.WorkoutRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MyWorkoutServiceTest {

    @InjectMocks
    private MyWorkoutService myWorkoutService;
    @Mock
    private MyWorkoutRepository workoutListRepository;
    @Mock
    private WorkoutRepository workoutRepository;
    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("내 운동 목록에 추가한다.")
    void addWorkoutList() {
        Workout workout = Workout.builder().build();
        Member member = Member.builder().build();
        MyWorkoutList myWorkoutList = new MyWorkoutList(workout, member);
        given(workoutListRepository.save(any())).willReturn(myWorkoutList);
        given(workoutRepository.findById(any())).willReturn(Optional.of(workout));
        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        Long myWorkoutId = myWorkoutService.addWorkout(member.getId(), workout.getId());

        verify(workoutListRepository).save(any());
        verify(workoutRepository).findById(any());

        assertThat(myWorkoutId).isEqualTo(myWorkoutList.getId());
    }

}