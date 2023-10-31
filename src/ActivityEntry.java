public abstract class ActivityEntry implements Comparable<ActivityEntry> {
    public enum INTENSITY {VERYLIGHT, LIGHT, MODERATE, VIGOROUS, VERYVIGOROUS,DEFAULT};

    private String date;
    private double distance;
    private int heartRate;
    private int duration;




    public ActivityEntry( String date, double distance, int heartRate, int duration) {
        this.date = date;
        this.distance = distance;
        this.heartRate = heartRate;
        this.duration = duration;
    }

    public abstract String getActivityType();

    public ActivityEntry() {
        this.date = "default";
        this.distance = 0.0;
        this.heartRate = 0;
        this.duration = 0;
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
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    public abstract double getCaloriesBurned();

    public abstract INTENSITY getIntensityValue();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ActivityEntry other = (ActivityEntry)o;
        if (this.getDate().equalsIgnoreCase(other.getDate()) && getHeartRate() == other.getHeartRate()){
            return true;
        }
        return false;
    }

    // natural ordering checks --->  distance/heartRate/date/activityType

    @Override
    public int compareTo(ActivityEntry e){
        DateComparator d = new DateComparator(true);
        if (this.distance > e.getDistance()) {
            return 1;
        } else if (this.distance < e.getDistance()) {
            return -1;
        } else {
            if(this.heartRate >  e.getHeartRate()){
                return 1;
            } else if (this.heartRate <  e.getHeartRate()){
                return -1;
            } else {
                int dateComparison = d.compare(this,e);

            if (dateComparison != 0 ) {
                return dateComparison;
            } else {
                return this.getActivityType().compareTo(e.getActivityType());
            }
            }
        }
    }

    @Override
    public String toString() {
        return "ActivityEntry{" +
                ", date='" + date + '\'' +
                ", distance=" + distance +
                ", heartRate=" + heartRate +
                ", duration=" + duration +
                '}';
    }
}
