package com.reborn.reborn.dto;

import com.reborn.reborn.entity.WorkoutCategory;
import com.reborn.reborn.entity.WorkoutImage;
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
public class WorkoutRequestEditForm {

    private String workoutName;
    private String content;
    @Builder.Default
    private List<FileDto> files = new ArrayList<>();

}
