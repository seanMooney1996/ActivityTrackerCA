public class ActivityEntry {
    public static enum INTENSITY {VERYLIGHT, LIGHT, MODERATE, VIGOROUS, VERYVIGOROUS,DEFAULT};

    final private String activityType = "Default";
    private String date;
    private double distance;
    private int heartRate;
    private int duration;


    public ActivityEntry(String date, double distance, int heartRate, int duration) {
        this.date = date;
        this.distance = distance;
        this.heartRate = heartRate;
        this.duration = duration;
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
