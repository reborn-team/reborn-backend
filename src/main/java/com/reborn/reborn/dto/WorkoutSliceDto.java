package com.reborn.reborn.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
public class WorkoutSliceDto {
    private List<WorkoutListDto> page;

    @JsonProperty("hasNext")
    @Accessors(fluent = true)
    private boolean hasNext;

    public WorkoutSliceDto(List<WorkoutListDto> page) {
        this.page = page;
        hasNext = page.size() == 10;
    }
}
