package com.reborn.reborn.security.exception;

import com.reborn.reborn.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ExpiredTokenException extends CustomException {

    public ExpiredTokenException() {
        super(HttpStatus.UNAUTHORIZED, "로그인 시간이 만료되었습니다.");
    }
}
