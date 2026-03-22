package com.foodsub.backend.controller;

import com.foodsub.backend.service.EmailService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.Map;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ContactController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<Map<String, String>> sendMessage(@RequestBody ContactForm form) {
        emailService.sendContactEmail(form.getEmail(), form.getName(), form.getSubject(), form.getMessage());
        return ResponseEntity.ok(Map.of("message", "Message sent successfully"));
    }

    @Data
    static class ContactForm {
        private String name;
        private String email;
        private String subject;
        private String message;
    }
}
