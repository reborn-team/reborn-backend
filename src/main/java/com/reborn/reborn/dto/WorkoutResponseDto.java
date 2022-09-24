package com.reborn.reborn.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.entity.WorkoutCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class WorkoutResponseDto {

    private Long id;
    private String workoutName;
    private String content;
    private List<FileDto> files = new ArrayList<>();
    private WorkoutCategory workoutCategory;

    private Long memberId;

    private String memberNickname;

    @JsonProperty("isAuthor")
    @Accessors(fluent = true)
    private boolean isAuthor;


    @Builder
    public WorkoutResponseDto(Long id, String workoutName, List<FileDto> files, String content, WorkoutCategory workoutCategory, Long memberId, String memberNickname) {
        this.id = id;
        this.workoutName = workoutName;
        this.content = content;
        this.files = files;
        this.workoutCategory = workoutCategory;
        this.memberId = memberId;
        this.memberNickname = memberNickname;
    }

    public static WorkoutResponseDto of(Workout workout) {
        List<FileDto> fileDtos = new ArrayList<>();
        workout.getWorkoutImages().forEach(image -> fileDtos.add(new FileDto(image.getOriginFileName(), image.getUploadFileName())));
        return WorkoutResponseDto.builder()
                .id(workout.getId())
                .workoutName(workout.getWorkoutName())
                .workoutCategory(workout.getWorkoutCategory())
                .content(workout.getContent())
                .memberId(workout.getMember().getId())
                .memberNickname(workout.getMember().getNickname())
                .files(fileDtos)
                .build();
    }

    public void isAuthor(Long memberId) {
        this.isAuthor = this.memberId == memberId;
    }
}
