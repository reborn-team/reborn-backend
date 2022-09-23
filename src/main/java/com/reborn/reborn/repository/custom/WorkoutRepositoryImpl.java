package com.reborn.reborn.repository.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.reborn.reborn.dto.QWorkoutListDto;
import com.reborn.reborn.dto.WorkoutListDto;
import com.reborn.reborn.entity.QWorkoutImage;
import com.reborn.reborn.entity.WorkoutCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.reborn.reborn.entity.QWorkout.*;
import static com.reborn.reborn.entity.QWorkoutImage.*;
import static org.springframework.util.StringUtils.*;

@Repository
@RequiredArgsConstructor
public class WorkoutRepositoryImpl implements WorkoutQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<WorkoutListDto> paginationWorkoutList(WorkoutSearchCondition cond) {

        QWorkoutImage qWorkoutImage = new QWorkoutImage("workoutImageMaxId");

        return jpaQueryFactory.select(new QWorkoutListDto(
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
                        ltWorkoutId(cond.getId()),
                        equalsWorkoutCategory(cond.getCategory())
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
