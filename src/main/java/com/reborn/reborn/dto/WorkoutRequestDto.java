package com.reborn.reborn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutRequestDto {

    private String workoutName;
    private String content;
    private String fileName;
    private String workoutCategory;

}
