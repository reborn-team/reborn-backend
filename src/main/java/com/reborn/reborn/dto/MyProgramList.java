package com.reborn.reborn.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class MyProgramList {

    private List<MyWorkoutDto> list;

    public MyProgramList(List<MyWorkoutDto> list) {
        this.list = list;
    }
}
