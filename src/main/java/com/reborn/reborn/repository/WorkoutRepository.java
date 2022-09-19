package com.reborn.reborn.repository;

import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.repository.custom.WorkoutQuerydslRepository;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WorkoutRepository extends JpaRepository<Workout, Long>, WorkoutQuerydslRepository {

}
