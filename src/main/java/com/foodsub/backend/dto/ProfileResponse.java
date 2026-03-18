package com.foodsub.backend.dto;

import com.foodsub.backend.entity.UserSubscription;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
    private Long id;
    private String email;
    private String username;
    private String fullName;
    private String gender;
    private LocalDate dateOfBirth;
    private Integer age;
    private Double height;
    private Double weight;
    private List<String> healthIssues;
    private String address;
    private UserSubscription currentSubscription;
    private List<UserSubscription> subscriptions;
}
