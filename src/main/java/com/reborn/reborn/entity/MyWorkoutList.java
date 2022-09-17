package com.reborn.reborn.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyWorkoutList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "workout_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Workout workout;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public MyWorkoutList(Workout workout, Member member) {
        this.workout = workout;
        this.member = member;
    }
}
