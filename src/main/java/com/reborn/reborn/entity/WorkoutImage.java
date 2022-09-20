package com.reborn.reborn.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkoutImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originFileName;

    private String uploadFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id")
    private Workout workout;

<<<<<<< HEAD
    @Builder
    public WorkoutImage(String originFileName, String uploadFileName, Workout workout) {
        this.originFileName = originFileName;
        this.uploadFileName = uploadFileName;
=======
    public WorkoutImage(String originFileName, String uploadFileName) {
        this.originFileName = originFileName;
        this.uploadFileName = uploadFileName;

    }

    public void uploadToWorkout(Workout workout){
        if (this.workout != null) {
            this.workout.getWorkoutImages().remove(this);
        }
>>>>>>> 113f9386f9f64211c2345b78b02f41d117e6e735
        this.workout = workout;
        workout.getWorkoutImages().add(this);
    }
}
