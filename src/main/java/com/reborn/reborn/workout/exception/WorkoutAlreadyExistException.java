package com.reborn.reborn.workout.exception;

import com.reborn.reborn.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class WorkoutAlreadyExistException extends CustomException {
    public WorkoutAlreadyExistException(String message) {
        super(HttpStatus.BAD_REQUEST, "이미 추가된 운동입니다 : " + message);
    }
}
