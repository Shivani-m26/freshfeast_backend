package com.foodsub.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private String username;
    private String email;
    private String gender;
    private java.time.LocalDate dateOfBirth;
}
