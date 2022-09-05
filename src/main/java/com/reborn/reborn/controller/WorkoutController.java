package com.reborn.reborn.controller;

import com.reborn.reborn.dto.MemberRequestDto;
import com.reborn.reborn.dto.WorkoutDetailResponseDto;
import com.reborn.reborn.dto.WorkoutRequestDto;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.security.CurrentUser;
import com.reborn.reborn.service.MemberService;
import com.reborn.reborn.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @PostMapping("/workout")
    public ResponseEntity createWorkout(@CurrentUser Member member, @RequestBody WorkoutRequestDto workoutRequestDto){
        Long saveWorkoutId = workoutService.create(member, workoutRequestDto);
        log.info("save Workout");
        return ResponseEntity.status(HttpStatus.CREATED).body(saveWorkoutId);
    }

    @GetMapping("/workout/{workoutId}")
    public ResponseEntity getWorkout(@PathVariable Long workoutId, @CurrentUser Member member){
        WorkoutDetailResponseDto dto = workoutService.getMyWorkout(member, workoutId);
        log.info("get myWorkout");
        return ResponseEntity.ok().body(dto);
    }

}
