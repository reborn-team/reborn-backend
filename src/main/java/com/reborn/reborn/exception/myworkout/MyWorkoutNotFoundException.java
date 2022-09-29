package com.reborn.reborn.exception.myworkout;

import com.reborn.reborn.exception.CustomException;
import org.springframework.http.HttpStatus;

public class MyWorkoutNotFoundException extends CustomException {

    public MyWorkoutNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
