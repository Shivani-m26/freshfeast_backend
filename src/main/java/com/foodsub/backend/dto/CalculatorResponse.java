package com.foodsub.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalculatorResponse {
    private Double bmi;
    private String bmiCategory;
    private Double suggestedCalories;
    private String suggestion;
}
