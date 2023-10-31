import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.binarySearch;


public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<ActivityEntry> records = new ArrayList<>();
        String fileName = "sampleCSV.csv";

        loadCSV(records,fileName);
        Scanner kb = new Scanner(System.in);
        boolean exit = false;
        int choice;

        String[] mainMenuOptions = {
                "All Sessions",
                "Select Activity",
                "Individual Reports",
                "Add New Activity",
        };

        // instantiate a new Menu object
        Menu mainMenu = new Menu(mainMenuOptions, "Activity Tracker");

        do{
            mainMenu.display();
            // check if input is not a number

            choice = mainMenu.getChoice();

            switch(choice) {
                case 1:
                    AllActivitiesInterface(records);
                    break;
                case 2:
                    Menu.displaySelectActivity();
                    int activityChoice = kb.nextInt();

                    if (activityChoice == 1) {
                        RunningInterface(records);
                    } else if (activityChoice == 2) {
                        SwimmingInterface(records);
                    } else if (activityChoice == 3) {
                        CyclingInterface(records);
                    } else {
                        System.out.println("Invalid choice. Please select from 0-3.");
                    }
                    break;
                case 3:
                    // TODO Individual Reports
                    break;
                case 4:
                    addActivityEntry(records);
                    break;
                case 0:
                    exit = true; // exit program
                    break;
                default:
                    System.out.println("Select from one of the menu options 0 -> 4");
                    break;
            }
        }while(!exit);
    }

    // ----------- Load CSV and create ArrayList
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
        //removing any duplicate entries
        removeDuplicates(records);
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
        } else {
            e = new Cycling(date, distance, heartRate, duration);
        }
        return e;
    }

    // Iterates through the records array to check for duplicates using Overrode version of .equals
    public static void removeDuplicates(ArrayList<ActivityEntry> records) {
        System.out.println("removing duplicates");
        int size = records.size();
        for (int i = 0; i < size; i++) {
            ActivityEntry entry1 = records.get(i);
            for (int j = i + 1; j < size; j++) {
                ActivityEntry entry2 = records.get(j);
                if (entry1.equals(entry2)) {
                    System.out.println("removing 1");
                    records.remove(j);
                    size--;
                    j--;
                }
            }
        }
    }



    // ----------- All Activities -----------
    public static void AllActivitiesInterface(ArrayList<ActivityEntry> e) {
        Scanner kb = new Scanner(System.in);
        boolean exit = false;
        int choice = 0;
        int duration = 0;
        double distance = 0.0;

        String[] sessionsMenuItems = {
                "Display all",
                "By Activity",
                "By Date",
                "By Duration",
                "By Distance",
                "By Heart Rate",
                "Above distance X",
                "Above duration X",
                "Calories Burned",
                "Energy Expended Type",
                "Average Distances",
                "Average Calories"};

        Menu sessionMenu = new Menu(sessionsMenuItems, "All Activities");
        do{
            sessionMenu.display();

            choice = sessionMenu.getChoice();

            switch (choice){
                case 1:
                    displaySessions(e);
                    break;
                case 2:
                    displayByActivity(e);
                    break;
                case 3:
                    Collections.sort(e, new DateComparator(Menu.selectOrder()));
                    displaySessions(e);
                    break;
                case 4:
                    Collections.sort(e, new DurationComparator(Menu.selectOrder()));
                    displaySessions(e);
                case 5:
                    displayByDistance(e, Menu.selectOrder());
                    break;
                case 6:
                    Collections.sort(e, new HeartRateComparator(Menu.selectOrder()));
                    displaySessions(e);
                    break;
                case 7:
                    System.out.print("Enter a  minimum distance: ");
                    // if the next input is not a number or less than one - enter loop
                    while(!kb.hasNextInt()) {
                        System.out.println("Invalid input, enter a number > 0");
                        kb.next(); // consume invalid input
                    }
                    distance = kb.nextDouble();
                    displaySessions(filterByMinimumDistance(e, distance));
                    break;
                case 8:
                    System.out.println("Enter a minimum duration: ");
                    // if the next input is not a number or less than one - enter loop
                    while(!kb.hasNextInt()) {
                        System.out.println("Invalid input, enter a number > 0");
                        kb.next(); // consume invalid input
                    }
                    duration = kb.nextInt();
                    displaySessions(filterByMinimumDuration(e, duration));
                    break;
                case 9:
                    displayByCaloriesBurned(e);
                    break;
                case 10:
                    displayEnergyExpended(filterByEnergyExpended(e, Menu.selectIntensityValue()));
                    break;
                case 11:
                    displayAverageDistancesPerActivity(getAverageDistances(e));
                    break;
                case 12:
                    displayAverageDistancesPerActivity(getAverageCalories(e));
                    break;
                case 0:
                    exit = true; // exit this menu
                    break;
                default:
                    System.out.println("Select from one of the menu options 0 -> 3");
                    break;
            }

        }while(!exit);

    }

    // ----------- Running -----------
    public static void RunningInterface(ArrayList<ActivityEntry> e) {
        Scanner kb = new Scanner(System.in);
        boolean exit = false;
        int choice = 0;
        int duration = 0;
        double distance = 0.0;

        // filter original ArrayList to all running entries


        String[] runningMenuItems = {
                "Display all",
                "By Date",
                "By Duration",
                "By Distance",
                "By Heart Rate",
                "Above distance X",
                "Above duration X",
                "Calories Burned"};

        Menu sessionMenu = new Menu(runningMenuItems, "Running");
        do{
            ArrayList<ActivityEntry> runningEntries = filterActivities(e, "Running");
            sessionMenu.display();

            choice = sessionMenu.getChoice();

            switch (choice){
                case 1:
                    displaySessions(runningEntries);
                    break;
                case 2:
                    Collections.sort(runningEntries, new DateComparator(Menu.selectOrder()));
                    displaySessions(runningEntries);
                    break;
                case 3:
                    Collections.sort(runningEntries, new DurationComparator(Menu.selectOrder()));
                    displaySessions(runningEntries);
                    break;
                case 4:
                    displayByDistance(runningEntries, Menu.selectOrder());
                    break;
                case 5:
                    Collections.sort(runningEntries, new HeartRateComparator(Menu.selectOrder()));
                    displaySessions(runningEntries);
                    break;
                case 6:
                    System.out.print("Enter a  minimum distance: ");
                    // if the next input is not a number or less than one - enter loop
                    while(!kb.hasNextInt()) {
                        System.out.println("Invalid input, enter a number > 0");
                        kb.next(); // consume invalid input
                    }
                    distance = kb.nextInt();
                    displaySessions(filterByMinimumDistance(runningEntries, distance));
                    break;
                case 7:
                    System.out.println("Enter a minimum duration: ");
                    // if the next input is not a number or less than one - enter loop
                    while(!kb.hasNextInt()) {
                        System.out.println("Invalid input, enter a number > 0");
                        kb.next(); // consume invalid input
                    }
                    duration = kb.nextInt();
                    displaySessions(filterByMinimumDuration(runningEntries, duration));
                    break;
                case 8:
                    displayByCaloriesBurned(runningEntries);
                    break;
                case 0:
                    exit = true; // exit this menu
                    break;
                default:
                    System.out.println("Select from one of the menu options 0 -> " + runningMenuItems.length);
                    break;
            }

        }while(!exit);
    }

    // ----------- Swimming -----------
    public static void SwimmingInterface(ArrayList<ActivityEntry> e) {
        Scanner kb = new Scanner(System.in);
        boolean exit = false;
        int choice = 0;
        int duration = 0;
        double distance = 0.0;

        String[] swimmingMenuItems = {
                "Display all",
                "By Date",
                "By Duration",
                "By Distance",
                "By Heart Rate",
                "Above distance X",
                "Above duration X",
                "Calories Burned"};

        Menu sessionMenu = new Menu(swimmingMenuItems, "Swimming");
        do{
            ArrayList<ActivityEntry> swimmingEntries = filterActivities(e, "Swimming");
            sessionMenu.display();

            choice = sessionMenu.getChoice();

            switch (choice){
                case 1:
                    displaySessions(swimmingEntries);
                    break;
                case 2:
                    Collections.sort(swimmingEntries, new DateComparator(Menu.selectOrder()));
                    displaySessions(swimmingEntries);
                    break;
                case 3:
                    Collections.sort(swimmingEntries, new DurationComparator(Menu.selectOrder()));
                    displaySessions(swimmingEntries);
                case 4:
                    displayByDistance(swimmingEntries, Menu.selectOrder());
                    break;
                case 5:
                    Collections.sort(swimmingEntries, new HeartRateComparator(Menu.selectOrder()));
                    displaySessions(swimmingEntries);
                    break;
                case 6:
                    System.out.print("Enter a  minimum distance: ");
                    // if the next input is not a number or less than one - enter loop
                    while(!kb.hasNextInt()) {
                        System.out.println("Invalid input, enter a number > 0");
                        kb.next(); // consume invalid input
                    }
                    distance = kb.nextInt();
                    displaySessions(filterByMinimumDistance(swimmingEntries, distance));
                    break;
                case 7:
                    System.out.println("Enter a minimum duration: ");
                    // if the next input is not a number or less than one - enter loop
                    while(!kb.hasNextInt()) {
                        System.out.println("Invalid input, enter a number > 0");
                        kb.next(); // consume invalid input
                    }
                    duration = kb.nextInt();
                    displaySessions(filterByMinimumDuration(swimmingEntries, duration));
                    break;
                case 8:
                    displayByCaloriesBurned(swimmingEntries);
                    break;
                case 0:
                    exit = true; // exit this menu
                    break;
                default:
                    System.out.println("Select from one of the menu options 0 -> " + swimmingMenuItems.length);
                    break;
            }

        }while(!exit);
    }

    // ----------- Cycling -----------
    public static void CyclingInterface(ArrayList<ActivityEntry> e) {
        Scanner kb = new Scanner(System.in);
        boolean exit = false;
        int choice = 0;
        int duration = 0;
        double distance = 0.0;

        String[] cyclingMenuItems = {
                "Display all",
                "By Date",
                "By Duration",
                "By Distance",
                "By Heart Rate",
                "Above distance X",
                "Above duration X",
                "Calories Burned"};

        Menu sessionMenu = new Menu(cyclingMenuItems, "Cycling");
        do{
            ArrayList<ActivityEntry> cyclingEntries = filterActivities(e, "Cycling");
            sessionMenu.display();

            choice = sessionMenu.getChoice();

            switch (choice){
                case 1:
                    displaySessions(cyclingEntries);
                    break;
                case 2:
                    Collections.sort(cyclingEntries, new DateComparator(Menu.selectOrder()));
                    displaySessions(cyclingEntries);
                    break;
                case 3:
                    Collections.sort(cyclingEntries, new DurationComparator(Menu.selectOrder()));
                    displaySessions(cyclingEntries);
                case 4:
                    displayByDistance(cyclingEntries, Menu.selectOrder());
                    break;
                case 5:
                    Collections.sort(cyclingEntries, new HeartRateComparator(Menu.selectOrder()));
                    displaySessions(cyclingEntries);
                    break;
                case 6:
                    System.out.print("Enter a  minimum distance: ");
                    // if the next input is not a number or less than one - enter loop
                    while(!kb.hasNextInt()) {
                        System.out.println("Invalid input, enter a number > 0");
                        kb.next(); // consume invalid input
                    }
                    distance = kb.nextInt();
                    displaySessions(filterByMinimumDistance(cyclingEntries, distance));
                    break;
                case 7:
                    System.out.println("Enter a minimum duration: ");
                    // if the next input is not a number or less than one - enter loop
                    while(!kb.hasNextInt()) {
                        System.out.println("Invalid input, enter a number > 0");
                        kb.next(); // consume invalid input
                    }
                    duration = kb.nextInt();
                    displaySessions(filterByMinimumDuration(cyclingEntries, duration));
                    break;
                case 8:
                    displayByCaloriesBurned(cyclingEntries);
                    break;
                case 0:
                    exit = true; // exit this menu
                    break;
                default:
                    System.out.println("Select from one of the menu options 0 -> " + cyclingMenuItems.length);
                    break;
            }

        }while(!exit);
    }


    // ----------- Add a new activity -----------
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
        if (activityTypeChoice == 1)
            e.add(new Running(date, distance, heartRate, duration));
        if (activityTypeChoice == 2)
            e.add(new Swimming(date, distance, heartRate, duration));
        if (activityTypeChoice == 3)
            e.add(new Cycling(date, distance, heartRate, duration));
        displaySessions(e);
    }


    // ----------- Filters the ArrayList to user requirements
    public static ArrayList<ActivityEntry> filterActivities(ArrayList<ActivityEntry> e, String requestedType) {
        ArrayList<ActivityEntry> filterByActivities = new ArrayList<>();

        for (ActivityEntry entries : e) {
            if (requestedType == entries.getActivityType()) {
                filterByActivities.add(entries);
            }
        }
        return filterByActivities;
    }
    public static ArrayList<ActivityEntry> filterByMinimumDistance(ArrayList<ActivityEntry> e, double minDistance) {
        ArrayList<ActivityEntry> filteredDistances = new ArrayList<>();
        for (ActivityEntry entries : e) {
            if (minDistance <= entries.getDistance()) {
                filteredDistances.add(entries);
            }
        }
        return filteredDistances;
    }
    public static ArrayList<ActivityEntry> filterByMinimumDuration(ArrayList<ActivityEntry> e, int duration) {
        ArrayList<ActivityEntry> filtered = new ArrayList<>();
        for (ActivityEntry entries : e) {
            if (duration <= entries.getDuration()) {
                filtered.add(entries);
            }
        }
        return filtered;
    }
    public static ArrayList<ActivityEntry> filterByEnergyExpended(ArrayList<ActivityEntry> e, String intensityString) {
        ArrayList<ActivityEntry> filtered = new ArrayList<>();
        for (ActivityEntry entries : e) {
            if (entries.getIntensityValue().name().equals(intensityString)) {
                System.out.println("11"+entries.getIntensityValue());
                filtered.add(entries);
            }
        }
        return filtered;
    }

    // ----------- Displays as a table -----------
    public static void displaySessions(ArrayList<ActivityEntry> entries) {
        System.out.println("+================================\t\t Sessions\t\t=======================================+");
        System.out.printf("|\t%-15s %-12s %-10s %-10s %-20s %-15s\n", "Activity Type", "Date", "Duration", "Distance", "Avg Heart Rate", "Calories Burned\t   |");

        for (ActivityEntry e : entries) {
            System.out.printf("|\t%-15s %-12s %-10s %-10s %-25s %-14.2f|\n", e.getActivityType(), e.getDate(), e.getDuration(), e.getDistance(), e.getHeartRate(), e.getCaloriesBurned());
        }
        System.out.println("+==============================================================================================+");
    }

    // ----------- Uses lambda function -----------
    public static void displayByDistance(ArrayList<ActivityEntry> records, boolean ascending) {
        if (ascending) {
            Collections.sort(records, (e1, e2) ->
            {
                if (e2.getDistance() > e1.getDistance())
                    return -1;
                else if (e1.getDistance() > e2.getDistance())
                    return 1;
                else
                    return 0;
            });
        } else {
            Collections.sort(records, (e1, e2) ->
            {
                if (e2.getDistance() > e1.getDistance())
                    return 1;
                else if (e1.getDistance() > e2.getDistance())
                    return -1;
                else
                    return 0;
            });
        }
        displaySessions(records);
    }

    // ----------- Anonymous inner class -----------
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
    public static void displayByActivity(ArrayList<ActivityEntry> records) {
        Collections.sort(records, new Comparator<ActivityEntry>() {
            @Override
            public int compare(ActivityEntry e1, ActivityEntry e2) {
                return e1.getActivityType().compareTo(e2.getActivityType());
            }
        });
        displaySessions(records);
    }

    public static void displayEnergyExpended(ArrayList<ActivityEntry> entries) {
        System.out.println("+================================\t\t Sessions\t\t=======================================+");
        System.out.printf("|\t%-15s %-12s %-10s %-10s %-20s %-15s\n", "Activity Type", "Date", "Duration", "Distance", "Avg Heart Rate", "Intensity Value\t   |");

        for (ActivityEntry e : entries) {
            System.out.printf("|\t%-15s %-12s %-10s %-10s %-25s %-14s|\n", e.getActivityType(), e.getDate(), e.getDuration(), e.getDistance(), e.getHeartRate(), e.getIntensityValue().name());
        }
        System.out.println("+==============================================================================================+");
    }


    //binary search with string return if object from parameter has a match
    public static String searchBinary(ArrayList<ActivityEntry> list, ActivityEntry toFind) {
        Collections.sort(list);
        int index = binarySearch(list, toFind);
        if (index != -1)
            return "Found "+list.get(index).toString()+" at index"+index+".";
        else
            return "No activity was found with these parameters.";
    }

    //Method to take input from user and create object for binary search
    public static ActivityEntry getUserParameters() {
        boolean inputValid = true;
        ActivityEntry e = new Running();
        do {
            inputValid = true;
            Scanner input = new Scanner(System.in);
            System.out.println("Enter Activity Type:");
            String type = input.next();
            System.out.println("Enter Date: dd/mm/yy");
            String date = input.next();

            if (type.equalsIgnoreCase("Running")) {
                e = new Running(date);
            } else if (type.equalsIgnoreCase("Swimming")) {
                e =  new Swimming(date);
            } else if (type.equalsIgnoreCase("Cycling")) {
                e = new Cycling(date);
            } else {
                System.out.println("Invalid activity type");
                inputValid = false;
            }
        } while(!inputValid);
        return e;
    }

    public static double[] getAverageDistances(ArrayList<ActivityEntry> records) {
        double runningAvg, cyclingAvg, swimmingAvg;
        double runningTotal = 0;
        double cyclingTotal = 0;
        double swimmingTotal = 0;
        int runningCount = 0;
        int cyclingCount = 0;
        int swimmingCount = 0;
        for (ActivityEntry e : records) {
            if (e.getActivityType().equals("Running")) {
                runningCount++;
                runningTotal += e.getDistance();
            } else if (e.getActivityType().equals("Swimming")) {
                swimmingCount++;
                swimmingTotal += e.getDistance();
            } else {
                cyclingCount++;
                cyclingTotal += e.getDistance();
            }
        }
        runningAvg = runningTotal / runningCount;
        swimmingAvg = swimmingTotal / swimmingCount;
        cyclingAvg = cyclingTotal / cyclingCount;
        double[] avgs = {runningAvg, swimmingAvg, cyclingAvg};
        return avgs;
    }
    public static double[] getAverageCalories(ArrayList<ActivityEntry> records) {
        double runningAvg, cyclingAvg, swimmingAvg;
        double runningTotal = 0;
        double cyclingTotal = 0;
        double swimmingTotal = 0;
        int runningCount = 0;
        int cyclingCount = 0;
        int swimmingCount = 0;
        for (ActivityEntry e : records) {
            if (e.getActivityType().equals("Running")) {
                runningCount++;
                runningTotal += e.getCaloriesBurned();
            } else if (e.getActivityType().equals("Swimming")) {
                swimmingCount++;
                swimmingTotal += e.getCaloriesBurned();
            } else {
                cyclingCount++;
                cyclingTotal += e.getCaloriesBurned();
            }
        }
        runningAvg = runningTotal / runningCount;
        swimmingAvg = swimmingTotal / swimmingCount;
        cyclingAvg = cyclingTotal / cyclingCount;
        double[] avgs = {runningAvg, swimmingAvg, cyclingAvg};
        return avgs;
    }

    public static void displayAverageDistancesPerActivity(double[] avgs) {
        System.out.println("+ ------------------------------- +");
        System.out.println("|           AVERAGES              |");
        System.out.println("|                                 |");
        System.out.printf("|       Running  : %-13.2f  |\n", avgs[0]);
        System.out.printf("|       Swimming : %-13.2f  |\n", avgs[1]);
        System.out.printf("|       Cycling  : %-13.2f  |\n", avgs[2]);
        System.out.println("+ ------------------------------- +");

    }

}
