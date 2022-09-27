package com.reborn.reborn.controller;

import com.reborn.reborn.dto.WorkoutSliceDto;
import com.reborn.reborn.repository.custom.WorkoutSearchCondition;
import com.reborn.reborn.security.LoginMember;
import com.reborn.reborn.service.MyWorkoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/v1/my-workout")
@RequiredArgsConstructor
public class MyWorkoutController {

    private final MyWorkoutService myWorkoutService;

    @GetMapping
    public ResponseEntity<WorkoutSliceDto> getList(@LoginMember Long memberId, @ModelAttribute WorkoutSearchCondition cond) {
        return ResponseEntity.ok(myWorkoutService.getMyWorkoutList(cond, memberId));
    }
    @PostMapping("/{workoutId}")
    public ResponseEntity addMyWorkoutList(@LoginMember Long memberId, @PathVariable Long workoutId) {
        Long myWorkoutId = myWorkoutService.addMyWorkout(memberId, workoutId);
        //TODO Location URI 추가 해야함
        return ResponseEntity.created(URI.create("/api/v1/my-workout/" + myWorkoutId)).body(myWorkoutId);
    }


    @DeleteMapping("/{workoutId}")
    public ResponseEntity<Void> deleteWorkout(@LoginMember Long memberId, @PathVariable Long workoutId) {
        myWorkoutService.deleteMyWorkout(memberId, workoutId);
        return ResponseEntity.noContent().build();
    }

}
