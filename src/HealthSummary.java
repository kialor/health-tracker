import java.time.LocalDate;
import java.util.List;

public class HealthSummary {
    public static double calculateDailyCaloricBalance(List<HealthDataEntry> calorieIntake, List<HealthDataEntry> exerciseActivities, LocalDate date) {
        double totalCaloriesConsumed = 0;
        double totalCaloriesBurned = 0;

        for (HealthDataEntry entry : calorieIntake) {
            if (entry.getDate().equals(date)) {
                totalCaloriesConsumed += entry.getCaloricValue();
            }
        }

        for (HealthDataEntry entry : exerciseActivities) {
            if (entry.getDate().equals(date)) {
                totalCaloriesBurned += entry.getCaloriesBurned();
            }
        }

        return totalCaloriesConsumed - totalCaloriesBurned;
    }


}
