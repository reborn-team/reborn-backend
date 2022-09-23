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
public class WorkoutResponseEditForm {

    private String workoutName;
    private String content;
    @Builder.Default
    private List<FileDto> files = new ArrayList<>();
    private WorkoutCategory workoutCategory;

    public static WorkoutResponseEditForm ofResponse(String workoutName, String content, List<WorkoutImage> images, WorkoutCategory workoutCategory) {
        List<FileDto> fileDtos = new ArrayList<>();
        images.forEach(image -> fileDtos.add(new FileDto(image.getOriginFileName(), image.getOriginFileName())));
        return new WorkoutResponseEditForm(workoutName, content, fileDtos, workoutCategory);
    }
}
