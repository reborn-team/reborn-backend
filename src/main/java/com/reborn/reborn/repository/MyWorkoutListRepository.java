package com.reborn.reborn.repository;

import com.reborn.reborn.entity.MyWorkoutList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MyWorkoutListRepository extends JpaRepository<MyWorkoutList, Long> {

    @Query("select m.id, m.workout.id, m.workout.workoutCategory, m.workout.workoutName,i.uploadFileName from MyWorkoutList m left join WorkoutImage i on m.workout=i.workout")
    List<Object[]> findMyWorkout();

}
