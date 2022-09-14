package com.reborn.reborn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutRequestDto {

    private String workoutName;
    private String content;
    private String workoutCategory;
    @Builder.Default
    private List<FileDto> files = new ArrayList<>();

}
