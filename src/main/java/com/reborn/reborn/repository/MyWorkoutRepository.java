package com.reborn.reborn.repository;

import com.reborn.reborn.entity.MyWorkout;
import com.reborn.reborn.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MyWorkoutRepository extends JpaRepository<MyWorkout, Long> {

    Boolean existsByWorkoutIdAndMemberId(Long workoutId, Long memberId);

    Optional<MyWorkout> findByWorkoutIdAndMemberId(Long workoutId, Long memberId);

    Boolean existsByWorkoutId(Long workoutId);

}
