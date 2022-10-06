package com.reborn.reborn.record.presentation.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.reborn.reborn.workout.domain.WorkoutCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static com.reborn.reborn.workout.domain.WorkoutCategory.*;

@Getter
@Setter
@NoArgsConstructor
public class RecordTodayResponse {
    private long back;
    private long chest;
    private long lowerBody;
    private long core;

    public RecordTodayResponse(Long back, Long chest, Long lowerBody, Long core) {
        this.back = back;
        this.chest = chest;
        this.lowerBody = lowerBody;
        this.core = core;
    }

    public void addTotal(WorkoutCategory category, Long total) {
        switch (category) {
            case BACK:
                this.back += total;
                break;
            case CHEST:
                this.chest += total;
                break;
            case LOWER_BODY:
                this.lowerBody += total;
                break;
            case CORE:
                this.core += total;
                break;
        }
    }
}
