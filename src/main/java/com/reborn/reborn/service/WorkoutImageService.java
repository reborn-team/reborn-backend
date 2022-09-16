package com.reborn.reborn.service;

import com.reborn.reborn.dto.FileDto;
import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.entity.WorkoutImage;
import com.reborn.reborn.repository.WorkoutImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkoutImageService {

    private final WorkoutImageRepository workoutImageRepository;

    public Long create(FileDto fileDto, Workout workout){
        WorkoutImage workoutImage = WorkoutImage.builder()
                .originFileName(fileDto.getOriginFileName())
                .uploadFileName(fileDto.getUploadFileName())
                .workout(workout)
                .build();
        workoutImageRepository.save(workoutImage);
        return workoutImage.getId();
    }



}
