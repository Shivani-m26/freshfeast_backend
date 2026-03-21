package com.foodsub.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendContactEmail(String fromEmail, String name, String subject, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("shivanimanivannan1@gmail.com");
        mail.setSubject("FreshFeast Query: " + subject);
        mail.setText("Name: " + name + "\nEmail: " + fromEmail + "\n\nMessage:\n" + message);
        mailSender.send(mail);
    }
}
