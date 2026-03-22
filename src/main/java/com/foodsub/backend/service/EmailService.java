package com.foodsub.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @org.springframework.beans.factory.annotation.Value("${spring.mail.username}")
    private String adminEmail;

    public void sendContactEmail(String fromEmail, String name, String subject, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(adminEmail);
        mail.setReplyTo(fromEmail);
        mail.setSubject("FreshFeast Query from " + name + ": " + subject);
        mail.setText("New query from: " + name + "\nCustomer Email: " + fromEmail + "\n\nMessage:\n" + message);
        mailSender.send(mail);
    }
}
