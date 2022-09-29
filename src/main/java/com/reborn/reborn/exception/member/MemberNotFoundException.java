package com.reborn.reborn.exception.member;

import com.reborn.reborn.exception.CustomException;
import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends CustomException {

    public MemberNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
