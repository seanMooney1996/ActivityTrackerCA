import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

    public static void main(String[] args) throws IOException {
        ArrayList<ActivityEntry> records = new ArrayList<>();
        String fileName = "sampleCSV.csv";
        loadCSV(records, fileName);
        displayByDistance(records);
        displayByCaloriesBurned(records);
        displayAverageDistancesPerActivity(getAverageDistances(records));
        Scanner kb = new Scanner(System.in);
        boolean exit = false;
        int choice = 0;

        do {
            displayMenu();
            // check if input is not a number
            while (!kb.hasNextInt()) {
                System.out.println("Invalid input, enter a corresponding number listed from the menu (0-5).");
                kb.next(); // Consume invalid input
            }
            choice = kb.nextInt();

            switch (choice) {

                case 0:
                    exit = true;
                    break;
                case 1:
                    SessionsInterface(records);
                    break;
                case 2:
                    addActivityEntry(records);
                case 3:
                    break;
                case 4:
                    System.out.println("this will eventually do something            |");
                    break;
                case 5:
                    System.out.println("this will eventually do something            |");
                    break;
                default:
                    System.out.println("Please select from 1-5 from the menu.");
                    break;
            }

        } while (!exit);
    }


    public static void loadCSV(ArrayList<ActivityEntry> records, String fileName) throws IOException {
        File f = new File(fileName);
        Scanner input = new Scanner(f);
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
            if (line != "") {
                ActivityEntry entry = parseLine(line);
                records.add(entry);
            }
        }
    }


    private static ActivityEntry parseLine(String line) {
        // To set default value to invalid in-case of entry not including (swimming,running,cycling)
        String date;
        int duration;
        double distance;
        int heartRate;
        StringTokenizer st = new StringTokenizer(line, ",");
        String activityString = st.nextToken().trim();
        date = st.nextToken().trim();

        duration = Integer.parseInt(st.nextToken().trim());
        distance = Double.parseDouble(st.nextToken().trim());
        heartRate = Integer.parseInt(st.nextToken().trim());

        ActivityEntry e;


        if (activityString.equals("Running")) {
            e = new Running(date, distance, heartRate, duration);
        } else if (activityString.equals("Swimming")) {
            e = new Swimming(date, distance, heartRate, duration);
        } else if (activityString.equalsIgnoreCase("Cycling")) {
            e = new Cycling(date, distance, heartRate, duration);
        } else {
            e = new ActivityEntry();
            // default if encountering an error in reading in activity type
        }
        return e;
    }

    public static void addActivityEntry(ArrayList<ActivityEntry> e) {
        String date = "";
        double distance = 0.0;
        int heartRate = 0;
        int duration = 0;
        Scanner kb = new Scanner(System.in);
        boolean flagDateInput = true;

        // Define pattern for dd/mm/yyyy format for date input
        final String DATE_FORMAT = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(\\d{4})$";
        Pattern pattern = Pattern.compile(DATE_FORMAT);

        System.out.println("** NEW ACTIVITY **");
        System.out.println("Activity type:\n1)Running\n2)Swimming\n3)Cycling ");

        // check if input is not a number
        while (!kb.hasNextInt()) {
            System.out.println("Invalid input, enter a corresponding number listed from the menu (1-3).");
            kb.next(); // Consume invalid input
        }
        int activityTypeChoice = kb.nextInt();
        kb.nextLine(); // consume line

        // Error handling for DATE input, ensures input follows the same dd/mm/yyyy format as csv file
        do {
            System.out.println("Date(dd/mm/yyyy): ");
            String input = kb.nextLine();
            Matcher matcher = pattern.matcher(input); // Create new Matcher object = input

            // Check if input matches DATE_FORMAT
            if (matcher.matches()) { // set date only if it matches the pattern
                date = input;
                flagDateInput = false;
            } else {    // deny input, stay in loop
                System.out.println("Entered date does not match required format(dd/mm/yyyy)");
            }
        } while (flagDateInput);

        System.out.println("Distance(km): ");
        // check if input is NOT double OR int
        while (!kb.hasNextDouble() || !kb.hasNextInt()) {
            System.out.println("Invalid entry, number required.");
            kb.next(); // Consume invalid input
        }
        distance = kb.nextDouble();

        System.out.println("Heart Rate: ");
        // check if input is not a number
        while (!kb.hasNextInt()) {
            System.out.println("Invalid entry, number required.");
            kb.next(); // Consume invalid input
        }
        heartRate = kb.nextInt();

        System.out.println("Duration: ");
        // check if input is not a number
        while (!kb.hasNextInt()) {
            System.out.println("Invalid entry, number required.");
            kb.next(); // Consume invalid input
        }
        duration = kb.nextInt();

        // add new entry
        e.add(new ActivityEntry(date, distance, heartRate, duration));
        displaySessions(e);
    }

    public static ArrayList<ActivityEntry> filterActivities(ArrayList<ActivityEntry> e, String requestedType) {
        ArrayList<ActivityEntry> filterByActivities = new ArrayList<>();


        return filterByActivities;
    }


    public static void displayMenu() {
        System.out.println("+ ------ Activity Tracker ----- +");
        System.out.println("|                               |");
        System.out.println("|    1. Sessions                |");
        System.out.println("|    2. Add new activity        |");
        System.out.println("|    3.                         |");
        System.out.println("|    4.                         |");
        System.out.println("|    5.                         |");
        System.out.println("|    0. Exit                    |");
        System.out.println("|                               |");
        System.out.println("+ ----------------------------- +");
    }

    public static void displaySessions(ArrayList<ActivityEntry> entries) {
        System.out.println("+==========================\t\t Sessions\t\t===========================+");
        System.out.printf("|\t%-15s %-12s %-10s %-10s %-15s %-15s\n", "Activity Type", "Date", "Duration", "Distance", "Avg Heart Rate", "Calories Burned\t|");

        for (ActivityEntry e : entries) {
            System.out.printf("|\t%-15s %-12s %-10s %-10s %-15s %-15.1f|\n", e.getActivityType(), e.getDate(), e.getDuration(), e.getDistance(), e.getHeartRate(), e.getCaloriesBurned());
        }
        System.out.println("+===========================================================================+");

    }

    public static void displaySessionsMenu() {
        System.out.println("+ --------  Sessions  --------- +");
        System.out.println("|                               |");
        System.out.println("|    1. Display all             |");
        System.out.println("|    2. Running                 |");
        System.out.println("|    3. Swimming                |");
        System.out.println("|    4. Cycling                 |");
        System.out.println("|    0. Return                  |");
        System.out.println("|                               |");
        System.out.println("+ ----------------------------- +");
    }

    public static void SessionsInterface(ArrayList<ActivityEntry> e) {
        Scanner kb = new Scanner(System.in);
        boolean exit = false;
        do {
            displaySessionsMenu();
            // check if input is not a number
            while (!kb.hasNextInt()) {
                System.out.println("Invalid input, enter a corresponding number listed from the menu (0-4).");
                kb.next(); // Consume invalid input
            }
            int choice = kb.nextInt();
            kb.nextLine();

            switch (choice) {
                case 1:
                    displaySessions(e);
                    break;
                case 2:
                    RunningInterface(e);
                    break;
                case 3:
                    SwimmingInterface(e);
                    break;
                case 4:
                    CyclingInterface(e);
                    break;
                case 0:
                    exit = true;
                default:
                    System.out.println("Please select from 1-5 from the menu.");
            }
        } while (!exit);

    }

    public static void displayRunningMenu() {
        System.out.println("+ ---------  Running  --------- +");
        System.out.println("|                               |");
        System.out.println("|    1. Display all             |");
        System.out.println("|    2. By Date                 |");
        System.out.println("|    3. By Duration             |");
        System.out.println("|    4. By Heart Rate           |");
        System.out.println("|    5.                         |");
        System.out.println("|    0. Return                  |");
        System.out.println("|                               |");
        System.out.println("+ ----------------------------- +");
    }

    public static void RunningInterface(ArrayList<ActivityEntry> e) {
        boolean exit = false;
        do {
            displayRunningMenu();

            ArrayList<ActivityEntry> runningSessions = filterActivities(e, "RUNNING");

            Scanner kb = new Scanner(System.in);
            // check if input is not a number
            while (!kb.hasNextInt()) {
                System.out.println("Invalid input, enter a corresponding number listed from the menu (0-5).");
                kb.next(); // Consume invalid input
            }
            int choice = kb.nextInt();

            switch (choice) {
                case 1:
                    displaySessions(runningSessions);
                    break;
                case 2:
                    Collections.sort(runningSessions, new DateComparator());
                    displaySessions(runningSessions);
                    break;
                case 3:
                    Collections.sort(runningSessions, new DurationComparator());
                    displaySessions(runningSessions);
                    break;
                case 4:
                    Collections.sort(runningSessions, new HeartRateComparator());
                    displaySessions(runningSessions);
                    break;
                case 0:
                    exit = true;
                default:
                    System.out.println("Please select from 1-5 from the menu.");
                    break;
            }
        } while (!exit);
    }

    public static void displaySwimmingMenu() {
        System.out.println("+ ---------  Swimming  -------- +");
        System.out.println("|                               |");
        System.out.println("|    1. Display all             |");
        System.out.println("|    2. By Date                 |");
        System.out.println("|    3. By Duration             |");
        System.out.println("|    4. By Heart Rate           |");
        System.out.println("|    5.                         |");
        System.out.println("|    0. Return                  |");
        System.out.println("|                               |");
        System.out.println("+ ----------------------------- +");
    }

    public static void SwimmingInterface(ArrayList<ActivityEntry> e) {
        boolean exit = false;
        do {
            displaySwimmingMenu();

            ArrayList<ActivityEntry> swimmingSessions = filterActivities(e, "SWIMMING");

            Scanner kb = new Scanner(System.in);
            // check if input is not a number
            while (!kb.hasNextInt()) {
                System.out.println("Invalid input, enter a corresponding number listed from the menu (0-5).");
                kb.next(); // Consume invalid input
            }
            int choice = kb.nextInt();

            switch (choice) {
                case 1:
                    displaySessions(swimmingSessions);
                    break;
                case 2:
                    Collections.sort(swimmingSessions, new DateComparator());
                    displaySessions(swimmingSessions);
                    break;
                case 3:
                    Collections.sort(swimmingSessions, new DurationComparator());
                    displaySessions(swimmingSessions);
                    break;
                case 4:
                    Collections.sort(swimmingSessions, new HeartRateComparator());
                    displaySessions(swimmingSessions);
                    break;
                case 0:
                    exit = true;
                default:
                    System.out.println("Please select from 1-5 from the menu.");
                    break;
            }
        } while (!exit);
    }

    public static void displayCyclingMenu() {
        System.out.println("+ ---------  Cycling  --------- +");
        System.out.println("|                               |");
        System.out.println("|    1. Display all             |");
        System.out.println("|    2. By Date                 |");
        System.out.println("|    3. By Duration             |");
        System.out.println("|    4. By Heart Rate           |");
        System.out.println("|    5.                         |");
        System.out.println("|    0. Return                  |");
        System.out.println("|                               |");
        System.out.println("+ ----------------------------- +");
    }

    public static void CyclingInterface(ArrayList<ActivityEntry> e) {
        boolean exit = false;
        do {
            displayCyclingMenu();

            ArrayList<ActivityEntry> cyclingSessions = filterActivities(e, "CYCLING");

            Scanner kb = new Scanner(System.in);
            // check if input is not a number
            while (!kb.hasNextInt()) {
                System.out.println("Invalid input, enter a corresponding number listed from the menu (0-5).");
                kb.next(); // Consume invalid input
            }
            int choice = kb.nextInt();

            switch (choice) {
                case 1:
                    displaySessions(cyclingSessions);
                    break;
                case 2:
                    Collections.sort(cyclingSessions, new DateComparator());
                    displaySessions(cyclingSessions);
                    break;
                case 3:
                    Collections.sort(cyclingSessions, new DurationComparator());
                    displaySessions(cyclingSessions);
                    break;
                case 4:
                    Collections.sort(cyclingSessions, new HeartRateComparator());
                    displaySessions(cyclingSessions);
                    break;
                case 0:
                    exit = true;
                default:
                    System.out.println("Please select from 1-5 from the menu.");
                    break;
            }
        } while (!exit);
    }

    public static void displayByDistance(ArrayList<ActivityEntry> records) {
        Collections.sort(records, (e1, e2) ->
        {
            if (e2.getDistance() > e1.getDistance())
                return -1;
            else if (e1.getDistance() > e2.getDistance())
                return 1;
            else
                return 0;
        });
        displaySessions(records);
    }

    public static void displayByCaloriesBurned(ArrayList<ActivityEntry> records) {
        Collections.sort(records, new Comparator<ActivityEntry>() {
            @Override
            public int compare(ActivityEntry e1, ActivityEntry e2) {
                if (e1.getCaloriesBurned() > e2.getCaloriesBurned())
                    return -1;
                else if (e1.getCaloriesBurned() < e2.getCaloriesBurned())
                    return +1;
                else
                    return 0;

            }
        });
        displaySessions(records);
    }

    public static double[] getAverageDistances(ArrayList<ActivityEntry> records) {
        double runningAvg, cyclingAvg, swimmingAvg;
        double runningTotal =0;
        double cyclingTotal = 0;
        double swimmingTotal = 0;
        int runningCount = 0;
        int cyclingCount = 0;
        int swimmingCount = 0;
        for (ActivityEntry e : records) {
            if (e.getActivityType().equals("Running")){
                runningCount++;
            runningTotal += e.getDistance();
        }else if (e.getActivityType().equals("Swimming")) {
                swimmingCount++;
                swimmingTotal += e.getDistance();
            } else {
                cyclingCount++;
                cyclingTotal +=  e.getDistance();
            }
        }
        runningAvg = runningTotal/runningCount;
        swimmingAvg = swimmingTotal/swimmingCount;
        cyclingAvg = cyclingTotal/cyclingCount;
        double[] avgs = {runningAvg,swimmingAvg,cyclingAvg};
        return avgs;
    }
    public static void displayAverageDistancesPerActivity(double[] avgs){
        System.out.printf("|        AVERAGES                |\n");
        System.out.printf("|    Running  : %.2f             |\n",avgs[0]);
        System.out.printf("|    Swimming : %.2f             |\n",avgs[1]);
        System.out.printf("|    Cycling  : %.2f            |\n",avgs[2]);
        System.out.printf("+ ----------------------------- +\n");
    }
}
