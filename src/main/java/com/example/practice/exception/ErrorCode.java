package com.example.practice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_EXIST(3001, "User already exists", HttpStatus.CONFLICT),
    USER_NOT_FOUND(3002, "User not found", HttpStatus.NOT_FOUND),
    INVALID_PASSWORD(3003, "Invalid password format", HttpStatus.BAD_REQUEST),
    DUPLICATE_USERNAME(3004, "Username is already taken", HttpStatus.CONFLICT),
    USER_INVALID(3005, "User invalid", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(3006, "Password invalid", HttpStatus.BAD_REQUEST),
    USER_SIZE(3007, "User must be at least 8 characters long", HttpStatus.BAD_REQUEST),
    PASSWORD_SIZE(3008, "Password must be at least 8 characters long", HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST(3009, "User already exists", HttpStatus.CONFLICT),
    UNAUTHENTICATED(3010, "User is not authenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZE(3010, "Access denied", HttpStatus.UNAUTHORIZED),
    INVALID_ERROR_KEY(3999, "Wrong error key", HttpStatus.BAD_REQUEST),
    SYSTEM_ERROR(4444, "System error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
