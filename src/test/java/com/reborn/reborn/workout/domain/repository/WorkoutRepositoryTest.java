package com.reborn.reborn.workout.domain.repository;

import com.reborn.reborn.workout.domain.repository.custom.WorkoutQuerydslRepository;
import com.reborn.reborn.workout.domain.repository.custom.WorkoutSearchCondition;
import com.reborn.reborn.workout.presentation.dto.WorkoutPreviewResponse;
import com.reborn.reborn.common.presentation.dto.Slice;
import com.reborn.reborn.member.domain.Member;
import com.reborn.reborn.member.domain.MemberRole;
import com.reborn.reborn.member.domain.repository.MemberRepository;
import com.reborn.reborn.workout.domain.Workout;
import com.reborn.reborn.workout.domain.WorkoutCategory;
import com.reborn.reborn.workout.domain.WorkoutImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.reborn.reborn.config.ControllerConfig.getMember;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class WorkoutRepositoryTest {

    @Autowired
    WorkoutRepository workoutRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    WorkoutImageRepository workoutImageRepository;
    @Autowired
    WorkoutQuerydslRepository workoutQuerydslRepository;
    @Autowired
    EntityManager em;

    @BeforeEach
    void before() {
        memberRepository.deleteAll();
        Member member = createMember();
        memberRepository.save(member);
        for (int i = 0; i < 11; i++) {
            Workout workout = createWorkout(member, i + "", (long) i);
            workoutRepository.save(workout);
        }
    }

    @Test
    @DisplayName("운동을 생성한다.")
    void createTest() {
        Member member = createMember();
        Workout workout = createWorkout(member, "",0L);
        WorkoutImage workoutImage = new WorkoutImage("", "");
        workoutImage.uploadToWorkout(workout);
        workoutImageRepository.save(workoutImage);
        workoutRepository.save(workout);

        assertThat(workout.getMember()).isEqualTo(member);
        assertThat(workoutImage.getWorkout()).isEqualTo(workout);

    }

    @Test
    @DisplayName("운동 정보를 상세 조회한다.")
    void detailView() {
        Member member = getMember();
        Workout workout = createWorkout(member, "",0L);
        WorkoutImage workoutImage = new WorkoutImage("", "");
        workoutImage.uploadToWorkout(workout);
        memberRepository.save(member);
        workoutImageRepository.save(workoutImage);
        workoutRepository.save(workout);

        em.flush();
        em.clear();

        Workout findWorkout = workoutRepository.findByIdWithImagesAndMember(workout.getId()).get();

        assertThat(findWorkout.getMember().getId()).isEqualTo(member.getId());
        assertThat(findWorkout.getWorkoutName()).isEqualTo(workout.getWorkoutName());

    }


    @Test
    @DisplayName("운동 목록을 Dto에 맞게 조회하고 10개를 반환한다.")
    void sliceTest() {
        List<WorkoutPreviewResponse> result = workoutQuerydslRepository.pagingWorkoutWithSearchCondition(new WorkoutSearchCondition(null, WorkoutCategory.BACK, null, null));
        assertThat(result.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("운동 목록을 조회하고 값이 10개면 true를 반환한다.")
    void pageTest() {
        List<WorkoutPreviewResponse> result = workoutQuerydslRepository.pagingWorkoutWithSearchCondition(new WorkoutSearchCondition(null, null, null, null));
        Slice<WorkoutPreviewResponse> page= new Slice<>(result);
        assertThat(page.hasNext()).isEqualTo(true);
    }

    @Test
    @DisplayName("운동 목록을 검색조건에 따라 조회하고 값이 10개면 true를 반환한다.")
    void pageSearchTest() {
        List<WorkoutPreviewResponse> result = workoutQuerydslRepository.pagingWorkoutWithSearchCondition(new WorkoutSearchCondition(null, null, "han", null));
        Slice<WorkoutPreviewResponse> page= new Slice<>(result);
        assertThat(page.hasNext()).isEqualTo(true);
    }
    @Test
    @DisplayName("운동 정보에 이미지를 같이 반환한다")
    void findWorkoutAndImage() {
        Member member = getMember();
        memberRepository.save(member);
        Workout workout = createWorkout(member, "",0L);
        workoutRepository.save(workout);
        for (int i = 0; i < 10; i++) {
            WorkoutImage workoutImage = new WorkoutImage("a" + i, "b" + i);
            workoutImage.uploadToWorkout(workout);
            workoutImageRepository.save(workoutImage);
        }
        em.flush();
        em.clear();

        Workout findWorkout = workoutRepository.findByIdWithImagesAndMember(workout.getId()).get();
        List<WorkoutImage> workoutImages = findWorkout.getWorkoutImages();

        assertThat(workoutImages.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("운동 정보에 이미지가 없으면 list size를 0 반환한다.")
    void findWorkoutAndNoImage() {
        Member member = getMember();
        memberRepository.save(member);
        Workout workout = createWorkout(member, "",0L);
        workoutRepository.save(workout);
        em.flush();
        em.clear();

        Workout findWorkout = workoutRepository.findByIdWithImagesAndMember(workout.getId()).get();
        List<WorkoutImage> workoutImages = findWorkout.getWorkoutImages();

        assertThat(workoutImages.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("운동 정보를 조회할 때 Member 데이터도 반환한다.")
    void findWorkoutAndMember() {
        Member member = getMember();
        Member member2 = Member.builder().email("a").memberRole(MemberRole.USER).password("a").nickname("a").build();
        memberRepository.save(member);
        memberRepository.save(member2);
        Workout workout = createWorkout(member, "",0L);
        workoutRepository.save(workout);
        em.flush();
        em.clear();

        Workout findWorkout = workoutRepository.findByIdWithImagesAndMember(workout.getId()).get();
        String nickname = findWorkout.getMember().getNickname();

        assertThat(nickname).isEqualTo(member.getNickname());

    }

    @Test
    @DisplayName("운동 목록을 addCount 순으로 조회한다.")
    void rank() {
        List<WorkoutPreviewResponse> workoutPreviewResponses = workoutQuerydslRepository.findWorkoutDtoListOrderByAddCount(WorkoutCategory.BACK);
        assertThat(workoutPreviewResponses.size()).isEqualTo(6);
    }

    @Test
    @DisplayName("운동 목록을 카테고리별로 addCount 순으로 조회한다.")
    void rankIsNull() {
        List<WorkoutPreviewResponse> workoutPreviewResponses = workoutQuerydslRepository.findWorkoutDtoListOrderByAddCount(WorkoutCategory.CORE);
        assertThat(workoutPreviewResponses.size()).isEqualTo(0);
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

    public static Workout createWorkout(Member member, String text,Long i) {
        Workout workout = Workout.builder()
                .workoutName("pull up" + text)
                .member(member)
                .content("등을 펼쳐서 당깁니다." + text)
                .workoutCategory(WorkoutCategory.BACK).build();
        return workout;
    }

}