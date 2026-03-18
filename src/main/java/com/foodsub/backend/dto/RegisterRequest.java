package com.foodsub.backend.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String email;
    private String username;
    private String password;
    private String fullName;
    private String gender;
    private LocalDate dateOfBirth;
}
