package com.reborn.reborn.dto;

import com.reborn.reborn.entity.Record;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecordRequest {

    private Long myWorkoutId;
    private Integer weight;

    public RecordRequest(Long myWorkoutId, Integer weight) {
        this.myWorkoutId = myWorkoutId;
        this.weight = weight;
    }
}
