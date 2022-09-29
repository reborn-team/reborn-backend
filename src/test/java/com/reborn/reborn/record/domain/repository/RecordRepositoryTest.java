package com.reborn.reborn.record.domain.repository;

import com.reborn.reborn.member.domain.Member;
import com.reborn.reborn.member.domain.repository.MemberRepository;
import com.reborn.reborn.myworkout.domain.MyWorkout;
import com.reborn.reborn.myworkout.domain.repository.MyWorkoutRepository;
import com.reborn.reborn.record.domain.Record;
import com.reborn.reborn.record.domain.repository.RecordRepository;
import com.reborn.reborn.workout.domain.Workout;
import com.reborn.reborn.workout.domain.repository.WorkoutRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class RecordRepositoryTest {

    @Autowired
    RecordRepository recordRepository;

    @Autowired
    MyWorkoutRepository myWorkoutRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    WorkoutRepository workoutRepository;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("saveAll 을 통해 전체 저장한다.")
    void saveAll() {
        Member saveMember = memberRepository.save(Member.builder().build());
        Workout saveWorkout = workoutRepository.save(Workout.builder().member(saveMember).build());
        MyWorkout saveMyWorkout = myWorkoutRepository.save(new MyWorkout(saveWorkout, saveMember));
        em.flush();
        em.clear();

        List<Record> records = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            records.add(new Record(saveMyWorkout, i));
        }

        List<Record> saveRecord = recordRepository.saveAll(records);
        assertThat(records).isEqualTo(saveRecord);

    }

    @Test
    @DisplayName("오늘 등록한 Record를 출력한다.")
    void findByToday() {
        Member saveMember = memberRepository.save(Member.builder().build());
        Workout saveWorkout = workoutRepository.save(Workout.builder().member(saveMember).build());
        MyWorkout saveMyWorkout = myWorkoutRepository.save(new MyWorkout(saveWorkout, saveMember));
        recordRepository.save(new Record(saveMyWorkout, 10));
        em.flush();
        em.clear();

        Record record = recordRepository.findByToday(saveMyWorkout.getId()).orElseThrow();

        assertThat(record.getMyWorkout().getId()).isEqualTo(saveMyWorkout.getId());

    }

}