package fh.kl.wamomu.meta;

import java.util.Date;

/**
 * Created by T on 13.11.13.
 */
public class measurement {

    int measurementID;
    double mvalue;
    Date date;
    Date time;


    public measurement(int measurementID, double mvalue, Date date, Date time) {
        this.measurementID = measurementID;
        this.mvalue = mvalue;
        this.date = date;
        this.time = time;
    }


    public double getmvalue() {
        return mvalue;
    }

    public Date getDate() {
        return date;
    }

    public Date getTime() {
        return time;
    }

}
