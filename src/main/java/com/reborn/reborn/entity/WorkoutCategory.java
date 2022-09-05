package com.reborn.reborn.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WorkoutCategory {
    BACK("등"),
    CHEST("가슴"),
    LOWER_BODY("하체"),
    CORE("하체");

    private String value;
}
