package com.reborn.reborn.repository;

import com.reborn.reborn.entity.MyWorkout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyWorkoutRepository extends JpaRepository<MyWorkout, Long> {

    Boolean existsByWorkoutIdAndMemberId(Long workoutId, Long memberId);

    Optional<MyWorkout> findByWorkoutIdAndMemberId(Long workoutId, Long memberId);
}
