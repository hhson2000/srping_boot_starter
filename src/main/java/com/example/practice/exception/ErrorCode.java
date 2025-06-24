package com.example.practice.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_EXIST(3001, "User already exists"),
    USER_NOT_FOUND(3002, "User not found"),
    INVALID_PASSWORD(3003, "Invalid password format"),
    DUPLICATE_USERNAME(3004, "Username is already taken"),
    USER_INVALID(3005, "User invalid"),
    PASSWORD_INVALID(3006, "Password invalid"),
    USER_SIZE(3007, "User must be at least 8 characters long"),
    PASSWORD_SIZE(3008, "Password must be at least 8 characters long"),
    USER_NOT_EXIST(3009, "User already exists"),
    UNAUTHENTICATED(3010, "User is not authenticated"),
    INVALID_ERROR_KEY(3999, "Wrong error key"),
    SYSTEM_ERROR(4444, "System error");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
