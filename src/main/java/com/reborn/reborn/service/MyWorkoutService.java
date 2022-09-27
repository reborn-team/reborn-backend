package com.reborn.reborn.service;

import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.MyWorkoutList;
import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.repository.MemberRepository;
import com.reborn.reborn.repository.MyWorkoutRepository;
import com.reborn.reborn.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MyWorkoutService {

    private final MyWorkoutRepository myWorkoutRepository;
    private final WorkoutRepository workoutRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long addWorkout(Long memberId, Long workoutId) {
        Workout workout = workoutRepository.findById(workoutId).orElseThrow(() -> new NoSuchElementException("찾으시는 운동이 없습니다"));
        Member member = memberRepository.findById(memberId).orElseThrow();
        if (myWorkoutRepository.existsByWorkoutIdAndMemberId(workoutId, memberId)) {

        }
        MyWorkoutList saveList = myWorkoutRepository.save(new MyWorkoutList(workout, member));
        return saveList.getId();
    }

}
