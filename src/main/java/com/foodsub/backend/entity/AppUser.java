package com.foodsub.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Set;
import java.util.List;

@Entity
@Table(name = "app_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String fullName;
    private String gender;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String address;

    private Double height;
    private Double weight;
    
    @Builder.Default
    @ElementCollection
    private List<String> healthIssues = new java.util.ArrayList<>();

    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new java.util.HashSet<>();

    private String resetToken;
    private LocalDate resetTokenExpiry;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("id DESC")
    @com.fasterxml.jackson.annotation.JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<UserSubscription> subscriptions = new java.util.ArrayList<>();

    public UserSubscription getCurrentSubscription() {
        if (subscriptions == null || subscriptions.isEmpty()) return null;
        return subscriptions.stream()
                .filter(s -> "ACTIVE".equals(s.getStatus()) || "PENDING_PAYMENT".equals(s.getStatus()))
                .findFirst()
                .orElse(null);
    }

    public Integer getAge() {
        if (dateOfBirth == null) return null;
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
}
