import java.util.Comparator;
public class DurationComparator implements Comparator<ActivityEntry>
{
    @Override
    public int compare(ActivityEntry duration1, ActivityEntry duration2){
        return duration1.getDuration() - duration2.getDuration();
    }
}
