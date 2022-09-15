package com.reborn.reborn.service;

import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.MyWorkoutList;
import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.repository.MyWorkoutListRepository;
import com.reborn.reborn.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MyWorkoutListService {

    private final MyWorkoutListRepository myWorkoutListRepository;
    private final WorkoutRepository workoutRepository;

    public Long addWorkout(Member member, Long workoutId) {
        Workout workout = workoutRepository.findById(workoutId).orElseThrow(() -> new NoSuchElementException("찾으시는 운동이 없습니다"));
        MyWorkoutList myWorkoutList = new MyWorkoutList(workout, member);
        myWorkoutListRepository.save(myWorkoutList);
        return myWorkoutList.getId();
    }

}
