package constants;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DynamicPricing {

    public static double getDynamicMultiplier(String season, Date travelDate) {
        double multiplier = 1.1; // Start with a default multiplier for regular days

        // Convert Date to LocalDate
        LocalDate localTravelDate = travelDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        // Spring Break (March 15–31)
        if (season.equalsIgnoreCase("spring")) {
            if (localTravelDate.getMonthValue() == 3 && localTravelDate.getDayOfMonth() >= 15 && localTravelDate.getDayOfMonth() <= 31) {
                multiplier = 1.6;
            }
        }

        // Summer Season
        if (season.equalsIgnoreCase("summer")) {
            multiplier = 1.4;
        }

        // Thanksgiving Week (November 20–27)
        if (localTravelDate.getMonthValue() == 11 && localTravelDate.getDayOfMonth() >= 20 && localTravelDate.getDayOfMonth() <= 27) {
            multiplier = 1.8;
        }

        // Christmas Week (December 20–27)
        if (localTravelDate.getMonthValue() == 12 && localTravelDate.getDayOfMonth() >= 20 && localTravelDate.getDayOfMonth() <= 27) {
            multiplier = 1.8;
        }

        // Adjust for weekends (Friday, Saturday, Sunday)
        DayOfWeek dayOfWeek = localTravelDate.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            multiplier += 0.2; // Add 0.2 for weekend travel
        }

        return multiplier; // Return the calculated multiplier
    }
}


