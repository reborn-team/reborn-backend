package com.reborn.reborn.exception.workout;

import com.reborn.reborn.exception.CustomException;
import org.springframework.http.HttpStatus;

public class WorkoutAlreadyExistException extends CustomException {
    public WorkoutAlreadyExistException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
