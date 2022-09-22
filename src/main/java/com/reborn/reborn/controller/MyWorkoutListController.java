package com.reborn.reborn.controller;

import com.reborn.reborn.security.LoginMember;
import com.reborn.reborn.service.MyWorkoutListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/v1/my-workout")
@RequiredArgsConstructor
public class MyWorkoutListController {

    private final MyWorkoutListService myWorkoutListService;

    @PostMapping("/{workoutId}")
    public ResponseEntity addMyWorkoutList(@LoginMember Long memberId,@PathVariable Long workoutId) {
        //TODO 이미 등록된 운동이면 추가 못하게해야함
        Long myWorkoutId = myWorkoutListService.addWorkout(memberId, workoutId);
        //TODO Location URI 추가 해야함
        return ResponseEntity.created(URI.create("/api/v1/members/" + myWorkoutId)).body(myWorkoutId);
    }




}
