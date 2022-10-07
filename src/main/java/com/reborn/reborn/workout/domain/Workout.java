package com.reborn.reborn.workout.domain;

import javax.persistence.*;

import com.reborn.reborn.common.domain.BaseTimeEntity;
import com.reborn.reborn.member.domain.Member;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@ToString(exclude = {"workoutImages", "member"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Workout extends BaseTimeEntity {

    @Id
    @Column(name = "workout_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String workoutName;
    @Column(nullable = false)
    private String content;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkoutCategory workoutCategory;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutImage> workoutImages = new ArrayList<>();

    private Long addCount;

    @Builder
    public Workout(String workoutName, String content, WorkoutCategory workoutCategory, Member member) {
        this.workoutName = workoutName;
        this.content = content;
        this.member = member;
        this.workoutCategory = workoutCategory;
        this.addCount = 0L;
    }

    public void modifyWorkout(String workoutName, String content) {
        this.workoutName = workoutName;
        this.content = content;
    }

    public void plusAddCount() {
        this.addCount += 1L;
    }

    public void minusAddCount() {
        this.addCount -= 1L;
    }
}
