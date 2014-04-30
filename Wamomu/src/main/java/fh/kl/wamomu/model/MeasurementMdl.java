package fh.kl.wamomu.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Max on 09.04.2014.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MeasurementMdl implements Serializable{

    @JsonProperty("measurementid")
    private Long measurementid;
    @JsonProperty("userid")
    private Long userid;
    @JsonProperty("mvalue")
    private String mvalue;
    @JsonProperty("date")
    private Date date;
    @JsonProperty("time")
    private Date time;

    @Override
    public String toString() {
        return "MeasurementMdl{" +
                "measurementid=" + measurementid +
                ", userid=" + userid +
                ", mvalue='" + mvalue + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public MeasurementMdl(){}

    public String getMvalue() {
        return mvalue;
    }

    public void setMvalue(String mvalue) {
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

    public Long getMeasurementid() {

        return measurementid;
    }

    public void setMeasurementid(Long measurementid) {
        this.measurementid = measurementid;
    }

}
