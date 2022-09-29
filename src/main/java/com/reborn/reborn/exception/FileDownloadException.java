package com.reborn.reborn.exception;

import org.springframework.http.HttpStatus;

public class FileDownloadException extends CustomException{

    public FileDownloadException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
