package fh.kl.wamomu.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import fh.kl.wamomu.R;
import fh.kl.wamomu.database.database;
import fh.kl.wamomu.database.databaseMeals;

/**
 * Created by Thundernator on 04.11.13.
 */
public class MealsFragment extends Fragment {

    private ListView overview_listview;
    private Button btnadd;
    private EditText timepicker, datepicker;
    private Spinner spMealGroup;
    static public int meals = 0;
    SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM");
    SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.meals,
                container, false);
        getActivity().setTitle("Mahlzeiten");

        String[] art = new String[databaseMeals.meals.size()];
        String[] gericht =  new String[databaseMeals.meals.size()];
        String[] datum = new String[databaseMeals.meals.size()];

        for (int i = 0; i < databaseMeals.meals.size(); i++){
            art[i] = databaseMeals.meals.get(i).getFoodkind();
            gericht[i] = databaseMeals.meals.get(i).getFood();
            datum[i] = sdfDate.format(databaseMeals.meals.get(i).getDate()) + "/" + sdfTime.format(databaseMeals.meals.get(i).getTime());
        }

        overview_listview = (ListView) view.findViewById(R.id.lv_meals);
        Context context = getActivity();
        OverviewArrayAdapter adapter = new OverviewArrayAdapter(context ,art,gericht,datum);
        overview_listview.setAdapter(adapter);

        if(meals == 1){
        final Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.dialog_add_mahlzeit);
        dialog.setTitle("Mahlzeit");
        dialog.setCancelable(true);
            dialog.setOnCancelListener(new  DialogInterface.OnCancelListener() {
                public  void  onCancel(DialogInterface dialog) {
                    Log.d("meals = 0", "user cancelling authentication");
                    meals = 0;

                }
            });

            TextView text = (TextView) dialog.findViewById(R.id.tv_mealtext);
        text.setText("Mahlzeit hinzufügen");
        ImageView image = (ImageView) dialog.findViewById(R.id.iv_mealimage);
        image.setImageResource(R.drawable.ic_launcher);
        btnadd = (Button)dialog.findViewById(R.id.bt_add);
        timepicker = (EditText)dialog.findViewById(R.id.tv_timeedit);
            Calendar mcurrentTime = Calendar.getInstance();
            final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            final int minute = mcurrentTime.get(Calendar.MINUTE);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            final int date = mcurrentTime.get(Calendar.DATE);
            final int month = mcurrentTime.get(Calendar.MONTH);
            final int year = mcurrentTime.get(Calendar.YEAR);
            timepicker.setText(sdf.format(mcurrentTime.getTime()));

        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timepicker.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });
        datepicker = (EditText)dialog.findViewById(R.id.tv_dateedit);

        datepicker.setText(date + "." + month + "." + year);
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedDate, int selectedMonth, int selectedYear) {
                        datepicker.setText( selectedDate + " :" + selectedMonth + ":" + selectedYear);
                    }
                }, date, month, year);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });
        spMealGroup = (Spinner)dialog.findViewById(R.id.sp_MealGroup);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                getActivity(), R.array.essensart, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMealGroup.setAdapter(adapter2);

        spMealGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int arg2, long arg3) {
                //String selItem = parent.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });



        btnadd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                meals = 0;

            }
        });
        dialog.show();
        }

        return view;
    }

}


