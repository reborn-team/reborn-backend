package com.reborn.reborn.record.domain;

import com.reborn.reborn.common.domain.BaseTimeEntity;
import com.reborn.reborn.myworkout.domain.MyWorkout;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
