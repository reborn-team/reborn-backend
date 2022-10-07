package com.reborn.reborn.myworkout.domain.repository;

import com.reborn.reborn.myworkout.domain.MyWorkout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyWorkoutRepository extends JpaRepository<MyWorkout, Long> {

    Boolean existsByWorkoutIdAndMemberId(Long workoutId, Long memberId);

    Optional<MyWorkout> findByWorkoutIdAndMemberId(Long workoutId, Long memberId);

    Boolean existsByWorkoutId(Long workoutId);

}
