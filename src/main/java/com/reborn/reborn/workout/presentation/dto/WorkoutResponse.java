package com.reborn.reborn.workout.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.reborn.reborn.common.presentation.dto.FileDto;
import com.reborn.reborn.workout.domain.Workout;
import com.reborn.reborn.workout.domain.WorkoutCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class WorkoutResponse {

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

    @JsonProperty("isAdd")
    private boolean isAdd;


    @Builder
    public WorkoutResponse(Long id, String workoutName, List<FileDto> files, String content, WorkoutCategory workoutCategory,
                           Long memberId, String memberNickname, boolean isAdd, boolean isAuthor) {
        this.id = id;
        this.workoutName = workoutName;
        this.content = content;
        this.files = files;
        this.workoutCategory = workoutCategory;
        this.memberId = memberId;
        this.isAdd = isAdd;
        this.memberNickname = memberNickname;
        this.isAuthor = isAuthor;
    }

    public static WorkoutResponse of(Workout workout, boolean isAdd, boolean isAuthor) {

        List<FileDto> fileDtos = new ArrayList<>();
        workout.getWorkoutImages().forEach(image -> fileDtos.add(new FileDto(image.getOriginFileName(), image.getUploadFileName())));

        return WorkoutResponse.builder()
                .id(workout.getId())
                .workoutName(workout.getWorkoutName())
                .workoutCategory(workout.getWorkoutCategory())
                .content(workout.getContent())
                .memberId(workout.getMember().getId())
                .memberNickname(workout.getMember().getNickname())
                .isAdd(isAdd)
                .isAuthor(isAuthor)
                .files(fileDtos)
                .build();
    }


    @Getter
    public static class WorkoutIdResponse {
        private Long saveId;

        public WorkoutIdResponse(Long saveId) {
            this.saveId = saveId;
        }
    }

    @Getter
    public static class WorkoutListResponse {
        private List<WorkoutPreviewResponse> list;

        public WorkoutListResponse(List<WorkoutPreviewResponse> list) {
            this.list = list;
        }
    }
}
