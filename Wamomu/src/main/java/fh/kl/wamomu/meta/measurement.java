package fh.kl.wamomu.meta;

import java.util.Date;

/**
 * Created by T on 13.11.13.
 */
public class measurement {

    double mvalue;
    Date date;
    Date time;


    public measurement(double mvalue, Date date, Date time) {
        this.mvalue = mvalue;
        this.date = date;
        this.time = time;
    }

    public double getmvalue() {
        return mvalue;
    }

    public void setmvalue(double mvalue) {
        this.mvalue = mvalue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
