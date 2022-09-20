package com.reborn.reborn.repository;

import com.reborn.reborn.dto.WorkoutListDto;
<<<<<<< HEAD
import com.reborn.reborn.dto.WorkoutResponseDto;
import com.reborn.reborn.repository.custom.WorkoutSearchCondition;
=======
import com.reborn.reborn.dto.WorkoutSliceDto;
>>>>>>> 113f9386f9f64211c2345b78b02f41d117e6e735
import com.reborn.reborn.entity.*;
import com.reborn.reborn.repository.custom.WorkoutSearchCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @BeforeEach
    void before(){
        Member member = createMember();
        memberRepository.save(member);
        for (int i = 0; i < 11; i++) {
            Workout workout = createWorkout(member, i + "");
            workoutRepository.save(workout);
        }
    }

    @Test
    @DisplayName("운동을 생성한다.")
    void createTest() {
        Member member = createMember();
        Workout workout = createWorkout(member, "");
        WorkoutImage workoutImage = new WorkoutImage("", "");
        workoutImage.uploadToWorkout(workout);
        workoutImageRepository.save(workoutImage);
        workoutRepository.save(workout);

        assertThat(workout.getMember()).isEqualTo(member);
        assertThat(workoutImage.getWorkout()).isEqualTo(workout);

    }


    @Test
    @DisplayName("운동 목록을 Dto에 맞게 조회하고 10개를 반환한다.")
    void sliceTest(){
        List<WorkoutListDto> result = workoutRepository.paginationWorkoutList(new WorkoutSearchCondition(null, null));
        assertThat(result.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("운동 목록을 조회하고 값이 10개면 true를 반환한다.")
    void pageTest(){
        List<WorkoutListDto> result = workoutRepository.paginationWorkoutList(new WorkoutSearchCondition(null, null));
        WorkoutSliceDto page = new WorkoutSliceDto(result);
        assertThat(page.hasNext()).isEqualTo(true);
    }

    public static Member createMember() {
        Member member = Member.builder()
                .email("email")
                .memberRole(MemberRole.USER)
                .password("A")
                .name("han")
                .build();
<<<<<<< HEAD
        for (int i = 0; i < 100; i++) {
            Workout workout = Workout.builder()
                    .workoutName("pull up"+i)
                    .content("등을 펼쳐서 당깁니다.")
                    .workoutCategory(WorkoutCategory.BACK).build();
            WorkoutImage workoutImage = new WorkoutImage(i+"",i+"",workout);
            workoutImageRepository.save(workoutImage);
            workoutRepository.save(workout);
        }
        memberRepository.save(member);
//        Optional<Workout> findWorkout = workoutRepository.findById(workout.getId());
//        assertThat(workout.getWorkoutName()).isEqualTo(findWorkout.get().getWorkoutName());
        List<WorkoutListDto> byIdAndMemberId = workoutRepository.paginationWorkoutList(new WorkoutSearchCondition(89L,"BACK"));
        for (WorkoutListDto workoutResponseDto : byIdAndMemberId) {
            System.out.println("workoutResponseDto = " + workoutResponseDto);
        }
        WorkoutResponseDto workoutDetail = workoutRepository.getWorkoutDetail(1L);
        System.out.println("workoutDetail = " + workoutDetail);
=======
        return member;
>>>>>>> 113f9386f9f64211c2345b78b02f41d117e6e735
    }

    private Workout createWorkout(Member member,String text) {
        Workout workout = Workout.builder()
                .workoutName("pull up" + text)
                .member(member)
                .content("등을 펼쳐서 당깁니다." + text)
                .workoutCategory(WorkoutCategory.BACK).build();
        return workout;
    }

}