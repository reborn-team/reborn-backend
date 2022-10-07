package com.reborn.reborn.myworkout.application;

import com.reborn.reborn.myworkout.presentation.dto.MyWorkoutResponse;
import com.reborn.reborn.common.presentation.dto.Slice;
import com.reborn.reborn.member.domain.Member;
import com.reborn.reborn.myworkout.domain.MyWorkout;
import com.reborn.reborn.workout.domain.Workout;
import com.reborn.reborn.workout.domain.WorkoutCategory;
import com.reborn.reborn.member.exception.MemberNotFoundException;
import com.reborn.reborn.workout.exception.WorkoutAlreadyExistException;
import com.reborn.reborn.workout.exception.WorkoutNotFoundException;
import com.reborn.reborn.member.domain.repository.MemberRepository;
import com.reborn.reborn.myworkout.domain.repository.MyWorkoutRepository;
import com.reborn.reborn.workout.domain.repository.WorkoutRepository;
import com.reborn.reborn.workout.domain.repository.custom.WorkoutQuerydslRepository;
import com.reborn.reborn.workout.domain.repository.custom.WorkoutSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.reborn.reborn.myworkout.presentation.dto.MyWorkoutResponse.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MyWorkoutService {

    private final MyWorkoutRepository myWorkoutRepository;
    private final WorkoutRepository workoutRepository;
    private final MemberRepository memberRepository;
    private final WorkoutQuerydslRepository workoutQuerydslRepository;

    public Long addMyWorkout(Long memberId, Long workoutId) {
        Workout workout = getWorkout(workoutId);
        Member member = getMember(memberId);
        if (myWorkoutRepository.existsByWorkoutIdAndMemberId(workoutId, memberId)) {
            throw new WorkoutAlreadyExistException(workout.getWorkoutName());
        }
        MyWorkout saveList = myWorkoutRepository.save(new MyWorkout(workout, member));
        return saveList.getId();
    }

    public void deleteMyWorkout(Long memberId, Long workoutId) {
        myWorkoutRepository.findByWorkoutIdAndMemberId(workoutId, memberId)
                .ifPresent(myWorkoutRepository::delete);
    }

    @Transactional(readOnly = true)
    public Slice<MyWorkoutResponse> getMyWorkoutList(WorkoutSearchCondition cond, Long memberId) {
        List<MyWorkoutResponse> result = workoutQuerydslRepository.pagingMyWorkoutWithSearchCondition(cond, memberId);
        return new Slice<>(result);
    }

    @Transactional(readOnly = true)
    public MyWorkoutList getMyProgram(Long memberId, WorkoutCategory workoutCategory) {
        List<MyWorkoutResponse> list = workoutQuerydslRepository.getMyWorkoutDto(memberId, workoutCategory);
        return new MyWorkoutList(list);
    }

    private Workout getWorkout(Long workoutId) {
        return workoutRepository.findById(workoutId).orElseThrow(() -> new WorkoutNotFoundException(workoutId.toString()));
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId.toString()));
    }
}
