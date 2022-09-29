package com.reborn.reborn.repository;

import com.reborn.reborn.dto.MyWorkoutDto;
import com.reborn.reborn.dto.WorkoutListDto;
import com.reborn.reborn.dto.WorkoutSliceDto;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.MyWorkout;
import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.entity.WorkoutCategory;
import com.reborn.reborn.repository.custom.WorkoutQuerydslRepository;
import com.reborn.reborn.repository.custom.WorkoutSearchCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.reborn.reborn.repository.WorkoutRepositoryTest.createMember;
import static com.reborn.reborn.repository.WorkoutRepositoryTest.createWorkout;
import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class MyWorkoutRepositoryTest {

    @Autowired
    MyWorkoutRepository myWorkoutRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    WorkoutRepository workoutRepository;
    @Autowired
    WorkoutQuerydslRepository workoutQuerydslRepository;

    Member member;

    Workout workout;

    @BeforeEach
    void before() {
        member = createMember();
        memberRepository.save(member);
        workout = createWorkout(member, "");
        workoutRepository.save(workout);
        for (int i = 0; i < 11; i++) {
            Workout workout = createWorkout(member, i + "");
            workoutRepository.save(workout);
            myWorkoutRepository.save(new MyWorkout(workout, member));
        }
    }

    @Test
    @DisplayName("내 운동 목록에 존재 하면 true를 반환한다.")
    void exist() {
        myWorkoutRepository.save(new MyWorkout(workout, member));

        Boolean exist = myWorkoutRepository.existsByWorkoutIdAndMemberId(workout.getId(), member.getId());

        assertThat(exist).isTrue();
    }

    @Test
    @DisplayName("내 운동 목록을 Dto에 맞게 조회하고 10개를 반환한다.")
    void sliceTest() {
        List<WorkoutListDto> result = workoutQuerydslRepository.pagingMyWorkoutWithSearchCondition(new WorkoutSearchCondition(null, null,null,null), member.getId());

        assertThat(result.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("내 운동 목록을 조회하고 값이 10개면 true를 반환한다.")
    void pageTest() {
        List<WorkoutListDto> result = workoutQuerydslRepository.pagingMyWorkoutWithSearchCondition(new WorkoutSearchCondition(null, null, null, null), member.getId());

        WorkoutSliceDto page = new WorkoutSliceDto(result);

        assertThat(page.hasNext()).isEqualTo(true);
    }
    @Test
    @DisplayName("내 운동 목록을 조회하고 값이 10개면 true를 반환한다.")
    void pageSearchTest() {
        List<WorkoutListDto> result = workoutQuerydslRepository.pagingMyWorkoutWithSearchCondition(new WorkoutSearchCondition(null, null, "han", null), member.getId());

        WorkoutSliceDto page = new WorkoutSliceDto(result);

        assertThat(result.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("내 운동목록에서 카테고리별로 Dto에 맞게 조회한다.")
    void myWorkoutDto() {
        List<MyWorkoutDto> myWorkoutDto = workoutQuerydslRepository.getMyWorkoutDto(member.getId(), WorkoutCategory.BACK);

        assertThat(myWorkoutDto.size()).isEqualTo(11);
    }

}