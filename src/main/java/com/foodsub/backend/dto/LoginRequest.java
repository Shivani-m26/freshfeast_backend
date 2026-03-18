package com.foodsub.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    private String usernameOrEmail;
    private String password;
}
