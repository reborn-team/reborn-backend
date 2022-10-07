package com.reborn.reborn.workout.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WorkoutCategory {
    BACK("등"),
    CHEST("가슴"),
    LOWER_BODY("하체"),
    CORE("코어");

    private String value;
}
