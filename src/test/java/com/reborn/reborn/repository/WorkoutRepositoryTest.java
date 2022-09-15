package com.reborn.reborn.repository;

import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.MemberRole;
import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.entity.WorkoutCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class WorkoutRepositoryTest {

    @Autowired
    WorkoutRepository workoutRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("운동을 생성한다.")
    void createTest(){
        Member member= Member.builder()
                .email("email")
                .memberRole(MemberRole.USER)
                .password("A")
                .name("han")
                .build();
        Workout workout = Workout.builder()
                .workoutName("pull up")
                .content("등을 펼쳐서 당깁니다.")
                .workoutCategory(WorkoutCategory.BACK).build();
        memberRepository.save(member);
        workoutRepository.save(workout);
        Optional<Workout> findWorkout = workoutRepository.findById(workout.getId());
        assertThat(workout.getWorkoutName()).isEqualTo(findWorkout.get().getWorkoutName());
    }

}