package fh.kl.wamomu.meta;

import android.text.format.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Meta Klasse, in der alle Daten für eine Mahlzeit gespeichert werden können.
 */
public class meal {

    int mealID;
    String foodkind;
    String food;
    Date date;
    Date time;

    /**
     * Konstruktor
     * @param mealID
     * @param foodkind
     * @param food
     * @param date
     * @param time
     */
    public meal(int mealID, String foodkind, String food, Date date, Date time) {
        this.mealID = mealID;
        this.foodkind = foodkind;
        this.food = food;
        this.date = date;
        this.time = time;
    }


    public String getFoodkind() {return foodkind;}

    public String getFood() {return food;}

    public Date getDate() {
        return date;
    }

    public Date getTime() {
        return time;
    }

}
