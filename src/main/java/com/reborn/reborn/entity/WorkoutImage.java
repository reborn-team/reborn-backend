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

    @Builder
    public WorkoutImage(String originFileName, String uploadFileName, Workout workout) {
        this.originFileName = originFileName;
        this.uploadFileName = uploadFileName;
        this.workout = workout;
    }
}
