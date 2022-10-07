package com.reborn.reborn.workout.domain.repository;

import com.reborn.reborn.workout.domain.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    @Query(value = "select w from Workout w left join fetch w.workoutImages i join fetch w.member where w.id = :id")
    Optional<Workout> findByIdWithImagesAndMember(@Param("id") Long workoutId);
}
