package constants;

import java.time.LocalTime;
import java.util.Map;

public class BaseFareCalculator {

    private static final Map<String, Double> CITY_STATE_TAX_RATES = Map.of(
            "Los Angeles, CA", 0.075,  // California
            "New York, NY", 0.085,    // New York
            "Houston, TX", 0.065,     // Texas
            "Miami, FL", 0.06,        // Florida
            "Chicago, IL", 0.08,      // Illinois
            "Other", 0.05             // Default for other locations
    );

    public static double calculatePrice(double baseFare, double fuelSurcharge, String cityState, double multiplier, LocalTime departureTime) {
        double taxRate = CITY_STATE_TAX_RATES.getOrDefault(cityState, CITY_STATE_TAX_RATES.get("Other"));
        double taxes = baseFare * taxRate;
        double totalCostBeforeMultiplier = baseFare + fuelSurcharge + taxes;

        // Apply time-based multiplier
        double timeMultiplier = getTimeMultiplier(departureTime);
        double finalPrice = totalCostBeforeMultiplier * multiplier * timeMultiplier;

        return Math.round(finalPrice * 100.0) / 100.0;
    }

    private static double getTimeMultiplier(LocalTime departureTime) {
        if (departureTime.isBefore(LocalTime.of(9, 0))) { // Early morning (5:00 AM to 9:00 AM)
            return 0.9;
        } else if (departureTime.isBefore(LocalTime.of(17, 0))) { // Peak hours (9:01 AM to 5:00 PM)
            return 1.2;
        } else { // Late evening (5:01 PM to midnight)
            return 1.0;
        }
    }
}

