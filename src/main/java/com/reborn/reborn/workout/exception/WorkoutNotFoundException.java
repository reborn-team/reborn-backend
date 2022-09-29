package com.reborn.reborn.workout.exception;

import com.reborn.reborn.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class WorkoutNotFoundException extends CustomException {
    public WorkoutNotFoundException( String message) {
        super(HttpStatus.NOT_FOUND, "운동이 존재하지 않습니다 : " + message);
    }
}
