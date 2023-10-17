public class Cycling extends ActivityEntry{

    final private String activityType = "Cycling";

    final private INTENSITY intensity = calculateIntensity();

    final private double caloriesBurned = calculateCaloriesBurned();

    public INTENSITY getIntensity() {
        return intensity;
    }
    public double getCaloriesBurned() {
        return caloriesBurned;
    }


    @Override
    public String getActivityType() {
        return activityType;
    }
    public INTENSITY calculateIntensity(){
        double kmph = this.getDistance()/((double) this.getDuration() /60);

        if (kmph<8)
            return INTENSITY.VERYLIGHT;
        else if (kmph<17)
            return INTENSITY.LIGHT;
        else if(kmph<25)
            return INTENSITY.MODERATE;
        else if (kmph<33)
            return INTENSITY.VIGOROUS;
        else
            return INTENSITY.VERYVIGOROUS;
    }

    public double calculateCaloriesBurned(){
        if (intensity == INTENSITY.VERYLIGHT)
            return 2 * getDuration();
        else if (intensity == INTENSITY.LIGHT)
            return 5 * getDuration();
        else if (intensity == INTENSITY.MODERATE)
            return 7 * getDuration();
        else if (intensity == INTENSITY.VIGOROUS)
            return 13 * getDuration();
        else
            return 15 * getDuration();
    }

    public Cycling(String date, double distance, int heartRate, int duration) {
        super(date,distance,heartRate,duration);

    }

    @Override
    public String toString() {
        return "ActivityEntry{" +
                "ActivityType=" + this.activityType+
                ", intensity=" + intensity +
                ", date='" + this.getDate() + '\'' +
                ", distance=" + this.getDistance() +
                ", heartRate=" + this.getHeartRate() +
                ", duration=" + this.getDuration() +
                '}';
    }
}
