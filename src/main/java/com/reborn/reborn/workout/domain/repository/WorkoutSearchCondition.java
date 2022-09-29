package com.reborn.reborn.workout.domain.repository;

import com.reborn.reborn.workout.domain.WorkoutCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSearchCondition {
    private Long id;
    private WorkoutCategory category;
    private String nickname;
    private String title;

}
