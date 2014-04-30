package fh.kl.wamomu.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by Max on 09.04.2014.
 */

@JsonIgnoreProperties(ignoreUnknown = true)


public class MealMdl implements Serializable {

    @JsonProperty("mealid")
    private Long mealid;
    @JsonProperty("userid")
    private Long userid;
    @JsonProperty("mealkind")
    private String mealkind;
    @JsonProperty("meal")
    private String meal;
    @JsonProperty("date")
    private String date;
    @JsonProperty("time")
    private String time;

    public Long getMealid() {
        return mealid;
    }

    public void setMealid(Long mealid) {
        this.mealid = mealid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getMealkind() {
        return mealkind;
    }

    public void setMealkind(String mealkind) {
        this.mealkind = mealkind;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "MealMdl{" +
                "mealid=" + mealid +
                ", userid=" + userid +
                ", mealkind='" + mealkind + '\'' +
                ", meal='" + meal + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
