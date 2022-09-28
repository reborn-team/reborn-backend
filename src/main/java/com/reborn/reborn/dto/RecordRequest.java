package com.reborn.reborn.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecordRequest {

    private Long myWorkoutId;
    private Integer total;

    public RecordRequest(Long myWorkoutId, Integer total) {
        this.myWorkoutId = myWorkoutId;
        this.total = total;
    }
}
