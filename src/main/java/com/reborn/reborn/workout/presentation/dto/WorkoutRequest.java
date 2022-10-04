package com.reborn.reborn.workout.presentation.dto;

import com.reborn.reborn.common.presentation.dto.FileDto;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutRequest {

    @NotNull
    private String workoutName;
    @NotNull
    private String content;
    @NotNull
    private String workoutCategory;
    @Builder.Default
    private List<FileDto> files = new ArrayList<>();

}
