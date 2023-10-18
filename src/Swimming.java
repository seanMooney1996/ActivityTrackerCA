public class Swimming extends ActivityEntry{

    final private INTENSITY intensity = getIntensityValue();


    public Swimming(String date, double distance, int heartRate, int duration) {
        super("Swimming",date,distance,heartRate,duration);
    }


    public double getCaloriesBurned(){
        if (intensity == INTENSITY.VERYLIGHT)
            return 5 * getDuration();
        else if (intensity == INTENSITY.LIGHT)
            return 6.3 * getDuration();
        else if (intensity == INTENSITY.MODERATE)
            return 7.6 * getDuration();
        else if (intensity == INTENSITY.VIGOROUS)
            return 8.9 * getDuration();
        else
            return 10.2 * getDuration();
    }

    public ActivityEntry.INTENSITY getIntensityValue() {
        double kmph = this.getDistance()/((double) this.getDuration() /60);

        if (kmph<1.25)
            return INTENSITY.VERYLIGHT;
        else if (kmph<2)
            return INTENSITY.LIGHT;
        else if(kmph<2.75)
            return INTENSITY.MODERATE;
        else if (kmph<3.5)
            return INTENSITY.VIGOROUS;
        else
            return INTENSITY.VERYVIGOROUS;
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
