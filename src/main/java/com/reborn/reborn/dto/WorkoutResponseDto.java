package com.reborn.reborn.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.entity.WorkoutCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class WorkoutResponseDto {

    private Long id;
    private String workoutName;
    private String content;
    @Builder.Default
    private List<FileDto> files = new ArrayList<>();
    private WorkoutCategory workoutCategory;

    @QueryProjection
    public WorkoutResponseDto(Long id, String workoutName, String content, List<FileDto> files, WorkoutCategory workoutCategory) {
        this.id = id;
        this.workoutName = workoutName;
        this.content = content;
        this.files = files;
        this.workoutCategory = workoutCategory;
    }

    //TODO 수정요망
    public static WorkoutResponseDto toDto(Workout workout){
        return WorkoutResponseDto.builder()
                .id(workout.getId())
                .workoutCategory(workout.getWorkoutCategory())
                .content(workout.getContent())
                .workoutName(workout.getWorkoutName()).build();
    }


}
