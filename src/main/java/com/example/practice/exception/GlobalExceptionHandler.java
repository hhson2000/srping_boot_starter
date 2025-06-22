package com.example.practice.exception;

import com.example.practice.comonResponse.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException exception) {
        ApiResponse response = new ApiResponse<>();
        response.setCode(ErrorCode.SYSTEM_ERROR.getCode());
        response.setMessage(ErrorCode.SYSTEM_ERROR.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse> handleRuntimeException(AppException exception) {
        ApiResponse response = new ApiResponse<>();
        ErrorCode errorCode = exception.getErrorCode();
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentException(MethodArgumentNotValidException exception) {
        ApiResponse response = new ApiResponse<>();

        String enumKey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_ERROR_KEY;
        try {
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException e) {
            errorCode = ErrorCode.INVALID_ERROR_KEY;
        }
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
