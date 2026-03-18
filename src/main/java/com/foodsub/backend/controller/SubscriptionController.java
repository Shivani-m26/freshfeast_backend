package com.foodsub.backend.controller;

import com.foodsub.backend.dto.SubscriptionRequest;
import com.foodsub.backend.entity.UserSubscription;
import com.foodsub.backend.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<UserSubscription> subscribe(Authentication authentication, @RequestBody SubscriptionRequest request) {
        return ResponseEntity.ok(subscriptionService.createSubscription(authentication.getName(), request));
    }

    @PostMapping("/{id}/confirm-payment")
    public ResponseEntity<UserSubscription> confirmPayment(@PathVariable Long id, @RequestParam String paymentRef) {
        return ResponseEntity.ok(subscriptionService.confirmPayment(id, paymentRef));
    }
}
