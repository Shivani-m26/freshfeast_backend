package com.foodsub.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subscription_plans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer mealsPerWeek;

    @Builder.Default
    private boolean isActive = true;
    private String imagePath;
}
