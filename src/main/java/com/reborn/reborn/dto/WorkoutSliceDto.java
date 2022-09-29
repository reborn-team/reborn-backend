package com.reborn.reborn.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@NoArgsConstructor
public class WorkoutSliceDto<T> {
    private List<T> page;

    @JsonProperty("hasNext")
    @Accessors(fluent = true)
    private boolean hasNext;

    public WorkoutSliceDto(List<T> page) {
        this.page = page;
        hasNext = page.size() == 10;
    }
}
