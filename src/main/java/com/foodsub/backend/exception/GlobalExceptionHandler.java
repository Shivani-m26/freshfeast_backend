package com.foodsub.backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        e.printStackTrace(); // Log to console for the developer
        Map<String, String> error = new HashMap<>();
        error.put("error", e.getMessage());
        error.put("type", e.getClass().getSimpleName());
        return ResponseEntity.status(500).body(error);
    }
}
