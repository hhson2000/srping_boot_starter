package com.example.practice.exception;

import com.example.practice.comonResponse.ApiResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {
    private static final String MIN_ATTRIBUTE = "min";
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException exception) {
        log.error("Runtime exception occurred: ", exception);
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
        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(response);
    }

    @ExceptionHandler(value = AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse> handleAccessDeniedException(AuthorizationDeniedException exception) {
        ApiResponse response = new ApiResponse<>();
        response.setCode(ErrorCode.UNAUTHORIZE.getCode());
        response.setMessage(ErrorCode.UNAUTHORIZE.getMessage());
        return ResponseEntity.status(ErrorCode.UNAUTHORIZE.getHttpStatusCode()).body(response);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentException(MethodArgumentNotValidException exception) {
        String enumKey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_ERROR_KEY;
        Map<String, Object> attributes = null;
        try {
            errorCode = ErrorCode.valueOf(enumKey);
            var constraintViolation = exception.getBindingResult().getAllErrors()
                    .getFirst().unwrap(ConstraintViolation.class);
            attributes = constraintViolation.getConstraintDescriptor().getAttributes();
            log.info("Attribute: {}", attributes);

        } catch (IllegalArgumentException e) {
            errorCode = ErrorCode.INVALID_ERROR_KEY;
        }
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(Objects.nonNull(attributes) ?
                mapAttribute(errorCode.getMessage(), attributes)
                :errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    private String mapAttribute(String message, Map<String, Object> attributes){
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
        return message.replace("{" + MIN_ATTRIBUTE +"}", minValue);
    }
}
