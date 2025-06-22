package com.example.practice.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppException extends RuntimeException {
    private ErrorCode errorCode;
    public AppException(String message) {
        super(message);
    }

    public AppException(ErrorCode errorCode, String message) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public AppException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
