package com.reborn.reborn.workout.application;

import com.reborn.reborn.common.presentation.dto.FileDto;
import com.reborn.reborn.common.presentation.dto.Slice;
import com.reborn.reborn.member.domain.Member;
import com.reborn.reborn.workout.domain.Workout;
import com.reborn.reborn.workout.domain.WorkoutCategory;
import com.reborn.reborn.workout.domain.WorkoutImage;
import com.reborn.reborn.member.exception.MemberNotFoundException;
import com.reborn.reborn.member.exception.UnAuthorizedException;
import com.reborn.reborn.workout.exception.WorkoutNotFoundException;
import com.reborn.reborn.member.domain.repository.MemberRepository;
import com.reborn.reborn.myworkout.domain.repository.MyWorkoutRepository;
import com.reborn.reborn.workout.domain.repository.WorkoutImageRepository;
import com.reborn.reborn.workout.domain.repository.WorkoutRepository;
import com.reborn.reborn.workout.domain.repository.custom.WorkoutQuerydslRepository;
import com.reborn.reborn.workout.domain.repository.custom.WorkoutSearchCondition;
import com.reborn.reborn.workout.presentation.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final MemberRepository memberRepository;
    private final WorkoutImageRepository workoutImageRepository;
    private final WorkoutQuerydslRepository workoutQuerydslRepository;
    private final MyWorkoutRepository myWorkoutRepository;


    public Workout create(Long memberId, WorkoutRequest dto) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId.toString()));
        Workout workout = Workout.builder()
                .workoutName(dto.getWorkoutName())
                .content(dto.getContent())
                .member(member)
                .workoutCategory(WorkoutCategory.valueOf(dto.getWorkoutCategory()))
                .build();

        Workout saveWorkout = workoutRepository.save(workout);
        List<FileDto> files = dto.getFiles();
        if (files.size() > 0) {
            files.forEach(file -> createImage(file, workout));
        }
        return saveWorkout;
    }

    @Transactional(readOnly = true)
    public Workout findWorkoutById(Long workoutId) {
        return getWorkout(workoutId);
    }

    @Transactional(readOnly = true)
    public Slice<WorkoutPreviewResponse> getPagingWorkout(WorkoutSearchCondition cond) {

        List<WorkoutPreviewResponse> result = workoutQuerydslRepository.pagingWorkoutWithSearchCondition(cond);
        return new Slice<>(result);
    }

    public void deleteWorkout(Long authorId, Long workoutId) {

        if (myWorkoutRepository.existsByWorkoutId(workoutId)) {
            throw new UnAuthorizedException("나의 리스트에 추가된 운동은 삭제될 수 없습니다");
        }
        Workout workout = getWorkout(workoutId);
        validIsAuthor(authorId, workout);
        workoutRepository.delete(workout);
    }

    public Workout updateWorkout(Long authorId, Long workoutId, WorkoutEditForm form) {
        Workout workout = getWorkout(workoutId);
        validIsAuthor(authorId, workout);
        workout.modifyWorkout(form.getWorkoutName(), form.getContent());

        deleteAndUpdateImage(form.getFiles(), workout);
        return workout;
    }


    public Long createImage(FileDto fileDto, Workout workout) {
        WorkoutImage workoutImage = new WorkoutImage(fileDto.getOriginFileName(), fileDto.getUploadFileName());
        workoutImage.uploadToWorkout(workout);
        workoutImageRepository.save(workoutImage);
        return workoutImage.getId();
    }


    public void deleteAndUpdateImage(List<FileDto> files, Workout workout) {
        if (files.size() == 0) {
            return;
        }
        workoutImageRepository.deleteAllByWorkout(workout);
        files.forEach(file -> createImage(file, workout));
    }

    @Transactional(readOnly = true)
    public WorkoutResponse getWorkoutDetailDto(Long memberId, Long workoutId) {

        Workout workout = workoutRepository.findByIdWithImagesAndMember(workoutId)
                .orElseThrow(() -> new WorkoutNotFoundException(workoutId.toString()));
        Boolean isAdd = myWorkoutRepository.existsByWorkoutIdAndMemberId(workoutId, memberId);

        WorkoutResponse dto = WorkoutResponse.of(workout, isAdd);
        dto.isAuthor(memberId);
        log.info("author={}", dto);
        return dto;

    }

    private void validIsAuthor(Long authorId, Workout workout) {
        if (workout.getMember().getId() != authorId) {
            throw new UnAuthorizedException("권한이 없습니다 : "+ authorId);
        }
    }

    private Workout getWorkout(Long workoutId) {
        return workoutRepository.findById(workoutId).orElseThrow(() -> new WorkoutNotFoundException(workoutId.toString()));
    }

}
