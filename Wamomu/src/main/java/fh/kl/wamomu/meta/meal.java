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


    public String getFoodkind() {return foodkind;}

    public String getFood() {return food;}

    public Date getDate() {
        return date;
    }

    public Date getTime() {
        return time;
    }

}
