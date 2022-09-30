package com.reborn.reborn.myworkout.domain.repository;

import com.reborn.reborn.member.domain.MemberRole;
import com.reborn.reborn.myworkout.presentation.dto.MyWorkoutDto;
import com.reborn.reborn.common.presentation.dto.Slice;
import com.reborn.reborn.member.domain.Member;
import com.reborn.reborn.member.domain.repository.MemberRepository;
import com.reborn.reborn.myworkout.domain.MyWorkout;
import com.reborn.reborn.workout.domain.Workout;
import com.reborn.reborn.workout.domain.WorkoutCategory;
import com.reborn.reborn.workout.domain.repository.custom.WorkoutQuerydslRepository;
import com.reborn.reborn.workout.domain.repository.custom.WorkoutSearchCondition;
import com.reborn.reborn.workout.domain.repository.WorkoutRepository;
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
        List<MyWorkoutDto> result = workoutQuerydslRepository.pagingMyWorkoutWithSearchCondition(new WorkoutSearchCondition(null, null, null, null), member.getId());

        assertThat(result.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("내 운동 목록을 조회하고 값이 10개면 true를 반환한다.")
    void pageTest() {
        List<MyWorkoutDto> result = workoutQuerydslRepository.pagingMyWorkoutWithSearchCondition(new WorkoutSearchCondition(null, null, null, null), member.getId());

        Slice<MyWorkoutDto> page = new Slice<>(result);

        assertThat(page.hasNext()).isEqualTo(true);
    }
    @Test
    @DisplayName("내 운동 목록을 조회하고 값이 10개면 true를 반환한다.")
    void pageSearchTest() {
        List<MyWorkoutDto> result = workoutQuerydslRepository.pagingMyWorkoutWithSearchCondition(new WorkoutSearchCondition(null, null, "han", null), member.getId());

        Slice<MyWorkoutDto> page = new Slice<>(result);

        assertThat(result.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("내 운동목록에서 카테고리별로 Dto에 맞게 조회한다.")
    void myWorkoutDto() {
        List<MyWorkoutDto> myWorkoutDto = workoutQuerydslRepository.getMyWorkoutDto(member.getId(), WorkoutCategory.BACK);

        assertThat(myWorkoutDto.size()).isEqualTo(11);
    }
    public  Member createMember() {
        Member member = Member.builder()
                .email("email")
                .memberRole(MemberRole.USER)
                .password("A")
                .nickname("han")
                .build();
        return member;
    }

    public  Workout createWorkout(Member member, String text) {
        Workout workout = Workout.builder()
                .workoutName("pull up" + text)
                .member(member)
                .content("등을 펼쳐서 당깁니다." + text)
                .workoutCategory(WorkoutCategory.BACK).build();
        return workout;
    }
}