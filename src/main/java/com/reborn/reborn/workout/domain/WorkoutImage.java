package com.reborn.reborn.workout.domain;

import com.reborn.reborn.workout.domain.Workout;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkoutImage {

    @Id
    @Column(name = "workout_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originFileName;

    private String uploadFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id")
    private Workout workout;

    public WorkoutImage(String originFileName, String uploadFileName) {
        this.originFileName = originFileName;
        this.uploadFileName = uploadFileName;

    }

    public void uploadToWorkout(Workout workout) {
        if (this.workout != null) {
            this.workout.getWorkoutImages().remove(this);
        }
        this.workout = workout;
        workout.getWorkoutImages().add(this);
    }
}
