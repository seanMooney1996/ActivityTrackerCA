public class Swimming extends ActivityEntry{
    private String activityType = "Swimming";

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
