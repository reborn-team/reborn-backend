package com.reborn.reborn.myworkout.domain;

import com.reborn.reborn.common.domain.BaseTimeEntity;
import com.reborn.reborn.workout.domain.Workout;
import com.reborn.reborn.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyWorkout extends BaseTimeEntity {

    @Id
    @Column(name = "my_workout_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "workout_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Workout workout;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public MyWorkout(Workout workout, Member member) {
        this.workout = workout;
        this.member = member;
    }
}
