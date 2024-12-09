package constants;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class BaseFareCalculator {

    private static final Map<String, Double> CITY_STATE_TAX_RATES = new HashMap<>() {{
        put("los angeles, ca", 0.075);  // California
        put("new york, ny", 0.085);    // New York
        put("houston, tx", 0.065);     // Texas
        put("miami, fl", 0.06);        // Florida
        put("chicago, il", 0.08);      // Illinois
        put("other", 0.05);            // Default for other locations
    }};

    public static double calculatePrice(double baseFare, double fuelSurcharge, String cityState, double multiplier, LocalTime departureTime) {
        // Normalize cityState input to lowercase and trim any extra spaces
        String normalizedCityState = cityState.trim().toLowerCase();

        // Get tax rate based on city/state, falling back to "other" if not found
        double taxRate = CITY_STATE_TAX_RATES.getOrDefault(normalizedCityState, CITY_STATE_TAX_RATES.get("other"));
        double taxes = baseFare * taxRate;
        double totalCostBeforeMultiplier = baseFare + fuelSurcharge + taxes;

        // Apply time-based multiplier
        double timeMultiplier = getTimeMultiplier(departureTime);
        double finalPrice = totalCostBeforeMultiplier * multiplier * timeMultiplier;

        // Round to two decimal places
        return Math.round(finalPrice * 100.0) / 100.0;
    }

    private static double getTimeMultiplier(LocalTime departureTime) {
        if (departureTime.isBefore(LocalTime.of(6, 0))) { // Very early morning (e.g., red-eye flights)
            return 0.8;
        } else if (departureTime.isBefore(LocalTime.of(9, 0))) { // Early morning (6:00 AM to 9:00 AM)
            return 0.9;
        } else if (departureTime.isBefore(LocalTime.of(17, 0))) { // Peak hours (9:01 AM to 5:00 PM)
            return 1.2;
        } else if (departureTime.isBefore(LocalTime.of(20, 0))) { // Evening (5:01 PM to 8:00 PM)
            return 1.1;
        } else { // Late night (8:01 PM to midnight)
            return 1.0;
        }
    }
}
