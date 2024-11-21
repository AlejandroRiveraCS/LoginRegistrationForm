package constants;

import java.time.LocalDate;
import java.time.DayOfWeek;

public class DynamicPricing {

    public static double getDynamicMultiplier(String season, LocalDate travelDate) {
        double multiplier = 1.1; // Start with a default multiplier for regular days

        // Spring Break (March 15–31)
        if (season.equalsIgnoreCase("spring")) {
            if (travelDate.getMonthValue() == 3 && travelDate.getDayOfMonth() >= 15 && travelDate.getDayOfMonth() <= 31) {
                multiplier = 1.6;
            }
        }

        // Summer Season
        if (season.equalsIgnoreCase("summer")) {
            multiplier = 1.4;
        }

        // Thanksgiving Week (November 20–27)
        if (travelDate.getMonthValue() == 11 && travelDate.getDayOfMonth() >= 20 && travelDate.getDayOfMonth() <= 27) {
            multiplier = 1.8;
        }

        // Christmas Week (December 20–27)
        if (travelDate.getMonthValue() == 12 && travelDate.getDayOfMonth() >= 20 && travelDate.getDayOfMonth() <= 27) {
            multiplier = 1.8;
        }

        // Adjust for weekends (Friday, Saturday, Sunday)
        DayOfWeek dayOfWeek = travelDate.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            multiplier += 0.2; // Add 0.2 for weekend travel
        }

        return multiplier; // Return the calculated multiplier
    }
}
