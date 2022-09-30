package com.reborn.reborn.member.exception;

import com.reborn.reborn.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class PasswordNotMatchException extends CustomException {

    public PasswordNotMatchException() {
        super(HttpStatus.UNAUTHORIZED, "비밀번호가 맞지 않습니다.");
    }

}
