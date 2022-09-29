package com.reborn.reborn.exception;

import org.springframework.http.HttpStatus;

public class FileUploadException extends CustomException{

    public FileUploadException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
