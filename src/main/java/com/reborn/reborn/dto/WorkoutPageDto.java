package com.reborn.reborn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WorkoutPageDto {
    private List<WorkoutResponseDto> page;
    private boolean hasNext;

    public WorkoutPageDto(List<WorkoutResponseDto> page) {
        this.page = page;
        hasNext = page.size()==10;
    }
}
