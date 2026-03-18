package com.foodsub.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user_subscriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private AppUser user;

    private String planType; // BASIC, PRO, CUSTOMIZED
    private String proteinAmount; // 50g, 100g, 150g, >150g
    private Integer mealsPerDay;
    
    @ElementCollection
    private List<String> selectedDays; // ALL, WEEKDAYS, WEEKENDS, or specific "Monday", etc.
    
    private Double totalPrice;
    private String status; // ACTIVE, EXPIRED, PENDING_PAYMENT
    
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // Custom Plan Details
    private Double height;
    private Double weight;
    private String goal; // WEIGHT_LOSS, FAT_LOSS, WEIGHT_GAIN, MUSCLE_UP
    private String preference; // VEG, NON_VEG, VEGAN, EGGETARIAN
    
    @ElementCollection
    private List<String> healthIssues;

    private String deliveryAddress;
    private String paymentReference;
    private Double suggestedCalories;
}
