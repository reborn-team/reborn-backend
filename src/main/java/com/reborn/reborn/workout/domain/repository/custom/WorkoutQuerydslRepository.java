package com.reborn.reborn.workout.domain.repository.custom;

import com.reborn.reborn.myworkout.presentation.dto.MyWorkoutResponse;
import com.reborn.reborn.workout.presentation.dto.WorkoutPreviewResponse;
import com.reborn.reborn.workout.domain.WorkoutCategory;

import java.util.List;

public interface WorkoutQuerydslRepository {

    List<WorkoutPreviewResponse> pagingWorkoutWithSearchCondition(WorkoutSearchCondition cond);

    List<MyWorkoutResponse> pagingMyWorkoutWithSearchCondition(WorkoutSearchCondition cond, Long memberId);

    List<MyWorkoutResponse> getMyWorkoutDto(Long memberId, WorkoutCategory workoutCategory);

}
