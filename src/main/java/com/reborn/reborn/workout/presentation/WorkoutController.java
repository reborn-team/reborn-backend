package com.reborn.reborn.workout.presentation;

import com.reborn.reborn.common.presentation.dto.Slice;
import com.reborn.reborn.workout.domain.Workout;
import com.reborn.reborn.workout.domain.WorkoutCategory;
import com.reborn.reborn.workout.domain.repository.custom.WorkoutSearchCondition;
import com.reborn.reborn.security.domain.LoginMember;
import com.reborn.reborn.workout.application.WorkoutService;
import com.reborn.reborn.workout.presentation.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.reborn.reborn.workout.presentation.dto.WorkoutResponse.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @GetMapping
    public ResponseEntity<Slice<WorkoutPreviewResponse>> getWorkoutList(@ModelAttribute WorkoutSearchCondition cond) {
        return ResponseEntity.ok().body(workoutService.getPagingWorkout(cond));
    }

    @PostMapping
    public ResponseEntity<WorkoutIdResponse> createWorkout(@LoginMember Long memberId, @RequestBody @Valid WorkoutRequest dto) {
        Workout workout = workoutService.create(memberId, dto);
        log.info("save Workout");
        return ResponseEntity.created(URI.create("/api/v1/workouts/" + workout.getId())).body(new WorkoutIdResponse(workout.getId()));
    }

    @GetMapping("/{workoutId}")
    public ResponseEntity<WorkoutResponse> getWorkoutDetail(@LoginMember Long memberId, @PathVariable Long workoutId) {

        WorkoutResponse dto = workoutService.getWorkoutDetailDto(memberId, workoutId);
        log.info("memberId={}", memberId);
        log.info("get myWorkout");
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{workoutId}")
    public ResponseEntity<Void> deleteWorkout(@LoginMember Long memberId, @PathVariable Long workoutId) {
        workoutService.deleteWorkout(memberId, workoutId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{workoutId}")
    public ResponseEntity<Void> editWorkout(@LoginMember Long memberId, @PathVariable Long workoutId, @RequestBody @Valid WorkoutEditForm form) {
        workoutService.updateWorkout(memberId, workoutId, form);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rank")
    public ResponseEntity<WorkoutListResponse> getRankList(@RequestParam(value = "category",required = false) WorkoutCategory workoutCategory) {
        List<WorkoutPreviewResponse> list = workoutService.getWorkoutListByRank(workoutCategory);
        return ResponseEntity.ok().body(new WorkoutListResponse(list));
    }
}
