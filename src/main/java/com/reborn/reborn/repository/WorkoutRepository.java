package com.reborn.reborn.repository;

import com.reborn.reborn.dto.WorkoutResponseDto;
import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.repository.custom.WorkoutQuerydslRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long>, WorkoutQuerydslRepository {

    @Query("select new com.reborn.reborn.dto.WorkoutResponseDto(w.id, w.workoutName, w.content,image.path ,image.fileName, w.workoutCategory)" +
            " from Workout w left join  WorkoutImage image on w = image.workout")
    List<WorkoutResponseDto> findAllDto();
}
