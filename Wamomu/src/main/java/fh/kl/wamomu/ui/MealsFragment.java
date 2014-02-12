package fh.kl.wamomu.ui;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
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
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import fh.kl.wamomu.R;
import fh.kl.wamomu.database.database;
import fh.kl.wamomu.database.databaseMeals;
import fh.kl.wamomu.database.databasePushMeal;

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

    public static String getDatumPush() {
        return datumPush;
    }

    public static String getZeit() {
        return zeit;
    }

    public static String getUserid() {
        return userid;
    }

    public static String getEssenszeit() {
        return essenszeit;
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
            final Dialog dialog = new Dialog(getActivity());

            dialog.setContentView(R.layout.dialog_add_mahlzeit);
            dialog.setTitle("Mahlzeit");
            dialog.setCancelable(true);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    Log.d("meals = 0", "user cancelling authentication");
                    meals = 0;

                }
            });

            TextView text = (TextView) dialog.findViewById(R.id.tv_mealtext);
            text.setText("Mahlzeit hinzufügen");
            ImageView image = (ImageView) dialog.findViewById(R.id.iv_mealimage);
            image.setImageResource(R.drawable.mahlzeit);

            mealedit = (EditText) dialog.findViewById(R.id.et_mealedit);

            btnadd = (Button) dialog.findViewById(R.id.bt_add);
            timepicker = (EditText) dialog.findViewById(R.id.et_timeedit);
            Calendar mcurrentTime = Calendar.getInstance();

            final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            final int minute = mcurrentTime.get(Calendar.MINUTE);
            final int date = mcurrentTime.get(Calendar.DATE);
            final int month = mcurrentTime.get(Calendar.MONTH);
            final int year = mcurrentTime.get(Calendar.YEAR);

            SimpleDateFormat sdfT1 = new SimpleDateFormat("HH:mm");
            timepicker.setText(sdfT1.format(mcurrentTime.getTime()));

            SimpleDateFormat sdfT2 = new SimpleDateFormat("HHmm");
            zeit = sdfT2.format(mcurrentTime.getTime()) + "00";

            timepicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            String strHour = String.valueOf(selectedHour);
                            String strMin = String.valueOf(selectedMinute);
                            if ((selectedHour < 10) && (selectedMinute < 10)) {
                                strHour = "0" + String.valueOf(selectedHour);
                                strMin = "0" + String.valueOf(selectedMinute);
                            } else if (selectedMinute < 10) {
                                strMin = "0" + String.valueOf(selectedMinute);
                            } else if (selectedHour < 10) {
                                strHour = "0" + String.valueOf(selectedHour);
                            }

                            timepicker.setText(strHour + ":" + strMin);

                            zeit = (strHour + strMin + "00");
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }
            });

            datepicker = (EditText) dialog.findViewById(R.id.et_dateedit);

            SimpleDateFormat sdfD1 = new SimpleDateFormat("yyyy-MM-dd");
            datepicker.setText(sdfD1.format(mcurrentTime.getTime()));//date + "." + month + "." + year

            SimpleDateFormat sdfD2 = new SimpleDateFormat("yyyyMMdd");
            datumPush = sdfD2.format(mcurrentTime.getTime());

            datepicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog mDatePicker;
                    mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDate) {
                            String strYear = String.valueOf(selectedYear);
                            String strMon = String.valueOf(selectedMonth + 1);
                            String strDate = String.valueOf(selectedDate);
                            if ((selectedMonth < 9) && (selectedDate < 10)) {
                                strMon = "0" + String.valueOf(selectedMonth + 1);
                                strDate = "0" + String.valueOf(selectedDate);
                            } else if (selectedDate < 10) {
                                strDate = "0" + String.valueOf(selectedDate);
                            } else if (selectedMonth < 9) {
                                strMon = "0" + String.valueOf(selectedMonth + 1);
                            }

                            datepicker.setText(strYear + "-" + strMon + "-" + strDate);

                            datumPush = (strYear + strMon + strDate);
                        }
                    }, date, month, year);
                    mDatePicker.setTitle("Select Date");
                    mDatePicker.show();
                }
            });
            // Spinner
            spMealGroup = (Spinner) dialog.findViewById(R.id.sp_MealGroup);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                    getActivity(), R.array.essensart, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spMealGroup.setAdapter(adapter2);

            spMealGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parent, View arg1,
                                           int arg2, long arg3) {
                    String selItem = parent.getSelectedItem().toString();
                    Log.d("MealsFragment: ", "SELECTED ITEM: " + selItem);
                }

                public void onNothingSelected(AdapterView<?> arg0) {

                }
            });
//            Hinzufügen Button in Dialog
            btnadd.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    essenszeit = String.valueOf(spMealGroup.getSelectedItem());
                    essen = mealedit.getText().toString();
                    userid = String.valueOf(database.getUsersID());//.toString();

                    Log.d("MealsFragment: ", "SELECTED ITEM: " + essenszeit + " " + essen + " " + datumPush + " " + zeit + " " + userid);

                    dbPushMeals.accessWebService();             // Meal Daten Pushen
                    dbMeals.accessWebService();

                    // halbe Sek delay, da sonst Nullpointerexcpetopn, wahrsch. wegen access WebService
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    dbMeals.checkMeal(database.getUsersID());   // Daten aus database anzeigen

                    Toast.makeText(getActivity(), "Meal added", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    meals = 0;
                }
            });
            // Fragment updaten um alle values anzuzeigen
            DialogInterface.OnDismissListener listener = new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fl_content_frame, mf);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                }
            };
            dialog.setOnDismissListener(listener);

            dialog.show();
        }

        return view;
    }
}


