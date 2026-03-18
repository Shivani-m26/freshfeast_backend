package com.foodsub.backend.service;

import com.foodsub.backend.dto.CalculatorRequest;
import com.foodsub.backend.dto.CalculatorResponse;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CalculationService {

    public CalculatorResponse calculatePlan(CalculatorRequest request) {
        // Enforce defaults for nutrition calculation
        double weight = request.getWeight() != null ? request.getWeight() : 65.0;
        double height = request.getHeight() != null ? request.getHeight() : 165.0;
        int age = request.getAge() != null ? request.getAge() : 25;
        String gender = request.getGender() != null ? request.getGender() : "MALE";

        // BMI = weight(kg) / height(m)^2
        double heightInM = height / 100.0;
        double bmi = weight / (heightInM * heightInM);
        
        String bmiCat = getBMICategory(bmi);
        
        // BMR (Mifflin-St Jeor)
        double bmr = (10 * weight) + (6.25 * height) - (5 * age);
        if ("MALE".equalsIgnoreCase(gender)) {
            bmr += 5;
        } else {
            bmr -= 161;
        }
        
        // Maintenance TDEE (Sedentary 1.25)
        double tdee = bmr * 1.25;
        
        double suggested = tdee;
        String goal = request.getGoal() != null ? request.getGoal() : "MAINTAIN";
        String suggestionText = "Maintain healthy eating habits.";
        
        if ("WEIGHT_LOSS".equalsIgnoreCase(goal) || "FAT_LOSS".equalsIgnoreCase(goal)) {
            suggested -= 500;
            suggestionText = "Consuming fewer calories than you burn will help in fat reduction.";
        } else if ("WEIGHT_GAIN".equalsIgnoreCase(goal)) {
            suggested += 500;
            suggestionText = "Calorie surplus is necessary for weight gain.";
        } else if ("MUSCLE_UP".equalsIgnoreCase(goal)) {
            suggested += 300;
            suggestionText = "Slight surplus with high protein is ideal for muscle synthesis.";
        }
        
        return CalculatorResponse.builder()
                .bmi(Math.round(bmi * 10.0) / 10.0)
                .bmiCategory(bmiCat)
                .suggestedCalories(Math.round(suggested * 1.0) / 1.0)
                .suggestion(suggestionText)
                .build();
    }
    
    private String getBMICategory(double bmi) {
        if (bmi < 18.5) return "Underweight";
        if (bmi < 25) return "Normal weight";
        if (bmi < 30) return "Overweight";
        return "Obese";
    }

    public Double calculateSubscriptionPrice(String planType, String protein, Integer mealsPerDay, Integer totalDays, List<String> healthIssues) {
        double pricePerMeal = 50.0; // Basic plan: 50 per meal
        
        if ("PRO".equalsIgnoreCase(planType)) {
            if ("50g".equalsIgnoreCase(protein)) pricePerMeal = 100.0;
            else if ("100g".equalsIgnoreCase(protein)) pricePerMeal = 120.0;
            else if ("150g".equalsIgnoreCase(protein)) pricePerMeal = 150.0;
            else if (">150g".equalsIgnoreCase(protein)) pricePerMeal = 200.0;
        } else if ("CUSTOMIZED".equalsIgnoreCase(planType)) {
            pricePerMeal = 150.0; // Custom starts higher due to expert menu
            if (healthIssues != null && !healthIssues.isEmpty()) {
                pricePerMeal += (healthIssues.size() * 20.0); // Adjust for complexity
            }
        }
        
        return pricePerMeal * (mealsPerDay != null ? mealsPerDay : 1) * (totalDays != null ? totalDays : 30);
    }
}
