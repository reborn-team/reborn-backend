package com.reborn.reborn.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WorkoutPageDto {
    private List<WorkoutListDto> page;
    private boolean hasNext;

    public WorkoutPageDto(List<WorkoutListDto> page) {
        this.page = page;
        hasNext = page.size() == 10;
    }
}
