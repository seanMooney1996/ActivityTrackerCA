import java.util.Comparator;
public class DurationComparator implements Comparator<ActivityEntry>
{
    boolean ascending = true;
    DurationComparator(boolean ascending){
        this.ascending = ascending;
    }

    @Override
    public int compare(ActivityEntry duration1, ActivityEntry duration2){
        if (ascending){
            return duration1.getDuration() - duration2.getDuration();
        } else {
            return duration2.getDuration() - duration1.getDuration();
        }
    }
}
