package com.reborn.reborn.service;

import com.reborn.reborn.dto.WorkoutListDto;
import com.reborn.reborn.dto.WorkoutSliceDto;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.MyWorkout;
import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.repository.MemberRepository;
import com.reborn.reborn.repository.MyWorkoutRepository;
import com.reborn.reborn.repository.WorkoutRepository;
import com.reborn.reborn.repository.custom.WorkoutQuerydslRepository;
import com.reborn.reborn.repository.custom.WorkoutSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MyWorkoutService {

    private final MyWorkoutRepository myWorkoutRepository;
    private final WorkoutRepository workoutRepository;
    private final MemberRepository memberRepository;
    private final WorkoutQuerydslRepository workoutQuerydslRepository;

    @Transactional
    public Long addMyWorkout(Long memberId, Long workoutId) {
        Workout workout = getWorkout(workoutId);
        Member member = getMember(memberId);
        if (myWorkoutRepository.existsByWorkoutIdAndMemberId(workoutId, memberId)) {
            //TODO Exception
            throw new RuntimeException("이미 추가된 운동");
        }
        MyWorkout saveList = myWorkoutRepository.save(new MyWorkout(workout, member));
        return saveList.getId();
    }

    @Transactional
    public void deleteMyWorkout(Long memberId, Long workoutId) {
        myWorkoutRepository.findByWorkoutIdAndMemberId(workoutId, memberId)
                .ifPresent(myWorkoutRepository::delete);
    }

    @Transactional(readOnly = true)
    public WorkoutSliceDto getMyWorkoutList(WorkoutSearchCondition cond, Long memberId) {
        List<WorkoutListDto> result = workoutQuerydslRepository.pagingMyWorkoutWithSearchCondition(cond, memberId);
        return new WorkoutSliceDto(result);
    }

    private Workout getWorkout(Long workoutId) {
        return workoutRepository.findById(workoutId).orElseThrow(() -> new NoSuchElementException("찾으시는 운동이 없습니다"));
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow();
    }

}
