package com.reborn.reborn.controller;

import com.reborn.reborn.entity.Member;
import com.reborn.reborn.security.LoginMember;
import com.reborn.reborn.service.MyWorkoutListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/my-workout")
@RequiredArgsConstructor
public class MyWorkoutListController {

    private final MyWorkoutListService myWorkoutListService;

    @PostMapping("/{workoutId}")
    public ResponseEntity addMyWorkoutList(@LoginMember Member member,@PathVariable Long workoutId) {
        Long myWorkoutId = myWorkoutListService.addWorkout(member, workoutId);
        return ResponseEntity.status(HttpStatus.CREATED).body(myWorkoutId);
    }




}
