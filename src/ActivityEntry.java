public class ActivityEntry {
    public static enum ACTIVITYTYPE {RUNNING, SWIMMING, CYCLING};

    public static enum INTENSITY {VERYLIGHT, LIGHT, MODERATE, VIGOROUS, VERYVIGOROUS};
    private double intensityValue;
    private String date;
    private double distance;
    private int heartRate;
    private int duration;

    public ActivityEntry(double intensityValue, String date, double distance, int heartRate, int duration) {
        this.intensityValue = intensityValue;
        this.date = date;
        this.distance = distance;
        this.heartRate = heartRate;
        this.duration = duration;
    }

    public double getIntensityValue() {
        return intensityValue;
    }

    public void setIntensityValue(double intensityValue) {
        this.intensityValue = intensityValue;
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
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "ActivityEntry{" +
                "intensityValue=" + intensityValue +
                ", date='" + date + '\'' +
                ", distance=" + distance +
                ", heartRate=" + heartRate +
                ", duration=" + duration +
                '}';
    }
}
