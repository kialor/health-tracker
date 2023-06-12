import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Map<String, UserManagement> users = new HashMap<>();

    public static void main(String[] args) {
        loadData();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        String currentUser = null;

        while (!exit) {
            System.out.println("---Health Tracker System---");
            System.out.println("1) Login with username");
            System.out.println("2) Create new user");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter username to login:");
                    String username = scanner.nextLine();
                    UserManagement loggedInUser = login(username);
                    if (loggedInUser != null) {
                        loggedInUser.healthDataInput(scanner);
                    }
                    break;
                case 2:
                    System.out.println("Enter a username to create new user:");
                    String newUsername = scanner.nextLine();
                    createUser(newUsername);
                    currentUser = newUsername;
                    if (currentUser != null) {
                        UserManagement userManagement = users.get(currentUser);
                        userManagement.healthDataInput(scanner);
                    }
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
        saveData();
    }

    private static UserManagement login(String username) {
        if (users.containsKey(username)) {
            System.out.println("Logged in. Hello " + username + "!");
            return users.get(username);
        } else {
            System.out.println("User not found");
            return null;
        }
    }

    private static void createUser(String username) {
        if (users.containsKey(username)) {
            System.out.println("Username already exists");
        } else {
            UserManagement newUser = new UserManagement(username);
            users.put(username, newUser);
            System.out.println("User created. Hello " + username + "!");
        }
    }

    public static void saveData() {
        try {
            FileWriter writer = new FileWriter("src/data.txt", true);

            for (UserManagement user : users.values()) {
                writer.write(user.getUserName() + "\n");

                for (HealthDataEntry entry : user.getCalorieIntake()) {
                    writer.write(entry.getEntryType() + "," + entry.getData() + "," + entry.getDate() + "\n");
                }

                for (HealthDataEntry entry : user.getExerciseActivities()) {
                    writer.write(entry.getEntryType() + "," + entry.getData() + "," + entry.getDate() + "\n");
                }

                for (HealthDataEntry entry : user.getSleepRecords()) {
                    writer.write(entry.getEntryType() + "," + entry.getData() + "," + entry.getDate() + "\n");
                }
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadData() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/data.txt"));
            String line;
            UserManagement currentUser = null;

            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",", 5);
                String dataType = parts[0];

                if (dataType.equals("User")) {
                    String username = line;
                    currentUser = new UserManagement(username);
                    users.put(username, currentUser);
                } else if (currentUser != null) {
                    switch (dataType) {
                        case "Calorie Intake":
                            String foodItem = parts[1];
                            double caloricValue = Double.parseDouble(parts[2]);
                            LocalDate date = LocalDate.parse(parts[3]);

                            currentUser.addCalorieIntake(foodItem, caloricValue, date);
                            break;
                        case "Exercise Activity":
                            String exerciseType = parts[1];
                            int duration = Integer.parseInt(parts[2]);
                            double caloriesBurned = Double.parseDouble(parts[3]);
                            LocalDate exerciseDate = LocalDate.parse(parts[4]);
                            currentUser.addExerciseActivity(exerciseType, duration, caloriesBurned, exerciseDate);
                            break;
                        case "Sleep Record":
                            LocalTime sleepTime = LocalTime.parse(parts[1]);
                            LocalTime wakeUpTime = LocalTime.parse(parts[2]);
                            LocalDate sleepDate = LocalDate.parse(parts[3]);

                            currentUser.addSleepRecord(sleepDate, sleepTime, wakeUpTime);
                            break;
                        default:
                            System.out.println("Invalid data type");
                    }
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




