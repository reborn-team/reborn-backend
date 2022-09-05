package com.reborn.reborn.repository;

import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.MemberRole;
import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.entity.WorkoutCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class WorkoutRepositoryTest {

    @Autowired
    WorkoutRepository workoutRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
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
                .filePath("imagePath")
                .member(member)
                .workoutCategory(WorkoutCategory.BACK).build();
        workoutRepository.save(workout);
    }
}