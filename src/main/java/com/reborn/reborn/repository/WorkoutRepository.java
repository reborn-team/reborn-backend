package com.reborn.reborn.repository;

import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.repository.custom.WorkoutQuerydslRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface WorkoutRepository extends JpaRepository<Workout, Long>, WorkoutQuerydslRepository {

    @Query(value = "select w from Workout w left join fetch w.workoutImages i join fetch w.member where w.id = :id")
    Optional<Workout> findByIdWithImagesAndMember(@Param("id") Long workoutId);
}
