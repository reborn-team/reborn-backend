package com.reborn.reborn.repository.custom;

import com.reborn.reborn.dto.WorkoutListDto;
import com.reborn.reborn.dto.WorkoutResponseDto;

import java.util.List;

public interface WorkoutQuerydslRepository {

    List<WorkoutListDto> paginationWorkoutList(WorkoutSearchCondition cond);

    WorkoutResponseDto getWorkoutDetail(Long workoutId);
}
