package com.reborn.reborn.myworkout.presentation;

import com.reborn.reborn.myworkout.presentation.dto.MyWorkoutDto;
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

import static com.reborn.reborn.myworkout.presentation.dto.MyWorkoutDto.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/my-workout")
@RequiredArgsConstructor
public class MyWorkoutController {

    private final MyWorkoutService myWorkoutService;

    @GetMapping
    public ResponseEntity<Slice<MyWorkoutDto>> getList(@LoginMember Long memberId, @ModelAttribute WorkoutSearchCondition cond) {
        log.info("memberId={}",memberId);
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

    @GetMapping("/program")
    public ResponseEntity<MyProgramList> getMyProgram(@LoginMember Long memberId, @RequestParam("category") WorkoutCategory workoutCategory) {
        MyProgramList myProgram = myWorkoutService.getMyProgram(memberId, workoutCategory);
        return ResponseEntity.ok(myProgram);
    }

}
