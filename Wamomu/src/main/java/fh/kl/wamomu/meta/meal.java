package fh.kl.wamomu.meta;

import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by T on 13.11.13.
 */
public class meal {

    int mealID;
    String foodkind; //Frühstück,Mittagessen,Abendessen
    String food; //Schinken
    Date date;
    Date time;

    public meal(int mealID, String foodkind, String food, Date date, Date time) {
        this.mealID = mealID;
        this.foodkind = foodkind;
        this.food = food;
        this.date = date;
        this.time = time;
    }

    public int getMealID() {
        return mealID;
    }

    public void setMealID(int mealID) {this.mealID = mealID;}

    public String getFoodkind() {return foodkind;}

    public void setFoodkind(String foodkind) {this.foodkind = foodkind;}

    public String getFood() {return food;}

    public void setFood(String food) {this.food = food;}

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
