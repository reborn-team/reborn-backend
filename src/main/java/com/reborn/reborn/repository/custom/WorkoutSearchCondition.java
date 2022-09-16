package com.reborn.reborn.repository.custom;

import com.reborn.reborn.entity.WorkoutCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSearchCondition {
    Long id;
    String category;

    public WorkoutCategory of(){
        if(category == null){
            return null;
        }
        return WorkoutCategory.valueOf(category.toUpperCase());
    }
}
