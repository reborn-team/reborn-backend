package com.reborn.reborn.repository.custom;

import com.reborn.reborn.dto.WorkoutResponseDto;

import java.util.List;

public interface WorkoutQuerydslRepository {

    List<WorkoutResponseDto> paginationWorkoutList(WorkoutSearchCondition cond);
}
