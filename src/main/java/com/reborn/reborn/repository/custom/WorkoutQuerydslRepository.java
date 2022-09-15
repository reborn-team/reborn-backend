package com.reborn.reborn.repository.custom;

import com.reborn.reborn.dto.WorkoutResponseDto;

public interface WorkoutQuerydslRepository {

    WorkoutResponseDto findByIdAndMemberId(Long id, Long memberId);
}
