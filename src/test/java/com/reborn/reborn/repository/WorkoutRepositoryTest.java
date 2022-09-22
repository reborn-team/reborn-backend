package com.reborn.reborn.repository;

import com.reborn.reborn.dto.WorkoutListDto;
import com.reborn.reborn.dto.WorkoutResponseDto;
import com.reborn.reborn.dto.WorkoutSliceDto;
import com.reborn.reborn.entity.*;
import com.reborn.reborn.repository.custom.WorkoutSearchCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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
    @Autowired
    EntityManager em;

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
    @DisplayName("운동 정보를 상세 조회한다.")
    void detailView(){
        Member member = createMember();
        Workout workout = createWorkout(member, "");
        WorkoutImage workoutImage = new WorkoutImage("", "");
        workoutImage.uploadToWorkout(workout);
        memberRepository.save(member);
        workoutImageRepository.save(workoutImage);
        workoutRepository.save(workout);

        em.flush();
        em.clear();

        WorkoutResponseDto workoutDetail = workoutRepository.getWorkoutDetail(workout.getId());

        assertThat(workoutDetail.getMemberId()).isEqualTo(member.getId());
        assertThat(workoutDetail.getOriginFileName()).isEqualTo(workoutImage.getOriginFileName());

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
                .nickname("han")
                .build();
        return member;
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