package com.reborn.reborn.repository;

import com.reborn.reborn.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
}
