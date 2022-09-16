package com.reborn.reborn.repository.custom;

import com.reborn.reborn.entity.WorkoutCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSearchCondition {
    Long workoutId;
    String workoutCategory;

    public WorkoutCategory of(){
        if(workoutCategory == null){
            return null;
        }
        return WorkoutCategory.valueOf(workoutCategory.toUpperCase());
    }
}
