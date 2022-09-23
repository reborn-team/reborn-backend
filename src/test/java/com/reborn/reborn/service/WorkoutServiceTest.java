package com.reborn.reborn.service;

import com.reborn.reborn.dto.*;
import com.reborn.reborn.entity.Member;
import com.reborn.reborn.entity.Workout;
import com.reborn.reborn.entity.WorkoutCategory;
import com.reborn.reborn.entity.WorkoutImage;
import com.reborn.reborn.repository.MemberRepository;
import com.reborn.reborn.repository.WorkoutImageRepository;
import com.reborn.reborn.repository.WorkoutRepository;
import com.reborn.reborn.repository.custom.WorkoutSearchCondition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkoutServiceTest {

    @InjectMocks
    private WorkoutService workoutService;
    @Mock
    WorkoutRepository workoutRepository;
    @Mock
    MemberRepository memberRepository;

    @Mock
    WorkoutImageRepository workoutImageRepository;

    @Test
    @DisplayName("운동 정보를 생성하고 Id 를 리턴한다.")
    void create() {
        WorkoutRequestDto requestDto = WorkoutRequestDto.builder()
                .workoutCategory("BACK").build();
        Workout workout = Workout.builder().build();
        Member member = Member.builder().build();

        given(workoutRepository.save(any())).willReturn(workout);
        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        //when
        Workout saveWorkout = workoutService.create(member.getId(), requestDto);
        //then
        verify(workoutRepository).save(any());

        assertThat(saveWorkout.getId()).isEqualTo(workout.getId());

    }

    @Test
    @DisplayName("운동 정보를 조회한다.")
    void getMyWorkout() {
        Workout workout = Workout.builder().workoutCategory(WorkoutCategory.BACK).build();

        given(workoutRepository.findById(any())).willReturn(Optional.of(workout));

        //when
        Workout findWorkout = workoutService.findWorkoutById(workout.getId());
        //then
        verify(workoutRepository).findById(any());

        assertThat(workout.getWorkoutCategory()).isEqualTo(findWorkout.getWorkoutCategory());

    }

    @Test
    @DisplayName("운동 정보를 검색조건에 따라 결과가 10개면 true를 출력한다")
    void sliceResultTenWorkout() {
        List<WorkoutListDto> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new WorkoutListDto((long) i, "name", ""));
        }
        WorkoutSearchCondition cond = new WorkoutSearchCondition();
        given(workoutRepository.paginationWorkoutList(cond))
                .willReturn(list);

        List<WorkoutListDto> findList = workoutService.pagingWorkout(cond);
        verify(workoutRepository).paginationWorkoutList(any());

        WorkoutSliceDto workoutSliceDto = new WorkoutSliceDto(findList);

        assertThat(list.size()).isEqualTo(findList.size());
        assertThat(workoutSliceDto.hasNext()).isTrue();
    }

    @Test
    @DisplayName("운동 정보를 검색조건에 따라 결과가 10개 미만이면 false를 출력한다")
    void sliceResultNotTenWorkout() {
        List<WorkoutListDto> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            list.add(new WorkoutListDto((long) i, "name", ""));
        }
        WorkoutSearchCondition cond = new WorkoutSearchCondition();
        given(workoutRepository.paginationWorkoutList(cond))
                .willReturn(list);

        List<WorkoutListDto> findList = workoutService.pagingWorkout(cond);
        verify(workoutRepository).paginationWorkoutList(any());

        WorkoutSliceDto workoutSliceDto = new WorkoutSliceDto(findList);

        assertThat(list.size()).isEqualTo(findList.size());
        assertThat(workoutSliceDto.hasNext()).isFalse();
    }

    @Test
    @DisplayName("작성자만 운동정보를 삭제할 수 있다.")
    void deleteWorkoutByAuthor() {
        Member member = Member.builder().id(1L).build();
        Workout workout = Workout.builder().member(member).build();

        given(workoutRepository.findById(any())).willReturn(Optional.of(workout));

        workoutService.deleteWorkout(member.getId(), workout.getId());
        verify(workoutRepository).findById(any());
        verify(workoutRepository).delete(workout);

    }

    @Test
    @DisplayName("작성자가 아니면 삭제요청 시 예외가 발생한다")
    void deleteWorkoutByNotAuthor() {
        Member author = Member.builder().id(1L).build();
        Workout workout = Workout.builder().member(author).build();
        Member reader = Member.builder().id(2L).build();

        given(workoutRepository.findById(any())).willReturn(Optional.of(workout));

        assertThatThrownBy(() -> workoutService.deleteWorkout(reader.getId(), workout.getId())).isInstanceOf(RuntimeException.class);

        verify(workoutRepository).findById(any());
    }

    @Test
    @DisplayName("운동을 수정한다")
    void modifyWorkout() {
        Member author = Member.builder().id(1L).build();
        Workout workout = Workout.builder().member(author).workoutCategory(WorkoutCategory.BACK).workoutName("풀업").content("내용").build();
        WorkoutEditForm form = WorkoutEditForm.builder().workoutName("바벨 로우").content("내용").build();

        given(workoutRepository.findById(any())).willReturn(Optional.of(workout));

        Workout updateWorkout = workoutService.updateWorkout(author.getId(), workout.getId(), form);


        verify(workoutRepository).findById(any());
        assertThat(form.getWorkoutName()).isEqualTo(updateWorkout.getWorkoutName());
    }


    @Test
    @DisplayName("등록 이미지를 저장한다.")
    void createImage() {
        FileDto fileDto = new FileDto("origin", "upload");
        Workout workout = Workout.builder().workoutCategory(WorkoutCategory.BACK).workoutName("등").build();
        WorkoutImage workoutImage = new WorkoutImage(fileDto.getOriginFileName(), fileDto.getUploadFileName());

        given(workoutImageRepository.save(any())).willReturn(workoutImage);

        Long saveImageId = workoutService.createImage(fileDto, workout);

        verify(workoutImageRepository).save(any());

        assertThat(saveImageId).isEqualTo(workoutImage.getId());
    }

    @Test
    @DisplayName("파일을 전체 삭제하고 추가한다")
    void deleteAndUpdateImage() {
        List<FileDto> fileDtoList = new ArrayList<>();
        fileDtoList.add(new FileDto());

        Workout workout = Workout.builder().workoutCategory(WorkoutCategory.BACK).workoutName("등").build();

        workoutService.deleteAndUpdateImage(fileDtoList, workout);

        verify(workoutImageRepository).deleteAllByWorkoutId(workout.getId());
        verify(workoutImageRepository).save(any());
    }

    @Test
    @DisplayName("파일이 없으면 삭제하지 않는다")
    void deleteAndUpdateImageNoFile() {
        Member author = Member.builder().id(1L).build();
        Workout workout = Workout.builder().member(author).workoutCategory(WorkoutCategory.BACK).workoutName("등").build();
        WorkoutImage workoutImage = new WorkoutImage("a", "b");
        workoutImage.uploadToWorkout(workout);

        given(workoutRepository.findByIdWithImagesAndMember(any())).willReturn(Optional.of(workout));

        WorkoutResponseDto workoutDto = workoutService.getWorkoutDto(workout.getId());

        assertThat(workoutDto.getWorkoutName()).isEqualTo(workout.getWorkoutName());
        assertThat(workoutDto.getFiles()).containsExactly(new FileDto("a", "b"));
    }

    @Test
    @DisplayName("파일이 없으면 삭제하지 않는다")
    void getWorkoutDto() {
        List<FileDto> fileDtoList = new ArrayList<>();

        Workout workout = Workout.builder().workoutCategory(WorkoutCategory.BACK).workoutName("등").build();

        workoutService.deleteAndUpdateImage(fileDtoList, workout);

        verify(workoutImageRepository, never()).deleteAllByWorkoutId(workout.getId());
        verify(workoutImageRepository, never()).save(any());
    }
}