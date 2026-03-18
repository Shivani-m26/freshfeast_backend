package com.foodsub.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "meals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;
    private String category; // e.g., Veg, Non-Veg, Vegan
    private Integer calories;
    private String imagePath;
    @Builder.Default
    private boolean isAvailable = true;
}
