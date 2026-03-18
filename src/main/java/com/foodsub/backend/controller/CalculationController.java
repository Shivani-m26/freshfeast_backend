package com.foodsub.backend.controller;

import com.foodsub.backend.dto.CalculatorRequest;
import com.foodsub.backend.dto.CalculatorResponse;
import com.foodsub.backend.service.CalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculate")
@RequiredArgsConstructor
public class CalculationController {

    private final CalculationService calculationService;

    @PostMapping
    public ResponseEntity<CalculatorResponse> calculate(@RequestBody CalculatorRequest request) {
        return ResponseEntity.ok(calculationService.calculatePlan(request));
    }
}
