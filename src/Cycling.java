public class Cycling extends ActivityEntry{

    private String activityType = "Cycling";
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
