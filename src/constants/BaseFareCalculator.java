package constants;

import java.util.Map;

public class BaseFareCalculator {

    // Example tax rates for cities/states
    private static final Map<String, Double> CITY_STATE_TAX_RATES = Map.of(
            "Los Angeles, CA", 0.075,  // California
            "New York, NY", 0.085,    // New York
            "Houston, TX", 0.065,     // Texas
            "Miami, FL", 0.06,        // Florida
            "Chicago, IL", 0.08,      // Illinois
            "Other", 0.05             // Default for other locations
    );

    public static double calculatePrice(double baseFare, double fuelSurcharge, String cityState, double multiplier) {
        // Step 1: Get the tax rate for the departure city/state
        double taxRate = CITY_STATE_TAX_RATES.getOrDefault(cityState, CITY_STATE_TAX_RATES.get("Other"));

        // Step 2: Calculate the taxes
        double taxes = baseFare * taxRate;

        // Step 3: Add base fare, fuel surcharge, and taxes
        double totalCostBeforeMultiplier = baseFare + fuelSurcharge + taxes;

        // Step 4: Apply the multiplier
        double finalPrice = totalCostBeforeMultiplier * multiplier;

        // Step 5: Return the rounded price
        return Math.round(finalPrice * 100.0) / 100.0;
    }
}
