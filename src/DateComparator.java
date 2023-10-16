import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class DateComparator implements Comparator<ActivityEntry>{
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public int compare(ActivityEntry entry1, ActivityEntry entry2) {
        try {
            Date date1 = dateFormat.parse(entry1.getDate());
            Date date2 = dateFormat.parse(entry2.getDate());
            return date2.compareTo(date1);
        } catch (ParseException e) {
            e.printStackTrace(); // Handle the exception appropriately
            return 0; // or throw an exception or return a default value
        }
    }
}
