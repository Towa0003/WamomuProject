package fh.kl.wamomu.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import fh.kl.wamomu.R;
import fh.kl.wamomu.database.database;
import fh.kl.wamomu.database.databaseMeals;
import fh.kl.wamomu.database.databasePushMeal;
import fh.kl.wamomu.dialogs.dialog_meals;

/**
 * Created by Thundernator on 04.11.13.
 */
public class MealsFragment extends Fragment {

    SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM");
    SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
    static public int meals = 0;

    public static databaseMeals dbMeals;
    public static databasePushMeal dbPushMeals;

    private static String essenszeit;
    private static String essen;
    private static String datumPush;
    private static String zeit;
    private static String userid;

    Fragment mf;

    private ListView overview_listview;
    private Button btnadd;
    private EditText timepicker, datepicker, mealedit;
    private Spinner spMealGroup;

    public static String getEssen() {
        return essen;
    }

    public static void setEssen(String essen) {
        MealsFragment.essen = essen;
    }

    public static String getDatumPush() {
        return datumPush;
    }

    public static void setDatumPush(String datumPush) {
        MealsFragment.datumPush = datumPush;
    }

    public static String getZeit() {
        return zeit;
    }

    public static void setZeit(String zeit) {
        MealsFragment.zeit = zeit;
    }

    public static String getUserid() {
        return userid;
    }

    public static void setUserid(String userid) {
        MealsFragment.userid = userid;
    }

    public static String getEssenszeit() {
        return essenszeit;
    }

    public static void setEssenszeit(String essenszeit) {
        MealsFragment.essenszeit = essenszeit;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.meals,
                container, false);
        getActivity().setTitle("Mahlzeiten");

        dbMeals = new databaseMeals();
        dbPushMeals = new databasePushMeal();

        mf = new MealsFragment();


        String[] art = new String[databaseMeals.meals.size()];
        String[] gericht = new String[databaseMeals.meals.size()];
        String[] datum = new String[databaseMeals.meals.size()];

        for (int i = 0; i < databaseMeals.meals.size(); i++) {
            art[i] = databaseMeals.meals.get(i).getFoodkind();
            gericht[i] = databaseMeals.meals.get(i).getFood();
            datum[i] = sdfDate.format(databaseMeals.meals.get(i).getDate()) + "/" + sdfTime.format(databaseMeals.meals.get(i).getTime());
        }

        overview_listview = (ListView) view.findViewById(R.id.lv_meals);
        Context context = getActivity();
        OverviewArrayAdapter adapter = new OverviewArrayAdapter(context, art, gericht, datum);
        overview_listview.setAdapter(adapter);

        if (meals == 1) {
            final Dialog dialog = new dialog_meals(getActivity());



            dialog.show();
        }

        return view;
    }
}


