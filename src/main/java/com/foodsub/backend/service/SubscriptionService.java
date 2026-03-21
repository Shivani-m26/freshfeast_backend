package com.foodsub.backend.service;

import com.foodsub.backend.dto.SubscriptionRequest;
import com.foodsub.backend.entity.AppUser;
import com.foodsub.backend.entity.UserSubscription;
import com.foodsub.backend.repository.AppUserRepository;
import com.foodsub.backend.repository.UserSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final UserSubscriptionRepository subscriptionRepository;
    private final AppUserRepository userRepository;
    private final CalculationService calculationService;

    @Transactional
    public UserSubscription createSubscription(String username, SubscriptionRequest request) {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        int totalDays = 30;
        String freq = request.getFrequency() != null ? request.getFrequency() : "ALL";

        if ("WEEKDAYS".equalsIgnoreCase(freq)) totalDays = 22;
        else if ("WEEKENDS".equalsIgnoreCase(freq)) totalDays = 8;
        else if ("CUSTOM".equalsIgnoreCase(freq) && request.getCustomDays() != null) totalDays = request.getCustomDays().size();

        Double totalPrice = calculationService.calculateSubscriptionPrice(
                request.getPlanType(),
                request.getProteinAmount(),
                request.getMealsPerDay(),
                totalDays,
                request.getHealthIssues()
        );

        UserSubscription sub = UserSubscription.builder()
                .user(user)
                .planType(request.getPlanType())
                .proteinAmount(request.getProteinAmount())
                .mealsPerDay(request.getMealsPerDay() != null ? request.getMealsPerDay() : 1)
                .selectedDays(freq.equals("CUSTOM") ? request.getCustomDays() : java.util.Collections.singletonList(freq))
                .totalPrice(totalPrice)
                .status("PENDING_PAYMENT")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(30))
                .weight(request.getWeight() != null ? request.getWeight() : 70.0)
                .height(request.getHeight() != null ? request.getHeight() : 170.0)
                .goal(request.getGoal() != null ? request.getGoal() : "WEIGHT_LOSS")
                .preference(request.getPreference() != null ? request.getPreference() : "VEG")
                .healthIssues(request.getHealthIssues())
                .deliveryAddress(request.getDeliveryAddress())
                .build();

        if ("CUSTOMIZED".equalsIgnoreCase(request.getPlanType())) {
             Integer userAge = user.getAge() != null ? user.getAge() : 25;
             String userGender = user.getGender() != null ? user.getGender() : "MALE";
             Double uWeight = request.getWeight() != null ? request.getWeight() : 65.0;
             Double uHeight = request.getHeight() != null ? request.getHeight() : 165.0;

             var calcRes = calculationService.calculatePlan(
                 com.foodsub.backend.dto.CalculatorRequest.builder()
                     .height(uHeight)
                     .weight(uWeight)
                     .age(userAge)
                     .gender(userGender)
                     .goal(request.getGoal() != null ? request.getGoal() : "WEIGHT_LOSS")
                     .build()
             );
             sub.setSuggestedCalories(calcRes.getSuggestedCalories());
        }

        sub.setUser(user);
        user.getSubscriptions().add(sub);
        
        user.setHeight(request.getHeight() != null ? request.getHeight() : user.getHeight());
        user.setWeight(request.getWeight() != null ? request.getWeight() : user.getWeight());
        if (request.getHealthIssues() != null) user.setHealthIssues(request.getHealthIssues());
        if (request.getDeliveryAddress() != null) user.setAddress(request.getDeliveryAddress());
        if (request.getContactNumber() != null) user.setPhoneNumber(request.getContactNumber());
        
        return subscriptionRepository.save(sub);
    }

    @Transactional
    public UserSubscription confirmPayment(Long id, String paymentRef) {
        UserSubscription sub = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));
        sub.setStatus("ACTIVE");
        sub.setPaymentReference(paymentRef);
        return subscriptionRepository.save(sub);
    }

    @Transactional
    public void deleteSubscription(Long id) {
        subscriptionRepository.deleteById(id);
    }
}
