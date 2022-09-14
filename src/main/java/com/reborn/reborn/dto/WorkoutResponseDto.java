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
public class WorkoutResponseDto {

    private Long id;
    private String workoutName;
    private String content;
    private String filePath;
    private WorkoutCategory workoutCategory;

    //TODO 수정요망
    public WorkoutResponseDto toDto(Workout workout){
        return WorkoutResponseDto.builder()
                .id(workout.getId())
                .workoutCategory(workout.getWorkoutCategory())
                .content(workout.getContent())
                .workoutName(workout.getWorkoutName()).build();
    }


}
