package com.reborn.reborn.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class WorkoutListDto {

    private Long workoutId;
    private String workoutName;
    private String uploadFileName;

    @QueryProjection
    public WorkoutListDto(Long workoutId, String workoutName, String uploadFileName) {
        this.workoutId = workoutId;
        this.workoutName = workoutName;
        this.uploadFileName = uploadFileName;
    }
}
