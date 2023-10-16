import java.util.Comparator;

public class HeartRateComparator implements Comparator<ActivityEntry> {
    @Override
    public int compare(ActivityEntry hr1, ActivityEntry hr2) {
        return Integer.compare(hr2.getHeartRate(), hr1.getHeartRate());
    }
}
