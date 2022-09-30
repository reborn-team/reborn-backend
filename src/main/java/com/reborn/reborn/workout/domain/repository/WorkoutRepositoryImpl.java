package com.reborn.reborn.workout.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.reborn.reborn.myworkout.presentation.dto.MyWorkoutDto;
import com.reborn.reborn.myworkout.presentation.dto.QMyWorkoutDto;
import com.reborn.reborn.workout.domain.QWorkoutImage;
import com.reborn.reborn.workout.presentation.dto.QWorkoutPreviewResponse;
import com.reborn.reborn.workout.presentation.dto.WorkoutPreviewResponse;
import com.reborn.reborn.workout.domain.WorkoutCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.reborn.reborn.myworkout.domain.QMyWorkout.myWorkout;
import static com.reborn.reborn.workout.domain.QWorkout.workout;
import static com.reborn.reborn.workout.domain.QWorkoutImage.workoutImage;


@Repository
@RequiredArgsConstructor
public class WorkoutRepositoryImpl implements WorkoutQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<WorkoutPreviewResponse> pagingWorkoutWithSearchCondition(WorkoutSearchCondition cond) {

        QWorkoutImage qWorkoutImage = new QWorkoutImage("workoutImageMaxId");

        return jpaQueryFactory.select(new QWorkoutPreviewResponse(
                        workout.id,
                        workout.workoutName,
                        workoutImage.uploadFileName.coalesce("empty")
                )).from(workout)
                .leftJoin(workoutImage).on(workoutImage.workout.eq(workout),
                        workoutImage.id.eq(
                                jpaQueryFactory
                                        .select(qWorkoutImage.id.max())
                                        .from(qWorkoutImage)
                                        .where(qWorkoutImage.workout.eq(workout))
                        ))
                .groupBy(workout)
                .where(
                        containsWorkoutTitle(cond.getTitle()),
                        containsWorkoutAuthor(cond.getNickname()),
                        ltWorkoutId(cond.getId()),
                        equalsWorkoutCategory(cond.getCategory())
                )
                .limit(10L)
                .orderBy(workout.createdDate.desc())
                .fetch();
    }


    @Override
    public List<MyWorkoutDto> pagingMyWorkoutWithSearchCondition(WorkoutSearchCondition cond, Long memberId) {
        QWorkoutImage qWorkoutImage = new QWorkoutImage("workoutImageMaxId");

        return jpaQueryFactory.select(new QMyWorkoutDto(
                        myWorkout.id,
                        myWorkout.workout.id,
                        myWorkout.workout.workoutName,
                        workoutImage.uploadFileName.coalesce("empty")
                )).from(myWorkout)
                .leftJoin(workoutImage).on(workoutImage.workout.eq(myWorkout.workout),
                        workoutImage.id.eq(
                                jpaQueryFactory
                                        .select(qWorkoutImage.id.max())
                                        .from(qWorkoutImage)
                                        .where(qWorkoutImage.workout.eq(myWorkout.workout))
                        ))
                .innerJoin(myWorkout.workout, workout)
                .where(
                        myWorkout.member.id.eq(memberId),
                        containsWorkoutTitle(cond.getTitle()),
                        containsWorkoutAuthor(cond.getNickname()),
                        ltMyWorkoutId(cond.getId()),
                        equalsWorkoutCategory(cond.getCategory())
                )
                .limit(10L)
                .orderBy(myWorkout.createdDate.desc())
                .fetch();
    }

    @Override
    public List<MyWorkoutDto> getMyWorkoutDto(Long memberId, WorkoutCategory workoutCategory) {
        QWorkoutImage qWorkoutImage = new QWorkoutImage("workoutImageMaxId");

        return jpaQueryFactory
                .select(new QMyWorkoutDto(
                        myWorkout.id,
                        myWorkout.workout.workoutName,
                        workoutImage.uploadFileName.coalesce("empty")
                )).from(myWorkout)
                .leftJoin(workoutImage).on(workoutImage.workout.eq(myWorkout.workout),
                        workoutImage.id.eq(
                                jpaQueryFactory
                                        .select(qWorkoutImage.id.max())
                                        .from(qWorkoutImage)
                                        .where(qWorkoutImage.workout.eq(myWorkout.workout))
                        ))
                .innerJoin(myWorkout.workout, workout)
                .where(
                        myWorkout.member.id.eq(memberId),
                        equalsWorkoutCategory(workoutCategory)
                )
                .orderBy(myWorkout.createdDate.desc())
                .fetch();
    }

    private BooleanExpression containsWorkoutTitle(String title) {
        return StringUtils.hasText(title) ? workout.workoutName.contains(title) : null;
    }


    private BooleanExpression equalsWorkoutCategory(WorkoutCategory workoutCategory) {
        return workoutCategory == null ? null : workout.workoutCategory.eq(workoutCategory);
    }

    private BooleanExpression ltWorkoutId(Long workoutId) {
        return workoutId == null ? null : workout.id.lt(workoutId);
    }


    private BooleanExpression ltMyWorkoutId(Long workoutId) {
        return workoutId == null ? null : myWorkout.id.lt(workoutId);
    }
    private BooleanExpression containsWorkoutAuthor(String author) {
        return StringUtils.hasText(author) ? workout.member.nickname.contains(author) : null;
    }

}
