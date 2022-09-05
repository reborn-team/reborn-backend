package com.reborn.reborn.entity;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Workout extends BaseTimeEntity{

    @Id
    @Column(name = "workout_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String workoutName;

    private String content;

    //TODO 이미지 파일을 어떻게 처리해야할지 공부하고 수정할 예정
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private WorkoutCategory workoutCategory;

    @Builder
    public Workout(String workoutName, String content, String filePath,Member member, WorkoutCategory workoutCategory) {
        this.workoutName = workoutName;
        this.content = content;
        this.filePath = filePath;
        this.workoutCategory = workoutCategory;
        this.member = member;
    }
}
