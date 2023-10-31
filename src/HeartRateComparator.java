import java.util.Comparator;

public class HeartRateComparator implements Comparator<ActivityEntry> {

    boolean ascending = true;
    HeartRateComparator(boolean ascending){

        this.ascending = ascending;
    }
    @Override
    public int compare(ActivityEntry hr1, ActivityEntry hr2) {
        if(ascending){
            return Integer.compare(hr1.getHeartRate(), hr2.getHeartRate());
        } else {
            return Integer.compare(hr2.getHeartRate(), hr1.getHeartRate());
        }

    }
}
