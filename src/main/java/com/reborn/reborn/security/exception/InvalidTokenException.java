package com.reborn.reborn.security.exception;

import com.reborn.reborn.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends CustomException {

    public InvalidTokenException() {
        super(HttpStatus.FORBIDDEN, "잘못된 토큰입니다.");
    }
}
