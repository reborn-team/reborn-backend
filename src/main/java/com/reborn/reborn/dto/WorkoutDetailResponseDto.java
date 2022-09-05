package com.reborn.reborn.dto;

import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.entity.WorkoutCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutDetailResponseDto {

    private String workoutName;
    private String content;
    private String filePath;
    private WorkoutCategory workoutCategory;

    public WorkoutDetailResponseDto toDto(Workout workout){
        return WorkoutDetailResponseDto.builder()
                .workoutCategory(workout.getWorkoutCategory())
                .content(workout.getContent())
                .filePath(workout.getFilePath())
                .workoutName(workout.getWorkoutName()).build();
    }

}
