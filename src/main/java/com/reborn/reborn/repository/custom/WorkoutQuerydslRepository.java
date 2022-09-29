package com.reborn.reborn.repository.custom;

import com.reborn.reborn.dto.MyWorkoutDto;
import com.reborn.reborn.dto.WorkoutListDto;
import com.reborn.reborn.entity.WorkoutCategory;

import java.util.List;

public interface WorkoutQuerydslRepository {

    List<WorkoutListDto> pagingWorkoutWithSearchCondition(WorkoutSearchCondition cond);

    List<MyWorkoutDto> pagingMyWorkoutWithSearchCondition(WorkoutSearchCondition cond, Long memberId);

    List<MyWorkoutDto> getMyWorkoutDto(Long memberId,WorkoutCategory workoutCategory);

}
