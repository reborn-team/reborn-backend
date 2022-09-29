package com.reborn.reborn.exception.member;

import com.reborn.reborn.exception.CustomException;
import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends CustomException {

    public MemberNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다" + message);
    }
}
