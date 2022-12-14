package com.reborn.reborn.workout.domain.repository;

import com.reborn.reborn.workout.domain.Workout;
import com.reborn.reborn.workout.domain.WorkoutImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkoutImageRepository extends JpaRepository<WorkoutImage, Long> {
    @Modifying
    @Query("delete from WorkoutImage wi where wi.workout = :workout")
    void deleteAllByWorkout(@Param("workout") Workout workout);
}
