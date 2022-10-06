package com.reborn.reborn.record.domain;

import com.reborn.reborn.common.domain.BaseTimeEntity;
import com.reborn.reborn.myworkout.domain.MyWorkout;
import com.reborn.reborn.workout.domain.WorkoutCategory;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Record extends BaseTimeEntity {

    @Id
    @Column(name = "record_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "my_workout_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    private MyWorkout myWorkout;

    private Integer total;
    @Enumerated(EnumType.STRING)
    private WorkoutCategory workoutCategory;

    public Record(MyWorkout myWorkout, Integer total, WorkoutCategory workoutCategory) {
        this.myWorkout = myWorkout;
        this.total = total;
        this.workoutCategory = workoutCategory;
    }

    public void addWeight(Integer total) {
        if (this.total != null) {
            this.total += total;
        }
    }
}
