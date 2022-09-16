package com.reborn.reborn.service;

import com.reborn.reborn.dto.WorkoutRequestDto;
import com.reborn.reborn.dto.WorkoutResponseDto;
import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.entity.WorkoutCategory;
import com.reborn.reborn.repository.WorkoutRepository;
import com.reborn.reborn.repository.custom.WorkoutSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    @Transactional
    public Long create(WorkoutRequestDto dto) {

        Workout workout = Workout.builder()
                .workoutName(dto.getWorkoutName())
                .content(dto.getContent())
                .workoutCategory(WorkoutCategory.valueOf(dto.getWorkoutCategory()))
                .build();

        Workout saveWorkout = workoutRepository.save(workout);
        return saveWorkout.getId();
    }

    public Workout getWorkout(Long id) {
        return workoutRepository.findById(id).orElseThrow(() -> new NoSuchElementException("찾으시는 운동이 없습니다."));
    }

    public List<WorkoutResponseDto> pagingWorkout(WorkoutSearchCondition cond){
        return workoutRepository.paginationWorkoutList(cond);
    }
}
