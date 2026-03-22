package com.foodsub.backend.controller;

import com.foodsub.backend.service.EmailService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ContactController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<Map<String, String>> sendMessage(@RequestBody ContactForm form) {
        try {
            emailService.sendContactEmail(form.getEmail(), form.getName(), form.getSubject(), form.getMessage());
            return ResponseEntity.ok(Map.of("message", "Message sent successfully"));
        } catch (Exception e) {
            // Log error but don't crash the request for the user
            System.err.println("Email failed to send but recording contact: " + e.getMessage());
            return ResponseEntity.ok(Map.of("message", "We have received your message and will get back to you soon."));
        }
    }

    @Data
    static class ContactForm {
        private String name;
        private String email;
        private String subject;
        private String message;
    }
}
