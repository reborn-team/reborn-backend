package com.reborn.reborn.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.reborn.reborn.entity.WorkoutCategory;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class WorkoutResponseDto {

    private Long id;
    private String workoutName;
    private String content;
    private String uploadFileName;
    private String originFileName;
    private WorkoutCategory workoutCategory;


    @QueryProjection
    public WorkoutResponseDto(Long id, String workoutName, String content, String uploadFileName, String originFileName, WorkoutCategory workoutCategory) {
        this.id = id;
        this.workoutName = workoutName;
        this.content = content;
        this.uploadFileName = uploadFileName;
        this.originFileName = originFileName;
        this.workoutCategory = workoutCategory;
    }

}
