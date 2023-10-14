import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
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

        do{
            displayMenu();
            // check if input is not a number
            while(!kb.hasNextInt()) {
                System.out.println("Invalid input, enter a corresponding number listed from the menu (1-5).");
                kb.next(); // Consume invalid input
            }
            choice = kb.nextInt();

            switch(choice) {

                case 0:
                    exit = true;
                    break;
                case 1:
                        addActivityEntry(records);
                    break;
                case 2:
                    displaySessions(records);
                    break;
                case 3:
                    System.out.println("this will eventually  Display all running    |");
                    break;
                case 4:
                    System.out.println("this will eventually  Display all swimming   |");
                    break;
                case 5:
                    System.out.println("this will eventually  Display all cycling    |");
                    break;
                default:
                    System.out.println("Please select from 1-5 from the menu.");
                    break;
            }

        }while(!exit);
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
                //System.out.println(entry);
            }
        }
    }
    private static ActivityEntry parseLine(String line) {
        // To set default value to invalid in-case of entry not including (swimming,running,cycling)
        ActivityEntry.ACTIVITYTYPE activitytype = ActivityEntry.ACTIVITYTYPE.INVALID;
        String date;
        int duration;
        double distance;
        int heartRate;
        StringTokenizer st = new StringTokenizer(line, ",");
        String activityString = st.nextToken().trim().toUpperCase();

        // loops through possible enums and checks if entry includes possible values
        for (ActivityEntry.ACTIVITYTYPE activity : ActivityEntry.ACTIVITYTYPE.values()){
            if(activityString.equalsIgnoreCase(activity.name())){
                activitytype = ActivityEntry.ACTIVITYTYPE.valueOf(activityString);
            }
        }

        date = st.nextToken().trim();
        duration = Integer.parseInt(st.nextToken().trim());
        distance = Double.parseDouble(st.nextToken().trim());
        heartRate = Integer.parseInt(st.nextToken().trim());
        return new ActivityEntry(activitytype,date,distance,heartRate,duration);
    }

    public static void displayMenu()
    {
        System.out.println("+ ----------------------------- +");
        System.out.println("|                               |");
        System.out.println("|    1. Add new activity        |");
        System.out.println("|    2. Display all sessions    |");
        System.out.println("|    3. Display all running     |");
        System.out.println("|    4. Display all swimming    |");
        System.out.println("|    5. Display all cycling     |");
        System.out.println("|    0. Exit                    |");
//        System.out.println("|                               |");      //space for additonal options
//        System.out.println("|                               |");
//        System.out.println("|                               |");
        System.out.println("|                               |");
        System.out.println("+ ----------------------------- +");
    }
    public static void displaySessions(ArrayList<ActivityEntry> entries)
    {
        System.out.println("========================\t\tSessions\t\t========================");
        System.out.printf("%-15s %-12s %-10s %-10s %-20s\n", "Activity Type", "Date", "Duration", "Distance", "Average Heart Rate");

        for(ActivityEntry e: entries) {
            System.out.printf("%-15s %-12s %-10s %-10s %-20s\n", e.getActivitytype(), e.getDate(), e.getDuration(), e.getDistance(), e.getHeartRate());
        }
    }
    public static void addActivityEntry(ArrayList<ActivityEntry> e)
    {
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

        // initialize activityType with 'invalid'
        ActivityEntry.ACTIVITYTYPE activityType = ActivityEntry.ACTIVITYTYPE.INVALID;
        // sets activityType to one of the 3 enums from ACTIVITYTYPE
        switch (activityTypeChoice) {
            case 1:
                activityType = ActivityEntry.ACTIVITYTYPE.RUNNING;
                break;
            case 2:
                activityType = ActivityEntry.ACTIVITYTYPE.SWIMMING;
                break;
            case 3:
                activityType = ActivityEntry.ACTIVITYTYPE.CYCLING;
                break;
            default:
                System.out.println("Please select from 1-5 from the menu.");
        }

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
        e.add(new ActivityEntry(activityType,date,distance,heartRate,duration));
        displaySessions(e);
    }
}
