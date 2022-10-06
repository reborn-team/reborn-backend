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
@ToString
@NoArgsConstructor
public class RecordTodayResponse {
    private int back;
    private int chest;
    private int lowerBody;
    private int core;

    @QueryProjection
    public RecordTodayResponse(Integer back, Integer chest, Integer lowerBody, Integer core) {
        this.back = back;
        this.chest = chest;
        this.lowerBody = lowerBody;
        this.core = core;
    }

    public void addTotal(WorkoutCategory category, Integer total) {
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
