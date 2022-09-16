package com.reborn.reborn.repository.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.reborn.reborn.dto.QWorkoutResponseDto;
import com.reborn.reborn.dto.WorkoutResponseDto;
import com.reborn.reborn.entity.QWorkoutImage;
import com.reborn.reborn.entity.WorkoutCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.reborn.reborn.entity.QWorkout.*;
import static com.reborn.reborn.entity.QWorkoutImage.*;

@Repository
@RequiredArgsConstructor
public class WorkoutRepositoryImpl implements WorkoutQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<WorkoutResponseDto> paginationWorkoutList(WorkoutSearchCondition cond) {

        QWorkoutImage qWorkoutImage = new QWorkoutImage("workoutImageMaxId");

        return jpaQueryFactory.select(new QWorkoutResponseDto(
                        workout.id,
                        workout.workoutName,
                        workout.content,
                        workoutImage.path.coalesce("empty"),
                        workoutImage.fileName.coalesce("empty"),
                        workout.workoutCategory
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
                        ltWorkoutId(cond.getId()),
                        equalsWorkoutCategory(cond.of())
                )
                .limit(10L)
                .orderBy(workout.createdDate.desc())
                .fetch();
    }

    private BooleanExpression equalsWorkoutCategory(WorkoutCategory workoutCategory) {
        return workoutCategory == null ? null : workout.workoutCategory.eq(workoutCategory);
    }

    private BooleanExpression ltWorkoutId(Long workoutId) {
        return workoutId == null ? null : workout.id.lt(workoutId);
    }
}
