
import java.time.LocalDate;
import java.time.LocalTime;

class HealthDataEntry {
    private String entryType;
    private String foodItem;
    private double caloricValue;
    private int duration;
    private double caloriesBurned;
    private String sleepRecordData;
    private LocalDate date;
    private LocalTime sleepTime;
    private LocalTime wakeUpTime;

    public HealthDataEntry(String entryType, String foodItem, double caloricValue, LocalDate date) {
        this.entryType = entryType;
        this.foodItem = foodItem;
        this.caloricValue = caloricValue;
        this.date = date;
    }

    public HealthDataEntry(String entryType, String exerciseType, int duration, double caloriesBurned, LocalDate date) {
        this.entryType = entryType;
        this.foodItem = exerciseType;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.date = date;
    }

    public HealthDataEntry(String entryType, String sleepRecordData, LocalDate date, LocalTime sleepTime, LocalTime wakeUpTime) {
        this.entryType = entryType;
        this.sleepRecordData = sleepRecordData;
        this.date = date;
        this.sleepTime = sleepTime;
        this.wakeUpTime = wakeUpTime;
    }

    public HealthDataEntry(String entryType, String sleepRecordData, LocalDate date) {
        this.entryType = entryType;
        this.sleepRecordData = sleepRecordData;
        this.date = date;
    }

    public HealthDataEntry(String exerciseActivity, LocalDate date) {
    }


    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public String getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    public double getCaloricValue() {
        return caloricValue;
    }

    public void setCaloricValue(double caloricValue) {
        this.caloricValue = caloricValue;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(double caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public String getSleepRecordData() {
        return sleepRecordData;
    }

    public void setSleepRecordData(String sleepRecordData) {
        this.sleepRecordData = sleepRecordData;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(LocalTime sleepTime) {
        this.sleepTime = sleepTime;
    }

    public LocalTime getWakeUpTime() {
        return wakeUpTime;
    }

    public void setWakeUpTime(LocalTime wakeUpTime) {
        this.wakeUpTime = wakeUpTime;
    }

    public String getData() {
        switch (entryType) {
            case "Calorie Intake":
                return foodItem + " - Caloric Value: " + caloricValue;
            case "Exercise Activity":
                return foodItem + " - Duration: " + duration + " minutes, Calories Burned: " + caloriesBurned;
            case "Sleep Record":
                return sleepRecordData;
            default:
                return "";
        }
    }
}

