package com.reborn.reborn.service;

import com.reborn.reborn.dto.WorkoutRequestDto;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.entity.WorkoutCategory;
import com.reborn.reborn.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;

    @Transactional
    @Override
    public void create(Member member,WorkoutRequestDto dto) {

        Workout workout = Workout.builder()
                .workoutName(dto.getWorkoutName())
                .content(dto.getContent())
                .workoutCategory(WorkoutCategory.valueOf(dto.getWorkoutCategory()))
                .member(member)
                .filePath(dto.getFilePath())
                .build();

        workoutRepository.save(workout);
    }
}
