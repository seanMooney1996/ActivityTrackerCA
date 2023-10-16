public class Cycling extends ActivityEntry{

    final private String activityType = "Cycling";

    @Override
    public String getActivityType() {
        return activityType;
    }

    public Cycling(String date, double distance, int heartRate, int duration) {
        super(date,distance,heartRate,duration);

    }
    @Override
    public String toString() {
        return "ActivityEntry{" +
                "ActivityType=" + this.activityType+
                ", date='" + this.getDate() + '\'' +
                ", distance=" + this.getDistance() +
                ", heartRate=" + this.getHeartRate() +
                ", duration=" + this.getDuration() +
                '}';
    }
}
