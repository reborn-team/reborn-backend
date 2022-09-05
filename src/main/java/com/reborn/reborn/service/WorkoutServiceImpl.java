package com.reborn.reborn.service;

import com.reborn.reborn.dto.WorkoutResponseDto;
import com.reborn.reborn.dto.WorkoutRequestDto;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.entity.WorkoutCategory;
import com.reborn.reborn.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;

    @Transactional
    @Override
    public Long create(Member member,WorkoutRequestDto dto) {

        Workout workout = Workout.builder()
                .workoutName(dto.getWorkoutName())
                .content(dto.getContent())
                .workoutCategory(WorkoutCategory.valueOf(dto.getWorkoutCategory()))
                .member(member)
                .filePath(dto.getFilePath())
                .build();

        Workout saveWorkout = workoutRepository.save(workout);
        return saveWorkout.getId();
    }

    @Override
    public WorkoutResponseDto getMyWorkout(Member member, Long workoutId) {
        Optional<Workout> workout = workoutRepository.findByIdAndMemberId(workoutId, member.getId());
        if(workout.isEmpty()){
            throw new NoSuchElementException("찾으시는 운동이 없습니다.");
        }
        WorkoutResponseDto workoutResponseDto = new WorkoutResponseDto();
        return workoutResponseDto.toDto(workout.get());
    }
}
