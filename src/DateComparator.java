import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class DateComparator implements Comparator<ActivityEntry>{
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    boolean ascending = true;
    public DateComparator(boolean ascending) {
        this.ascending = ascending;
    }
    @Override
    public int compare(ActivityEntry entry1, ActivityEntry entry2) {
        Date date1;
        Date date2;
        try {
            date1 = dateFormat.parse(entry1.getDate());
            date2 = dateFormat.parse(entry2.getDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (ascending) {
            return date1.compareTo(date2);  // Corrected order for ascending
        } else {
            return date2.compareTo(date1);
        }
    }
}
