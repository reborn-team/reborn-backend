package com.reborn.reborn.myworkout.presentation;

import com.reborn.reborn.myworkout.presentation.dto.MyWorkoutResponse;
import com.reborn.reborn.common.presentation.dto.Slice;
import com.reborn.reborn.workout.domain.WorkoutCategory;
import com.reborn.reborn.workout.domain.repository.custom.WorkoutSearchCondition;
import com.reborn.reborn.security.domain.LoginMember;
import com.reborn.reborn.myworkout.application.MyWorkoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.reborn.reborn.myworkout.presentation.dto.MyWorkoutResponse.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/workouts/me")
@RequiredArgsConstructor
public class MyWorkoutController {

    private final MyWorkoutService myWorkoutService;

    @GetMapping
    public ResponseEntity<Slice<MyWorkoutResponse>> getList(@LoginMember Long memberId, @ModelAttribute WorkoutSearchCondition cond) {
        log.info("memberId={}",memberId);
        return ResponseEntity.ok(myWorkoutService.getMyWorkoutList(cond, memberId));
    }

    @PostMapping("/{workoutId}")
    public ResponseEntity<MyWorkoutIdResponse> addMyWorkoutList(@LoginMember Long memberId, @PathVariable Long workoutId) {
        Long myWorkoutId = myWorkoutService.addMyWorkout(memberId, workoutId);
        return ResponseEntity.created(URI.create("/api/v1/workouts/me/" + myWorkoutId)).body(new MyWorkoutIdResponse(myWorkoutId));
    }

    @DeleteMapping("/{workoutId}")
    public ResponseEntity<Void> deleteWorkout(@LoginMember Long memberId, @PathVariable Long workoutId) {
        myWorkoutService.deleteMyWorkout(memberId, workoutId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/program")
    public ResponseEntity<MyWorkoutList> getMyProgram(@LoginMember Long memberId, @RequestParam("category") WorkoutCategory workoutCategory) {
        MyWorkoutList myProgram = myWorkoutService.getMyProgram(memberId, workoutCategory);
        return ResponseEntity.ok(myProgram);
    }

}
