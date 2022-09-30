package com.reborn.reborn.security.exception;

import com.reborn.reborn.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ExpiredTokenException extends CustomException {

    public ExpiredTokenException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
