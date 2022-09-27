package com.reborn.reborn.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class MyWorkoutDto {

    private Long myWorkoutId;

    private String workoutName;

    private String uploadFileName;

    @QueryProjection
    public MyWorkoutDto(Long myWorkoutId, String workoutName, String uploadFileName) {
        this.myWorkoutId = myWorkoutId;
        this.workoutName = workoutName;
        this.uploadFileName = uploadFileName;
    }
}

