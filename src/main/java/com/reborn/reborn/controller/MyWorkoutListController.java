package com.reborn.reborn.controller;

import com.reborn.reborn.entity.Member;
import com.reborn.reborn.security.LoginMember;
import com.reborn.reborn.service.MyWorkoutListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/workout-list")
@RequiredArgsConstructor
public class MyWorkoutListController {

    private final MyWorkoutListService myWorkoutListService;

    @PostMapping("/{workoutId}")
    public ResponseEntity addMyWorkoutList(@LoginMember Member member,@PathVariable Long workoutId) {
        Long myWorkoutId = myWorkoutListService.addWorkout(member, workoutId);
        return ResponseEntity.status(HttpStatus.CREATED).body(myWorkoutId);
    }
}
