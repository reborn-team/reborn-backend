package com.reborn.reborn.entity;

import javax.persistence.*;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@ToString(exclude = "workoutImages")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Workout extends BaseTimeEntity {

    @Id
    @Column(name = "workout_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String workoutName;

    private String content;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Enumerated(EnumType.STRING)
    private WorkoutCategory workoutCategory;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutImage> workoutImages = new ArrayList<>();

    @Builder
    public Workout(String workoutName, String content, WorkoutCategory workoutCategory, Member member) {
        this.workoutName = workoutName;
        this.content = content;
        this.member = member;
        this.workoutCategory = workoutCategory;
    }

    public void modifyWorkout(String workoutName, String content) {
        this.workoutName = workoutName;
        this.content = content;
    }
}
