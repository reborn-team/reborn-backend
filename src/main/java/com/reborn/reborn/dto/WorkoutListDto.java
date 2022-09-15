package com.reborn.reborn.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class WorkoutListDto {

    private Long workoutId;
    private String workoutName;
    private String fileFullPath;

    @QueryProjection
    public WorkoutListDto(Long workoutId, String workoutName, String fileFullPath) {
        this.workoutId = workoutId;
        this.workoutName = workoutName;
        this.fileFullPath = fileFullPath;
    }
}
