public class Swimming extends ActivityEntry{
    final private String activityType = "Swimming";

    public Swimming(String date, double distance, int heartRate, int duration) {
        super(date,distance,heartRate,duration);
    }

    public String getActivityType() {
        return activityType;
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
