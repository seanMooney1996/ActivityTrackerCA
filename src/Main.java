import java.io.File;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    public static void main(String[] args)throws IOException {
        ArrayList<ActivityEntry> records = new ArrayList<>();
        String fileName = "sampleCSV.csv";
        loadCSV(records,fileName);

        Scanner kb = new Scanner(System.in);
        boolean exit = false;
        int choice = 0;
        int activityChoice = 0;

        do{
            displayMenu();
            // check if input is not a number
            while(!kb.hasNextInt()) {
                System.out.println("Invalid input, enter a corresponding number listed from the menu (0-5).");
                kb.next(); // Consume invalid input
            }
            choice = kb.nextInt();

            switch(choice) {
                case 1:
                    SessionsInterface(records);
                    break;
                case 2:
                    displaySelectActivity();
                    activityChoice = kb.nextInt();

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

        if (activityString.equals("Running")){
           e = new Running(date,distance,heartRate,duration);
        }
        else if (activityString.equals("Swimming")) {
          e = new Swimming(date,distance,heartRate,duration);
        }
        else if (activityString.equalsIgnoreCase("Cycling")) {
            e = new Cycling(date,distance,heartRate,duration);
        }
        else {
            e = new ActivityEntry();
            // default if encountering an error in reading in activity type
        }
        return e;
    }

    // ----------- Add a new activity
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
        while(!kb.hasNextInt()) {
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
        }while(flagDateInput);

        System.out.println("Distance(km): ");
        // check if input is NOT double OR int
        while(!kb.hasNextDouble() || !kb.hasNextInt()) {
            System.out.println("Invalid entry, number required.");
            kb.next(); // Consume invalid input
        }
        distance = kb.nextDouble();

        System.out.println("Heart Rate: ");
        // check if input is not a number
        while(!kb.hasNextInt()) {
            System.out.println("Invalid entry, number required.");
            kb.next(); // Consume invalid input
        }
        heartRate = kb.nextInt();

        System.out.println("Duration: ");
        // check if input is not a number
        while(!kb.hasNextInt()) {
            System.out.println("Invalid entry, number required.");
            kb.next(); // Consume invalid input
        }
        duration = kb.nextInt();

        // add new entry
        e.add(new ActivityEntry(date,distance,heartRate,duration));
        displaySessions(e);
    }

    // ----------- Filters the ArrayList to user requirements
    public static ArrayList<ActivityEntry> filterActivities(ArrayList<ActivityEntry> e, String requestedType) {
        ArrayList<ActivityEntry> filterByActivities = new ArrayList<>();

        for(ActivityEntry entries: e) {
            if(requestedType == entries.getActivityType()) {
                filterByActivities.add(entries);
            }
        }
        return filterByActivities;
    }

    public static ArrayList<ActivityEntry> filterByMininumDistance(ArrayList<ActivityEntry> e, double minDistance) {
        ArrayList<ActivityEntry> filteredDistances = new ArrayList<>();
        for (ActivityEntry entries : e) {
            if (minDistance <= entries.getDistance()) {
                filteredDistances.add(entries);
            }
        }
        return filteredDistances;
    }
    public static ArrayList<ActivityEntry> filterByMininumDuration(ArrayList<ActivityEntry> e, int duration) {
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
            if (entries.getIntensity().toString().equalsIgnoreCase(intensityString)) {
                filtered.add(entries);
            }
        }
        return filtered;
    }

    // ----------- Displays as a table -----------
    public static void displaySessions(ArrayList<ActivityEntry> entries) {
        System.out.println("+================================\t\t Sessions\t\t=======================================+");
        System.out.printf("|\t%-15s %-12s %-10s %-10s %-20s %-15s\n", "Activity Type", "Date", "Duration", "Distance", "Avg Heart Rate", "Calories Burned\t   |");

        for(ActivityEntry e : entries) {
            System.out.printf("|\t%-15s %-12s %-10s %-10s %-25s %-14.2f|\n", e.getActivityType(), e.getDate(), e.getDuration(), e.getDistance(), e.getHeartRate(), e.getCaloriesBurned());
        }
        System.out.println("+==============================================================================================+");
    }


    // ----------- Display menu -----------
    public static void displayMenu() {
        System.out.println("+ ------ Activity Tracker ----- +");
        System.out.println("|                               |");
        System.out.println("|    1. All Sessions            |");
        System.out.println("|    2. Select Activity         |");
        System.out.println("|    3. Individual Reports      |");
        System.out.println("|    4. Add New Activity        |");
        System.out.println("|                               |");
        System.out.println("|    0. Exit                    |");
        System.out.println("|                               |");
        System.out.println("+ ----------------------------- +");
    }

    // ----------- Select activity -----------
    public static void displaySelectActivity() {
        System.out.println("+ ------ Select Activity ----- +");
        System.out.println("|                               |");
        System.out.println("|         1. Running            |");
        System.out.println("|         2. Swimming           |");
        System.out.println("|         3. Cycling            |");
        System.out.println("|                               |");
        System.out.println("+ ----------------------------- +");
    }


    // ----------- Sessions -----------
    public static void SessionsInterface(ArrayList<ActivityEntry> e) {
        Scanner kb = new Scanner(System.in);
        boolean exit = false;
        do{
            displaySessionsMenu();
            // check if input is not a number
            while(!kb.hasNextInt()) {
                System.out.println("Invalid input, enter a corresponding number listed from the menu (0-4).");
                kb.next(); // Consume invalid input
            }
            int choice = kb.nextInt();
            kb.nextLine();

            switch(choice) {
                case 1:
                    displaySessions(e);
                    break;
                case 2:
                    // TODO
                    break;
                case 3:
                    // TODO
                    break;
                case 4:
                    // TODO
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Please select from 1-5 from the menu.");
                    break;
            }
        }while(!exit);

    }

    public static void displaySessionsMenu() {
        System.out.println("+ --------  Sessions  --------- +");
        System.out.println("|                               |");
        System.out.println("|    1. Display all             |");
        System.out.println("|    2.                         |");
        System.out.println("|    3.                         |");
        System.out.println("|    4.                         |");
        System.out.println("|    5.                         |");
        System.out.println("|    0. Return                  |");
        System.out.println("|                               |");
        System.out.println("+ ----------------------------- +");
    }


    // ----------- Running -----------
    public static void RunningInterface(ArrayList<ActivityEntry> e) {
        Scanner kb = new Scanner(System.in);
        boolean exit = false;
        boolean page = false; // false = 1, true = 2
        do {
            // Create a nw ArrayList of all entries that are of type 'Running'
            ArrayList<ActivityEntry> runningSessions = filterActivities(e, "Running");

            System.out.println(displayRunningMenu(page)); // Print menu onto screen

            // check if input is not a number
            while (!kb.hasNextInt()) {
                System.out.println("Enter a number listed from the menu.");
                kb.next(); // Consume invalid input
            }
            int choice = kb.nextInt();

            // if your on the first page, you can select these options
            if(!page) {
                switch(choice) {
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
                        displayByDistance(runningSessions);
                        break;
                    case 5:
                        Collections.sort(runningSessions, new HeartRateComparator());
                        displaySessions(runningSessions);
                        break;
                    case -1:
                        page = true; // move to the second page
                        break;
                    case 0:
                        exit = true; // exit this menu;
                        break;
                    default:
                        System.out.println("Select from one of the menu options -1 -> 5");
                        break;
                }
            }

            // if your on the first page, you can select these options
            if(page) {
                switch (choice) {
                    case 1:
                        // TODO Method to take in a DISTANCE X and display all entries above that DISTANCE - LUKE
                        break;
                    case 2:
                        // TODO Method to take in a DURATION X and display all entries above that DURATION - LUKE
                        break;
                    case 3:
                        displayByCaloriesBurned(runningSessions);
                        break;
                    case 4:
                        // TODO Method to show the energy expended for each activity in a subset
                        break;
                    case 0:
                        page = false; // return to previous page
                        break;
                    default:
                        System.out.println("Select from one of the menu options 0 -> 3");
                        break;
                }
            }
        }while(!exit);
    }
    public static String displayRunningMenu(boolean whichPage) {

        StringBuilder selectedPage = new StringBuilder();

        if (!whichPage) // page 1
        {
            selectedPage.append("+ ---------  Running  --------- +\n");
            selectedPage.append("|                               |\n");
            selectedPage.append("|    1. Display all             |\n");
            selectedPage.append("|    2. By Date                 |\n");
            selectedPage.append("|    3. By Duration             |\n");
            selectedPage.append("|    4. By Distance             |\n");
            selectedPage.append("|    5. By Heart Rate           |\n");
            selectedPage.append("|                               |\n");
            selectedPage.append("|   -1. Next                    |\n");
            selectedPage.append("|    0. Return                  |\n");
            selectedPage.append("|                               |\n");
            selectedPage.append("|          page 1 : 2           |\n");
            selectedPage.append("+ ----------------------------- +");
        }
        if (whichPage) // page 2
        {
            selectedPage.append("+ ---------  Running  --------- +\n");
            selectedPage.append("|                               |\n");
            selectedPage.append("|    1. Above distance X        |\n");
            selectedPage.append("|    2. Above duration X        |\n");
            selectedPage.append("|    3. Calories Burned         |\n");
            selectedPage.append("|    4. Energy Expended Type    |\n");
            selectedPage.append("|    0. Return                  |\n");
            selectedPage.append("|                               |\n");
            selectedPage.append("|          page 2 : 2           |\n");
            selectedPage.append("+ ----------------------------- +");
        }
        return selectedPage.toString();
    }


    // ----------- Swimming -----------
    public static void SwimmingInterface(ArrayList<ActivityEntry> e) {
        Scanner kb = new Scanner(System.in);
        boolean exit = false;
        boolean page = false; // false = 1, true = 2
        do {
            // Create a new ArrayList of all entries that are of type 'Swimming'
            ArrayList<ActivityEntry> swimmingSessions = filterActivities(e, "Swimming");

            System.out.println(displaySwimmingMenu(page)); // Print menu onto screen

            // check if input is not a number
            while (!kb.hasNextInt()) {
                System.out.println("Enter a number listed from the menu.");
                kb.next(); // Consume invalid input
            }
            int choice = kb.nextInt();

            // if your on the first page, you can select these options
            if(!page) {
                switch(choice) {
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
                        displayByDistance(swimmingSessions);
                        break;
                    case 5:
                        Collections.sort(swimmingSessions, new HeartRateComparator());
                        displaySessions(swimmingSessions);
                        break;
                    case -1:
                        page = true; // move to the second page
                        break;
                    case 0:
                        exit = true; // exit this menu;
                        break;
                    default:
                        System.out.println("Select from one of the menu options -1 -> 5");
                        break;
                }
            }

            // if your on the first page, you can select these options
            if(page) {
                switch (choice) {
                    case 1:
                        // TODO Method to take in a DISTANCE X and display all entries above that DISTANCE - LUKE
                        break;
                    case 2:
                        // TODO Method to take in a DURATION X and display all entries above that DURATION - LUKE
                        break;
                    case 3:
                        displayByCaloriesBurned(swimmingSessions);
                        break;
                    case 4:
                        // TODO Method to show the energy expended for each activity in a subset
                        break;
                    case 0:
                        page = false; // return to previous page
                        break;
                    default:
                        System.out.println("Select from one of the menu options 0 -> 3");
                        break;
                }
            }
        }while(!exit);
    }
    public static String displaySwimmingMenu(boolean whichPage) {
        StringBuilder selectedPage = new StringBuilder();

        if (!whichPage) // page 1
        {
            selectedPage.append("+ ---------  Swimming  -------- +\n");
            selectedPage.append("|                               |\n");
            selectedPage.append("|    1. Display all             |\n");
            selectedPage.append("|    2. By Date                 |\n");
            selectedPage.append("|    3. By Duration             |\n");
            selectedPage.append("|    4. By Distance             |\n");
            selectedPage.append("|    5. By Heart Rate           |\n");
            selectedPage.append("|                               |\n");
            selectedPage.append("|   -1. Next                    |\n");
            selectedPage.append("|    0. Return                  |\n");
            selectedPage.append("|                               |\n");
            selectedPage.append("|          page 1 : 2           |\n");
            selectedPage.append("+ ----------------------------- +");
        }
        if (whichPage) // page 2
        {
            selectedPage.append("+ ---------  Swimming  -------- +\n");
            selectedPage.append("|                               |\n");
            selectedPage.append("|    1. Above distance X        |\n");
            selectedPage.append("|    2. Above duration X        |\n");
            selectedPage.append("|    3. Calories Burned         |\n");
            selectedPage.append("|    4. Energy Expended Type    |\n");
            selectedPage.append("|    0. Return                  |\n");
            selectedPage.append("|                               |\n");
            selectedPage.append("|          page 2 : 2           |\n");
            selectedPage.append("+ ----------------------------- +");
        }
        return selectedPage.toString();
    }


    // ----------- Cycling -----------
    public static void CyclingInterface(ArrayList<ActivityEntry> e) {
        Scanner kb = new Scanner(System.in);
        boolean exit = false;
        boolean page = false; // false = 1, true = 2
        do {
            // Create a new ArrayList of all entries that are of type 'Swimming'
            ArrayList<ActivityEntry> cyclingSessions = filterActivities(e, "Cycling");

            System.out.println(displayCyclingMenu(page)); // Print menu onto screen

            // check if input is not a number
            while (!kb.hasNextInt()) {
                System.out.println("Enter a number listed from the menu.");
                kb.next(); // Consume invalid input
            }
            int choice = kb.nextInt();

            // Deal with important inputs first
            //
            if(choice == -1) // move to the second page
                page = true;
            if(!page && choice == 0) // only works when on page 1:2 | Exits Swimming menu
                exit = true;
            if(page && choice == 0) // if your on the second page accept the controls from the second page
            {
                page = false;
                exit = false;
            }

            // if your on the first page, you can select these options
            if(!page) {
                switch(choice) {
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
                        displayByDistance(cyclingSessions);
                        break;
                    case 5:
                        Collections.sort(cyclingSessions, new HeartRateComparator());
                        displaySessions(cyclingSessions);
                        break;
                    case -1:
                        page = true; // move to the second page
                        break;
                    case 0:
                        exit = true; // exit this menu;
                        break;
                    default:
                        System.out.println("Select from one of the menu options -1 -> 5");
                        break;
                }
            }

            // if your on the first page, you can select these options
            if(page) {
                switch (choice) {
                    case 1:
                        // TODO Method to take in a DISTANCE X and display all entries above that DISTANCE - LUKE
                        break;
                    case 2:
                        // TODO Method to take in a DURATION X and display all entries above that DURATION - LUKE
                        break;
                    case 3:
                        displayByCaloriesBurned(cyclingSessions);
                        break;
                    case 4:
                       // TODO Method to show the energy expended for each activity in a subset
                        break;
                    case 0:
                        page = false; // return to previous page
                        break;
                    default:
                        System.out.println("Select from one of the menu options 0 -> 4");
                        break;
                }
            }
        } while (!exit);
    }
    public static String displayCyclingMenu(boolean whichPage) {
        StringBuilder selectedPage = new StringBuilder();

        if (!whichPage) // page 1
        {
            selectedPage.append("+ ---------  Cycling  --------- +\n");
            selectedPage.append("|                               |\n");
            selectedPage.append("|    1. Display all             |\n");
            selectedPage.append("|    2. By Date                 |\n");
            selectedPage.append("|    3. By Duration             |\n");
            selectedPage.append("|    4. By Distance             |\n");
            selectedPage.append("|    5. By Heart Rate           |\n");
            selectedPage.append("|                               |\n");
            selectedPage.append("|   -1. Next                    |\n");
            selectedPage.append("|    0. Return                  |\n");
            selectedPage.append("|                               |\n");
            selectedPage.append("|          page 1 : 2           |\n");
            selectedPage.append("+ ----------------------------- +");
        }
        if (whichPage) // page 2
        {
            selectedPage.append("+ ---------  Cycling  --------- +\n");
            selectedPage.append("|                               |\n");
            selectedPage.append("|    1. Above distance X        |\n");
            selectedPage.append("|    2. Above duration X        |\n");
            selectedPage.append("|    3. Calories Burned         |\n");
            selectedPage.append("|    4. Energy Expended Type    |\n");
            selectedPage.append("|    0. Return                  |\n");
            selectedPage.append("|                               |\n");
            selectedPage.append("|          page 2 : 2           |\n");
            selectedPage.append("+ ----------------------------- +");
        }
        return selectedPage.toString();
    }



    // uses lambda function
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
    // anonymous inner class
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
