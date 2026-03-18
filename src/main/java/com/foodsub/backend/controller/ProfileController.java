package com.foodsub.backend.controller;

import com.foodsub.backend.dto.ProfileResponse;
import com.foodsub.backend.entity.AppUser;
import com.foodsub.backend.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final AppUserRepository userRepository;

    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile(Authentication authentication) {
        AppUser user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return ResponseEntity.ok(ProfileResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .gender(user.getGender())
                .dateOfBirth(user.getDateOfBirth())
                .age(user.getAge())
                .height(user.getHeight())
                .weight(user.getWeight())
                .healthIssues(user.getHealthIssues())
                .address(user.getAddress())
                .currentSubscription(user.getCurrentSubscription())
                .subscriptions(user.getSubscriptions())
                .build());
    }
}
