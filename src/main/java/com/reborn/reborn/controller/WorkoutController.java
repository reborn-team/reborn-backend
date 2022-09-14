package com.reborn.reborn.controller;

import com.reborn.reborn.dto.FileDto;
import com.reborn.reborn.dto.WorkoutResponseDto;
import com.reborn.reborn.dto.WorkoutRequestDto;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.security.CurrentUser;
import com.reborn.reborn.service.WorkoutImageService;
import com.reborn.reborn.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/workout")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;
    private final WorkoutImageService workoutImageService;

    @PostMapping
    public ResponseEntity createWorkout(@CurrentUser Member member, @RequestBody WorkoutRequestDto workoutRequestDto){
        Workout workout = workoutService.create(member, workoutRequestDto);
        workoutRequestDto.getFiles().forEach(dto -> workoutImageService.create(dto,workout));
        log.info("save Workout");
        return ResponseEntity.status(HttpStatus.CREATED).body(workout.getId());
    }

    @GetMapping("/{workoutId}")
    public ResponseEntity<WorkoutResponseDto> getWorkout(@PathVariable Long workoutId, @CurrentUser Member member){
        WorkoutResponseDto dto = workoutService.getMyWorkout(member, workoutId);
        log.info("get myWorkout");
        return ResponseEntity.ok().body(dto);
    }

}
