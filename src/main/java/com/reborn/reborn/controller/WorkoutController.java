package com.reborn.reborn.controller;

import com.reborn.reborn.dto.WorkoutListDto;
import com.reborn.reborn.dto.WorkoutPageDto;
import com.reborn.reborn.dto.WorkoutResponseDto;
import com.reborn.reborn.dto.WorkoutRequestDto;
import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.repository.custom.WorkoutSearchCondition;
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

    @GetMapping
    public ResponseEntity<WorkoutPageDto> getWorkoutList(@ModelAttribute WorkoutSearchCondition cond) {
        List<WorkoutListDto> responseDto = workoutService.pagingWorkout(cond);
        return ResponseEntity.ok().body(new WorkoutPageDto(responseDto));
    }

    @PostMapping
    public ResponseEntity<Long> createWorkout(@RequestBody WorkoutRequestDto workoutRequestDto){
        Long saveWorkoutId = workoutService.create(workoutRequestDto);
        Workout workout = workoutService.findWorkoutById(saveWorkoutId);
        workoutRequestDto.getFiles().forEach(dto -> workoutImageService.create(dto,workout));
        log.info("save Workout");
        return ResponseEntity.status(HttpStatus.CREATED).body(saveWorkoutId);
    }

    @GetMapping("/{workoutId}")
    public ResponseEntity<WorkoutResponseDto> getWorkout(@PathVariable Long workoutId){
        WorkoutResponseDto dto =  workoutService.getWorkoutDto(workoutId);
        log.info("get myWorkout");
        return ResponseEntity.ok().body(dto);
    }


}
