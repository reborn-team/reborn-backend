package com.reborn.reborn.record.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RecordRequest {

    private Long myWorkoutId;
    private Integer total;

    public RecordRequest(Long myWorkoutId, Integer total) {
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
