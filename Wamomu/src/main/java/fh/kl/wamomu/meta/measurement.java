package fh.kl.wamomu.meta;

/**
 * Created by T on 13.11.13.
 */
public class measurement {

    double date;
    double time;
    double measurement;


    public measurement(double date, double time, double measurement) {
        this.date = date;
        this.time = time;
        this.measurement = measurement;
    }

    public double getDate() {
        return date;
    }

    public void setDate(double date) {
        this.date = date;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getMeasurement() {
        return measurement;
    }

    public void setMeasurement(double measurement) {
        this.measurement = measurement;
    }
}
