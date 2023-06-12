
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class UserManagement {

    private String userName;
    private List<HealthDataEntry> calorieIntake;
    private List<HealthDataEntry> exerciseActivities;
    private List<HealthDataEntry> sleepRecords = new ArrayList<>();

    public UserManagement(String userName) {
        this.userName = userName;
        this.calorieIntake = new ArrayList<>();
        this.exerciseActivities = new ArrayList<>();
        this.sleepRecords = new ArrayList<>();
    }

    public List<HealthDataEntry> getCalorieIntake() {
        return calorieIntake;
    }

    public List<HealthDataEntry> getExerciseActivities() {
        return exerciseActivities;
    }

    public List<HealthDataEntry> getSleepRecords() {
        return sleepRecords;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void addCalorieIntake(String foodItem, double caloricValue, LocalDate date) {
        HealthDataEntry entry = new HealthDataEntry("Calorie Intake", foodItem, caloricValue, date);
        calorieIntake.add(entry);
    }


    public void addExerciseActivity(String exerciseType, int duration, double caloriesBurned, LocalDate date) {
        HealthDataEntry entry = new HealthDataEntry("Exercise Activity", exerciseType, duration, caloriesBurned, date);
        exerciseActivities.add(entry);
        System.out.println("Exercise activity logged successfully");
    }

    public void addSleepRecord(LocalDate date, LocalTime sleepTime, LocalTime wakeUpTime) {
        String sleepRecordData;
        if (wakeUpTime != null) {
            long sleepDuration = ChronoUnit.HOURS.between(sleepTime, wakeUpTime);
            sleepRecordData = sleepTime + " - " + wakeUpTime + ", Sleep Duration: " + sleepDuration + " hours";
        } else {
            sleepRecordData = sleepTime.toString() + " - N/A, Sleep Duration: N/A";
        }

        HealthDataEntry entry = new HealthDataEntry("Sleep Record", sleepRecordData, date);
        sleepRecords.add(entry);
        System.out.println("Sleep record logged successfully");
    }


    public void healthDataInput(Scanner scanner) {
        boolean exit = false;

        while (!exit) {
            System.out.println("---Health Data Input---");
            System.out.println("1) Add daily calorie intake");
            System.out.println("2) Add exercise activities");
            System.out.println("3) Add sleep records");
            System.out.println("4) View daily caloric balance");
            System.out.println("5) View sleep analysis");
            System.out.println("6) View exercise log");
            System.out.println("7) Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    enterCalorieIntake(scanner);
                    break;
                case 2:
                    logExerciseActivity(scanner);
                    break;
                case 3:
                    recordSleep(scanner);
                    break;
                case 4:
                    viewDailyCaloricBalance();
                    break;
                case 5:
                    viewSleepAnalysis(scanner);
                    viewExerciseSummaries();
                    break;
                case 6:
                    viewExerciseLog();
                    break;
                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
        Main.saveData();
    }

    private void enterCalorieIntake(Scanner scanner) {
        System.out.println("Enter the food item consumed:");
        String foodItem = scanner.nextLine();
        System.out.println("Enter the caloric value:");
        double caloricValue = scanner.nextDouble();
        scanner.nextLine();

        LocalDate date = null;
        while (date == null) {
            System.out.println("Enter the date consumed (MM/DD/YYYY):");
            String dateStr = scanner.nextLine();
            date = parseDate(dateStr);
        }

        addCalorieIntake(foodItem, caloricValue, date);
    }

    private void logExerciseActivity(Scanner scanner) {
        System.out.println("Enter the type of exercise:");
        String exerciseType = scanner.nextLine();
        System.out.println("Enter the duration (in minutes):");
        int duration = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the estimated calories burned:");
        double caloriesBurned = scanner.nextDouble();
        scanner.nextLine();

        LocalDate date = null;
        while (date == null) {
            System.out.println("Enter the date exercise was completed (MM/DD/YYYY):");
            String dateStr = scanner.nextLine();
            date = parseDate(dateStr);
        }

        addExerciseActivity(exerciseType, duration, caloriesBurned, date);
    }

    private void recordSleep(Scanner scanner) {
        LocalTime sleepTime = null;
        while (sleepTime == null) {
            System.out.println("Enter the time you went to sleep (HH:MM AM/PM):");
            String sleepTimeStr = scanner.nextLine();
            sleepTime = parseTime(sleepTimeStr);
        }

        LocalTime wakeUpTime = null;
        while (wakeUpTime == null) {
            System.out.println("Enter the time you woke up (HH:MM AM/PM):");
            String wakeUpTimeStr = scanner.nextLine();
            wakeUpTime = parseTime(wakeUpTimeStr);
        }

        LocalDate date = null;
        while (date == null) {
            System.out.println("Enter the date (MM/DD/YYYY):");
            String dateStr = scanner.nextLine();
            date = parseDate(dateStr);
        }

        addSleepRecord(date, sleepTime, wakeUpTime);
        Main.saveData();
    }

    private LocalTime parseTime(String timeStr) {
        try {
            return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("h:mm a"));
        } catch (DateTimeParseException e) {
            System.out.println("Invalid time format. Please enter the time in the format HH:MM AM/PM.");
            return null;
        }
    }

    private LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter the date in the format MM/DD/YYYY.");
            return null;
        }
    }


    private String formatTime(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
        return time.format(formatter);
    }


    //DATA ANALYSIS CODE


    public void viewDailyCaloricBalance() {
        LocalDate currentDate = LocalDate.now();
        List<HealthDataEntry> calorieIntake = getCalorieIntake();
        List<HealthDataEntry> exerciseActivities = getExerciseActivities();
        double caloricBalance = HealthSummary.calculateDailyCaloricBalance(calorieIntake, exerciseActivities, currentDate);


        System.out.println();
        System.out.println("--- Daily Caloric Balance ---");
        System.out.println("Date: " + currentDate);
        System.out.println("Caloric Balance: " + caloricBalance);
        System.out.println();
    }


    //SLEEP DURATION


    private Duration calculateSleepDuration(LocalTime sleepTime, LocalTime wakeUpTime) {
        Duration sleepDuration;

        LocalDate currentDate = LocalDate.now();
        LocalDateTime sleepDateTime = LocalDateTime.of(currentDate, sleepTime);
        LocalDateTime wakeUpDateTime = LocalDateTime.of(currentDate, wakeUpTime);

        if (wakeUpDateTime.isBefore(sleepDateTime)) {
            wakeUpDateTime = wakeUpDateTime.plusDays(1);
        }

        sleepDuration = Duration.between(sleepDateTime, wakeUpDateTime);

        return sleepDuration;
    }

    private double calculateAverageSleepDuration(LocalDate startDate, LocalDate endDate) {
        List<Double> sleepDurations = new ArrayList<>();

        for (HealthDataEntry entry : sleepRecords) {
            LocalDate entryDate = entry.getDate();
            if (entryDate.isAfter(startDate) && entryDate.isBefore(endDate.plusDays(1))) {
                LocalTime sleepTime = entry.getSleepTime();
                LocalTime wakeUpTime = entry.getWakeUpTime();
                double duration = calculateSleepDuration(sleepTime, wakeUpTime).toHours();
                sleepDurations.add(duration);
            }
        }

        double sum = 0;
        for (double duration : sleepDurations) {
            sum += duration;
        }

        return sum / sleepDurations.size();
    }


    private void viewSleepAnalysis(Scanner scanner) {
        System.out.println("---Sleep Analysis---");
        System.out.print("Enter the start date (MM/dd/yyyy): ");
        String startDateString = scanner.next();
        System.out.print("Enter the end date (MM/dd/yyyy): ");
        String endDateString = scanner.next();

        try {
            LocalDate startDate = LocalDate.parse(startDateString, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            LocalDate endDate = LocalDate.parse(endDateString, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

            long totalSleepDuration = 0;
            int sleepRecordsCount = 0;

            for (HealthDataEntry entry : sleepRecords) {
                LocalDate entryDate = entry.getDate();
                if (!entryDate.isBefore(startDate) && !entryDate.isAfter(endDate)) {
                    LocalTime sleepTime = entry.getSleepTime();
                    LocalTime wakeUpTime = entry.getWakeUpTime();
                    if (sleepTime != null && wakeUpTime != null) {
                        Duration sleepDuration = Duration.between(sleepTime, wakeUpTime);
                        totalSleepDuration += sleepDuration.toMinutes();
                        sleepRecordsCount++;
                    }
                }
            }

            if (sleepRecordsCount > 0) {
                long averageSleepDuration = totalSleepDuration / sleepRecordsCount;
                System.out.println("Period: " + startDateString + " to " + endDateString);
                System.out.println("Average Sleep Duration: " + averageSleepDuration / 60 + " hours");
                for (HealthDataEntry entry : sleepRecords) {
                    LocalDate entryDate = entry.getDate();
                    if (!entryDate.isBefore(startDate) && !entryDate.isAfter(endDate)) {
                        LocalTime sleepTime = entry.getSleepTime();
                        LocalTime wakeUpTime = entry.getWakeUpTime();
                        if (sleepTime != null && wakeUpTime != null) {
                            Duration sleepDuration = Duration.between(sleepTime, wakeUpTime);
                            long sleepDurationHours = sleepDuration.toMinutes() / 60;
                            if (sleepDurationHours < averageSleepDuration - 1) {
                                System.out.println("Day with sleep duration significantly less than the average: " + entryDate + ", Duration: " + sleepDurationHours + " hours");
                            }
                        }
                    }
                }
            } else {
                System.out.println("No sleep records found between " + startDateString + " and " + endDateString);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Sleep analysis not performed.");
        }
    }


    private List<HealthDataEntry> findSleepDurationsBelowAverage(LocalDate startDate, LocalDate endDate, double averageDuration) {
        List<HealthDataEntry> belowAverageEntries = new ArrayList<>();

        for (HealthDataEntry entry : sleepRecords) {
            LocalDate entryDate = entry.getDate();
            if (entryDate.isAfter(startDate) && entryDate.isBefore(endDate.plusDays(1))) {
                LocalTime sleepTime = entry.getSleepTime();
                LocalTime wakeUpTime = entry.getWakeUpTime();
                double duration = calculateSleepDuration(sleepTime, wakeUpTime).toHours();
                if (duration < averageDuration) {
                    belowAverageEntries.add(entry);
                }
            }
        }

        return belowAverageEntries;
    }


    //EXERCISE


    public void viewExerciseLog() {
        System.out.println("--- Exercise Log ---");
        List<ExerciseActivity> exercises = new ArrayList<>();
        for (HealthDataEntry entry : exerciseActivities) {
            if (entry instanceof ExerciseActivity) {
                ExerciseActivity exercise = (ExerciseActivity) entry;
                exercises.add(exercise);
            }
        }

        if (exercises.isEmpty()) {
            System.out.println("No exercise activities recorded");
        } else {
            for (ExerciseActivity exercise : exercises) {
                System.out.println(exercise);
            }
        }
        System.out.println();
    }



    public void viewExerciseSummaries() {
        System.out.println("--- Exercise Summaries ---");
        if (exerciseActivities.isEmpty()) {
            System.out.println("No exercise activities recorded");
        } else {
            Map<String, ExerciseSummary> summaryMap = new HashMap<>();
            for (HealthDataEntry entry : exerciseActivities) {
                if (entry instanceof ExerciseActivity) {
                    ExerciseActivity activity = (ExerciseActivity) entry;
                    String exerciseType = activity.getExerciseType();
                    int duration = activity.getDuration();
                    double caloriesBurned = activity.getCaloriesBurned();

                    ExerciseSummary summary = summaryMap.getOrDefault(exerciseType, new ExerciseSummary(exerciseType));
                    summary.addEntry(duration, caloriesBurned);
                    summaryMap.put(exerciseType, summary);
                }
            }

            for (ExerciseSummary summary : summaryMap.values()) {
                System.out.println(summary);
            }
        }
        System.out.println();
    }


    private static class ExerciseActivity extends HealthDataEntry {
        private String exerciseType;
        private int duration;
        private double caloriesBurned;

        public ExerciseActivity(String exerciseType, int duration, double caloriesBurned, LocalDate date) {
            super("Exercise Activity", date);
            this.exerciseType = exerciseType;
            this.duration = duration;
            this.caloriesBurned = caloriesBurned;
        }

        public String getExerciseType() {
            return exerciseType;
        }

        public int getDuration() {
            return duration;
        }

        public double getCaloriesBurned() {
            return caloriesBurned;
        }

        @Override
        public String toString() {
            return "Exercise: " + exerciseType + ", Duration: " + duration + " minutes, Calories Burned: " + caloriesBurned;
        }
    }


    private static class ExerciseSummary {
        private String exerciseType;
        private int totalDuration;
        private double totalCaloriesBurned;

        public ExerciseSummary(String exerciseType) {
            this.exerciseType = exerciseType;
            this.totalDuration = 0;
            this.totalCaloriesBurned = 0.0;
        }

        public void addEntry(int duration, double caloriesBurned) {
            totalDuration += duration;
            totalCaloriesBurned += caloriesBurned;
        }

        @Override
        public String toString() {
            return "Exercise Type: " + exerciseType + ", Total Duration: " + totalDuration +
                    " minutes, Total Calories Burned: " + totalCaloriesBurned;
        }
    }

}
