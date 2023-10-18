public class Running extends ActivityEntry {

    final private INTENSITY intensity = getIntensityValue();

    public Running(String date, double distance, int heartRate, int duration) {
        super("Running", date,distance,heartRate,duration);
    }

    public ActivityEntry.INTENSITY getIntensityValue() {
        double kmph = this.getDistance()/((double) this.getDuration() /60);

        if (kmph<4)
            return INTENSITY.VERYLIGHT;
        else if (kmph<8)
            return INTENSITY.LIGHT;
        else if(kmph<12)
            return INTENSITY.MODERATE;
        else if (kmph<16)
            return INTENSITY.VIGOROUS;
        else
            return INTENSITY.VERYVIGOROUS;
    }


    public double getCaloriesBurned() {
        if (intensity == INTENSITY.VERYLIGHT)
            return 4.1 * getDuration();
        else if (intensity == INTENSITY.LIGHT)
            return 7.2 * getDuration();
        else if (intensity == INTENSITY.MODERATE)
            return 10 * getDuration();
        else if (intensity == INTENSITY.VIGOROUS)
            return 15.4 * getDuration();
        else
            return 20.8 * getDuration();
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
