//package com.reborn.reborn.repository.custom;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import com.reborn.reborn.dto.FileDto;
//import com.reborn.reborn.dto.QWorkoutResponseDto;
//import com.reborn.reborn.dto.WorkoutResponseDto;
//import com.reborn.reborn.entity.QWorkout;
//import com.reborn.reborn.entity.QWorkoutImage;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//import static com.reborn.reborn.entity.QWorkout.*;
//import static com.reborn.reborn.entity.QWorkoutImage.*;
//
//@Repository
//@RequiredArgsConstructor
//public class WorkoutRepositoryImpl implements WorkoutQuerydslRepository{
//
//    private final JPAQueryFactory jpaQueryFactory;
//
//    @Override
//    public WorkoutResponseDto findByIdAndMemberId(Long id, Long memberId) {
//        jpaQueryFactory.select(new QWorkoutResponseDto(
//                workout.id,
//                workout.workoutName,
//                workout.content,
//
//        ));
//        return null;
//    }
//}
