public class Running extends ActivityEntry {
 private final String activityType = "Running";
    public String getActivityType() {
        return activityType;
    }

    public Running(String date, double distance, int heartRate, int duration) {
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
