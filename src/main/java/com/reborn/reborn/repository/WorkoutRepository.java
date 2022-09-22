package com.reborn.reborn.repository;

import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.repository.custom.WorkoutQuerydslRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface WorkoutRepository extends JpaRepository<Workout, Long>, WorkoutQuerydslRepository {

//    Optional<Workout>
}
