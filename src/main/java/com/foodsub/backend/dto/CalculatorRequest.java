package com.foodsub.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalculatorRequest {
    private Double weight;
    private Double height;
    private Integer age;
    private String gender;
    private String goal;
}
