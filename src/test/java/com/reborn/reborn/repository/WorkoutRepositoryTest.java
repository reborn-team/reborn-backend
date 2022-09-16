package com.reborn.reborn.repository;

import com.reborn.reborn.dto.WorkoutResponseDto;
import com.reborn.reborn.repository.custom.WorkoutSearchCondition;
import com.reborn.reborn.entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class WorkoutRepositoryTest {

    @Autowired
    WorkoutRepository workoutRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    WorkoutImageRepository workoutImageRepository;

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
        for (int i = 0; i < 4; i++) {
            WorkoutImage workoutImage = new WorkoutImage(i+"",i+"",workout);
            workoutImageRepository.save(workoutImage);
        }
        memberRepository.save(member);
        workoutRepository.save(workout);
        Optional<Workout> findWorkout = workoutRepository.findById(workout.getId());
        assertThat(workout.getWorkoutName()).isEqualTo(findWorkout.get().getWorkoutName());
        List<WorkoutResponseDto> byIdAndMemberId = workoutRepository.paginationWorkoutList(new WorkoutSearchCondition(2L,"BACK"));
        for (WorkoutResponseDto workoutResponseDto : byIdAndMemberId) {
            System.out.println("workoutResponseDto = " + workoutResponseDto);
        }
    }



}