package com.reborn.reborn.repository.custom;

import com.reborn.reborn.dto.WorkoutListDto;

import java.util.List;

public interface WorkoutQuerydslRepository {

    List<WorkoutListDto> pagingWorkoutWithSearchCondition(WorkoutSearchCondition cond);

    List<WorkoutListDto> pagingMyWorkoutWithSearchCondition(WorkoutSearchCondition cond, Long memberId);

}
