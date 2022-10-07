package com.reborn.reborn.workout.domain.repository;

import com.reborn.reborn.member.domain.Member;
import com.reborn.reborn.member.domain.repository.MemberRepository;
import com.reborn.reborn.workout.domain.Workout;
import com.reborn.reborn.workout.domain.WorkoutImage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class WorkoutImageRepositoryTest {

    @Autowired
    private WorkoutImageRepository workoutImageRepository;

    @Autowired
    private WorkoutRepository workoutRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Test
    void deleteAllByWorkoutId() {
        Member member = Member.builder().build();
        memberRepository.save(member);
        Workout workout = Workout.builder().build();
        workoutRepository.save(workout);
        for (int i = 0; i < 10; i++) {
            WorkoutImage workoutImage = new WorkoutImage("" + i, "" + i);
            workoutImage.uploadToWorkout(workout);
            workoutImageRepository.save(workoutImage);
        }
        System.out.println("----------------------------------------------");
        workoutImageRepository.deleteAllByWorkout(workout);
        Assertions.assertThat(workoutImageRepository.findAll().size()).isEqualTo(0);

    }
}