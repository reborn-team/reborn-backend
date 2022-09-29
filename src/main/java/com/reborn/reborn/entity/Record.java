package com.reborn.reborn.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private MyWorkout myWorkout;

    private Integer total;

    public Record(MyWorkout myWorkout, Integer total) {
        this.myWorkout = myWorkout;
        this.total = total;
    }

    public void addWeight(Integer total) {
        if (this.total != null) {
            this.total += total;
        }
    }
}
