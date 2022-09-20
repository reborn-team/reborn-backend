package com.reborn.reborn.service;

import com.reborn.reborn.dto.FileDto;
import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.entity.WorkoutImage;
import com.reborn.reborn.repository.WorkoutImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkoutImageService {

    private final WorkoutImageRepository workoutImageRepository;

    @Transactional
    public Long create(FileDto fileDto, Workout workout){
<<<<<<< HEAD
        WorkoutImage workoutImage = WorkoutImage.builder()
                .originFileName(fileDto.getOriginFileName())
                .uploadFileName(fileDto.getUploadFileName())
                .workout(workout)
                .build();
=======
        WorkoutImage workoutImage = new WorkoutImage(fileDto.getOriginFileName(), fileDto.getUploadFileName());
        workoutImage.uploadToWorkout(workout);
>>>>>>> 113f9386f9f64211c2345b78b02f41d117e6e735
        workoutImageRepository.save(workoutImage);
        return workoutImage.getId();
    }



}
