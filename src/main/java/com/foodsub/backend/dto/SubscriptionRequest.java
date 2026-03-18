package com.foodsub.backend.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionRequest {
    private String planType;
    private String proteinAmount;
    private Integer mealsPerDay;
    private String frequency; // ALL_DAYS, WEEKDAYS, WEEKENDS, CUSTOM
    private List<String> customDays;
    
    // For Customized Plan
    private Double weight;
    private Double height;
    private String goal;
    private String preference;
    private List<String> healthIssues;
    private String deliveryAddress;
    private String contactNumber;
}
