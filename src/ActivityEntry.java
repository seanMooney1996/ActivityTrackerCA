import java.util.Collections;
import java.util.Comparator;

public abstract class ActivityEntry implements Comparable<ActivityEntry> {
    public static enum INTENSITY {VERYLIGHT, LIGHT, MODERATE, VIGOROUS, VERYVIGOROUS,DEFAULT};

    private String activityType = "Default";
    private String date;
    private double distance;
    private int heartRate;
    private int duration;



    public ActivityEntry(String activityType, String date, double distance, int heartRate, int duration) {
        this.date = date;
        this.distance = distance;
        this.heartRate = heartRate;
        this.duration = duration;
        this.activityType = activityType;
    }

    public String getActivityType() {
        return activityType;
    }

    public ActivityEntry() {
        this.date = "default";
        this.distance = 0.0;
        this.heartRate = 0;
        this.duration = 0;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public abstract double getCaloriesBurned();

    public abstract INTENSITY getIntensityValue();

    @Override
    public int compareTo(ActivityEntry e){
        if (this.distance > e.getDistance())
            return 1;
        else if (this.distance < e.getDistance())
            return -1;
        else
            return 0;
    }

    @Override
    public String toString() {
        return "ActivityEntry{" +
                ", date='" + date + '\'' +
                ", distance=" + distance +
                ", heartRate=" + heartRate +
                ", duration=" + duration +
                '}';
    }
}
