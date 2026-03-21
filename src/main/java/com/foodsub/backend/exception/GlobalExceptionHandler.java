package com.foodsub.backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception e) {
        e.printStackTrace(); // Log full stacktrace to server console
        Map<String, Object> error = new HashMap<>();
        error.put("error", e.getMessage() != null ? e.getMessage() : "Unknown Server Error");
        error.put("type", e.getClass().getSimpleName());
        // Find deep root cause if possible
        Throwable root = e;
        while(root.getCause() != null) root = root.getCause();
        error.put("rootCause", root.getMessage());
        return ResponseEntity.status(500).body(error);
    }
}
