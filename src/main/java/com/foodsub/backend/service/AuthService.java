package com.foodsub.backend.service;

import com.foodsub.backend.dto.*;
import com.foodsub.backend.entity.AppUser;
import com.foodsub.backend.repository.AppUserRepository;
import com.foodsub.backend.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent() ||
            userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        AppUser user = AppUser.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .gender(request.getGender())
                .dateOfBirth(request.getDateOfBirth())
                .roles(Collections.singleton("ROLE_USER"))
                .build();

        userRepository.save(user);
        
        // Auto-login after registration
        return login(new LoginRequest(request.getEmail(), request.getPassword()));
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateToken(userDetails);

        AppUser user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .gender(user.getGender())
                .dateOfBirth(user.getDateOfBirth())
                .build();
    }

    public String generateResetToken(String email) {
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        // Expiry could be added here
        userRepository.save(user);
        return token;
    }

    public void resetPassword(String token, String newPassword) {
        AppUser user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid reset token"));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        userRepository.save(user);
    }
}
