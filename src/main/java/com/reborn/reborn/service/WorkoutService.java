package com.reborn.reborn.service;

import com.reborn.reborn.dto.WorkoutRequestDto;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.Workout;

import java.util.List;

public interface WorkoutService {

    void create(Member member, WorkoutRequestDto dto);
}
