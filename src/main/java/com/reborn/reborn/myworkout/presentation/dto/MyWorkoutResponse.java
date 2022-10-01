package com.reborn.reborn.myworkout.presentation.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
public class MyWorkoutResponse {

    private Long myWorkoutId;
    private Long workoutId;

    private String workoutName;

    private String uploadFileName;

    @QueryProjection
    public MyWorkoutResponse(Long myWorkoutId, String workoutName, String uploadFileName) {
        this.myWorkoutId = myWorkoutId;
        this.workoutName = workoutName;
        this.uploadFileName = uploadFileName;
    }

    @QueryProjection
    public MyWorkoutResponse(Long myWorkoutId, Long workoutId, String workoutName, String uploadFileName) {
        this.myWorkoutId = myWorkoutId;
        this.workoutId = workoutId;
        this.workoutName = workoutName;
        this.uploadFileName = uploadFileName;
    }

    @Getter
    public static class MyWorkoutList {

        private List<MyWorkoutResponse> list;

        public MyWorkoutList(List<MyWorkoutResponse> list) {
            this.list = list;
        }
    }


    @Getter
    public static class MyWorkoutIdResponse {
        private Long saveId;

        public MyWorkoutIdResponse(Long saveId) {
            this.saveId = saveId;
        }
    }
}

