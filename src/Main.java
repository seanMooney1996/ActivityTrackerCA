import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;


public class Main {
    public static void main(String[] args)throws IOException {
        ArrayList<ActivityEntry> records = new ArrayList<>();
        String fileName = "sampleCSV.csv";
        loadCSV(records,fileName);
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
                System.out.println(entry);
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

}
