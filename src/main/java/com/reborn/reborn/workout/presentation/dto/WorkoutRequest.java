package com.reborn.reborn.workout.presentation.dto;

import com.reborn.reborn.common.presentation.dto.FileDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutRequest {

    private String workoutName;
    private String content;
    private String workoutCategory;
    @Builder.Default
    private List<FileDto> files = new ArrayList<>();

}
