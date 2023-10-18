public class Cycling extends ActivityEntry{


    final private INTENSITY intensity = getIntensityValue();


    public ActivityEntry.INTENSITY getIntensityValue() {
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
    public double getCaloriesBurned() {
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
        super("Cycling",date,distance,heartRate,duration);

    }

    @Override
    public String toString() {
        return "ActivityEntry{" +
                "ActivityType=" + this.getActivityType()+
                ", intensity=" + intensity +
                ", date='" + this.getDate() + '\'' +
                ", distance=" + this.getDistance() +
                ", heartRate=" + this.getHeartRate() +
                ", duration=" + this.getDuration() +
                '}';
    }
}
