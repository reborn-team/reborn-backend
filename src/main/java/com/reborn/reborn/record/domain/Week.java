package com.reborn.reborn.record.domain;

import lombok.Getter;

@Getter
public enum Week {
    SUNDAY(1),
    MONDAY(2),
    TUESDAY(3),
    WEDNESDAY(4),
    THURSDAY(5),
    FRIDAY(6),
    SATURDAY(7);

    private int value;

    Week(int value) {
        this.value = value;
    }
}
