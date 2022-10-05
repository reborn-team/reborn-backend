package com.reborn.reborn.record.presentation.dto;

import com.reborn.reborn.workout.domain.WorkoutCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class RecordRequest {

    @NotNull
    private Long myWorkoutId;
    @NotNull
    private Integer total;
    @NotNull
    private WorkoutCategory workoutCategory;

    public RecordRequest(Long myWorkoutId, Integer total, WorkoutCategory workoutCategory) {
        this.myWorkoutId = myWorkoutId;
        this.total = total;
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecordRequestList {

        private List<RecordRequest> recordList;
    }

}
