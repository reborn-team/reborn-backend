package com.reborn.reborn.repository;

import com.reborn.reborn.entity.MyWorkoutList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyWorkoutListRepository extends JpaRepository<MyWorkoutList, Long> {
}
