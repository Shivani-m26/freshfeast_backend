package com.foodsub.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;

@Entity
@Table(name = "meal_timings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealTiming {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private UserSubscription subscription;

    private String mealType; // MORNING, LUNCH, DINNER
    private LocalTime preferredTime;
}
