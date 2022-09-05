package com.reborn.reborn.dto;

import com.reborn.reborn.entity.WorkoutCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutDetailResponseDto {

    private String workoutName;
    private String content;
    private String filePath;
    private WorkoutCategory workoutCategory;

}
