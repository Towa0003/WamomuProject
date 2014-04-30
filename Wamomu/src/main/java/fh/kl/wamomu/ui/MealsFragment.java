package fh.kl.wamomu.ui;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.TimePicker;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import fh.kl.wamomu.R;
import fh.kl.wamomu.database.CheckUserID;
import fh.kl.wamomu.database.MealReady;
import fh.kl.wamomu.database.RestfulMeal;
import fh.kl.wamomu.database.RestfulUser;

public class MealsFragment extends Fragment implements CheckUserID, MealReady {

    //Erstellen des Formates des Datums
    SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM");
    SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");




    //Instanzvariablen
    private static String essenszeit;
    private static String essen;
    private static String datumPush;
    private static String zeit;
    private static String userid;

    static public int meals = 0;
    FragmentTransaction ft;
    FragmentManager fm;

    //Layout-Elemente
    Fragment changeFragment;
    public static ListView overview_listview;
    private Button btnadd;
    private EditText timepicker, datepicker, mealedit;
    private Spinner spMealGroup;

    //public static final View view = getView();

    private CheckUserID bsync, measuresync, mealssync, pushsyncmeal;
    private boolean isMealReady = false;

    public static void setEssenszeit(String essenszeit) {
        MealsFragment.essenszeit = essenszeit;
    }

    public static void setEssen(String essen) {
        MealsFragment.essen = essen;
    }

    public static void setDatumPush(String datumPush) {
        MealsFragment.datumPush = datumPush;
    }

    public static void setZeit(String zeit) {
        MealsFragment.zeit = zeit;
    }

    public static void setUserid(String userid) {
        MealsFragment.userid = userid;
    }

    //getter für die Daten aus der Datenbank
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



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.meals,
                container, false);

        getActivity().setTitle("Mahlzeiten");

        ft = getFragmentManager().beginTransaction();
        fm = getActivity().getSupportFragmentManager();

        overview_listview = (ListView) view.findViewById(R.id.lv_meals);




        //Arrays für art, gericht und datum
        String[] art = new String[RestfulMeal.mahlzeit.size()];
        String[] gericht = new String[RestfulMeal.mahlzeit.size()];
        String[] datum = new String[RestfulMeal.mahlzeit.size()];



        //Datensätze aus der Datenbank werden in die ArrayListe gepushed
        for (int i = 0; i < RestfulMeal.mahlzeit.size(); i++) {
            art[i] = RestfulMeal.mahlzeit.get(i).getFoodkind();
            gericht[i] = RestfulMeal.mahlzeit.get(i).getFood();
            datum[i] = sdfDate.format(RestfulMeal.mahlzeit.get(i).getDate()) + "/" + sdfTime.format(RestfulMeal.mahlzeit.get(i).getTime());
        }

        //ListView wird befüllt


        OverviewArrayAdapter adapter = new OverviewArrayAdapter(getActivity(), art, gericht, datum);
        overview_listview.setAdapter(adapter);


        if (meals == 1) {
            //Dialog zum erstellen einer Mahlzeit
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


            ImageView image = (ImageView) dialog.findViewById(R.id.iv_mealimage);
            image.setImageResource(R.drawable.mahlzeit);

            mealedit = (EditText) dialog.findViewById(R.id.et_mealedit);

            btnadd = (Button) dialog.findViewById(R.id.bt_add);
            timepicker = (EditText) dialog.findViewById(R.id.et_timeedit);

            //Kalender für den DatePicker
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
                    //Dialog für den TimePicker
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
                    }, hour, minute, true);//Yes für 24 Stunden
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }
            });

            datepicker = (EditText) dialog.findViewById(R.id.et_dateedit);

            SimpleDateFormat sdfD1 = new SimpleDateFormat("yyyy-MM-dd");
            datepicker.setText(sdfD1.format(mcurrentTime.getTime()));//date + "." + month + "." + year

            //Zeitpunkt der in die Datenbank gepushed wird
            SimpleDateFormat sdfD2 = new SimpleDateFormat("yyyyMMdd");
            datumPush = sdfD2.format(mcurrentTime.getTime());


            datepicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Picker für das Datum
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

                            //Datum das in die Datenbank gepushed wird
                            datumPush = (strYear + strMon + strDate);
                        }
                    }, 2014, 3, 12);
                    mDatePicker.setTitle("Wähle das Datum aus");
                    mDatePicker.show();
                }
            });
            // Spinner für die Auswahl der Mahlzeit
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
            //Hinzufügen Button in Dialog
            btnadd.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    essenszeit = String.valueOf(spMealGroup.getSelectedItem());
                    setEssenszeit(essenszeit);
                    essen = mealedit.getText().toString();
                    setEssen(essen);
                    userid = String.valueOf(RestfulUser.activeUser.getId());//.toString();
                    setUserid(userid);
                    setDatumPush(datumPush);
                    setZeit(zeit);

                    Log.d("MealsFragment: ", "SELECTED ITEM: " + essenszeit + " " + essen + " " + datumPush + " " + zeit + " " + userid);

                    checkUserID();            // Meal Daten Pushen


                    Toast.makeText(getActivity(), "Meal added", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    meals = 0;
                }
            });

            dialog.show();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    @Override
    public void setMealReady(){

        changeFragment = new MealsFragment();
        ft.replace(R.id.fl_content_frame, changeFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

    }


    @Override
    public void checkUserID() {

        RestfulMeal.PUSH_MEAL(this);

    }
}


