package com.reborn.reborn.repository;

import com.reborn.reborn.entity.MyWorkoutList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyWorkoutRepository extends JpaRepository<MyWorkoutList, Long> {

    Boolean existsByWorkoutIdAndMemberId(Long workoutId, Long memberId);
}
