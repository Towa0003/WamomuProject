package fh.kl.wamomu.meta;

import java.text.SimpleDateFormat;

/**
 * Created by T on 13.11.13.
 */
public class meal {

    String foodkind; //Frühstück,Mittagessen,Abendessen
    String food; //Schinken
    double date;
    double time;

    public meal(String foodkind, String food, double date, double time) {
        this.foodkind = foodkind;
        this.food = food;
        this.date = date;
        this.time = time;
    }

    public String getFoodkind() {
        return foodkind;
    }

    public void setFoodkind(String foodkind) {
        this.foodkind = foodkind;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
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
}
