package com.albymens.task_management.service;

import com.albymens.task_management.response.APIResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<APIResponse> handleJwtException(JwtException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new APIResponse(false, "Invalid or expired token", null));
    }
}
