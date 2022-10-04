package com.reborn.reborn.workout.presentation.dto;

import com.reborn.reborn.common.presentation.dto.FileDto;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutEditForm {

    @Length(min = 1, max = 20)
    private String workoutName;
    @Length(min = 5)
    private String content;
    @Builder.Default
    private List<FileDto> files = new ArrayList<>();

}
