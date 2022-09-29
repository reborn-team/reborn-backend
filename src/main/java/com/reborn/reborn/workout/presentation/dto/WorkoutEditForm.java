package com.reborn.reborn.workout.presentation.dto;

import com.reborn.reborn.common.presentation.dto.FileDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutEditForm {

    private String workoutName;
    private String content;
    @Builder.Default
    private List<FileDto> files = new ArrayList<>();

}
