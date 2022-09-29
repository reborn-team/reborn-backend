package com.reborn.reborn.member.exception;

import com.reborn.reborn.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends CustomException {

    public UnAuthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
