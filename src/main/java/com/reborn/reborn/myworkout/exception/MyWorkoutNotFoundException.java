package com.reborn.reborn.myworkout.exception;

import com.reborn.reborn.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class MyWorkoutNotFoundException extends CustomException {

    public MyWorkoutNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, "찾으시는 내 운동이 없습니다 :" + message);
    }
}
