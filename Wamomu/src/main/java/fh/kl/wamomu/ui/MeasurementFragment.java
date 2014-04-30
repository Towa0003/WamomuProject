package fh.kl.wamomu.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.TimePicker;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fh.kl.wamomu.R;
import fh.kl.wamomu.database.CheckUserID;
import fh.kl.wamomu.database.MeasurementReady;
import fh.kl.wamomu.database.RestfulMeasure;
import fh.kl.wamomu.database.RestfulUser;


public class MeasurementFragment extends Fragment implements CheckUserID, MeasurementReady {

    static public int dia = 0;


    private static String messwert;
    private static Date datumPush;
    private static String datumpushstr;
    private static Date zeit;
    private static String time;
    private static String userid;
    private Context contect;

    public static ListView overview_listview;
    public int sfItem = 0;
    Fragment changeFragment;
    SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM");
    SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
    OverviewArrayAdapter adapter;

    //Layout-Elemente
    private Button btnSave;
    private EditText timepicker, datepicker, measurementedit;
    private Spinner spMeasureGroup;
    public static int swtch = 0;
    private boolean uebertrage = true;
    Date testdate, testtime;
    FragmentTransaction ft;
    FragmentManager fm;
    ScheduledExecutorService scheduleTaskExecutor;
    private final View view = getView();
    private CheckUserID  pushsyncmeasure;
    public static List<Object> combined = new ArrayList<Object>();



    public static void setMesswert(String messwert) {
        MeasurementFragment.messwert = messwert;
    }

    public static void setDatumPush(Date datumPush) {
        MeasurementFragment.datumPush = datumPush;
    }

    public static void setZeit(Date zeit) {
        MeasurementFragment.zeit = zeit;
    }

    public static void setUserid(String userid) {
        MeasurementFragment.userid = userid;
    }

    /**
     * Getter für Messwert, Datum, Zeit und UserID
     * @return messwert
     * @return datumPush
     * @return zeit
     * @return userid
     */

    public static String getMesswert() {
        return messwert;
    }

    public static Date getDatumPush() {
        return datumPush;
    }

    public static Date getZeit() {
        return zeit;
    }

    public static String getUserid() {
        return userid;
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.measurement,
                container, false);
        getActivity().setTitle("Messungen");



        ft = getFragmentManager().beginTransaction();
        fm = getActivity().getSupportFragmentManager();

        scheduleTaskExecutor = Executors.newScheduledThreadPool(5);

        // This schedule a runnable task every 10 seconds
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                Log.e("RUUUUUUN", "" + swtch);
                if(swtch == 1) {
                    refresh();
                }
            }
        }, 5, 10, TimeUnit.SECONDS);




        //msf = new MeasurementFragment();

        //Array zur Anzeige der Daten der Messung
        String[] art = new String[RestfulMeasure.measurements.size()];
        String[] value = new String[RestfulMeasure.measurements.size()];
        String[] datum = new String[RestfulMeasure.measurements.size()];





        //Durclaufen aller Messungs-Elemente
        for (int i = 0; i < RestfulMeasure.measurements.size(); i++) {
            art[i] = "Messung";
            value[i] = RestfulMeasure.measurements.get(i).getmvalue() + " mg/dl";
            datum[i] = sdfDate.format(RestfulMeasure.measurements.get(i).getDate()) + "/" + sdfTime.format(RestfulMeasure.measurements.get(i).getTime());
        }
        overview_listview = (ListView) view.findViewById(R.id.lv_measurement);



        adapter = new OverviewArrayAdapter(getActivity(), art, value, datum);
        overview_listview.setAdapter(adapter);
        overview_listview.setItemsCanFocus(true);

        // Verzögerung, da sonst der Smoothscroll nicht funktioniert, wenn man von der Statistik darauf zugreift
        overview_listview.postDelayed(new Runnable() {
            @Override
            public void run() {
                overview_listview.performItemClick(overview_listview, getSfItem(), getSfItem());
//                overview_listview.setSelection(getSfItem());
            }
        }, 250);

        overview_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setSfItem(position);
                Log.d("MeasurementFragment: ", "POSITION ITEM :  " + position);
                Log.d("MeasurementFragment: ", "ID ITEM :  " + id);
                overview_listview.smoothScrollToPosition(getSfItem());
            }
        });

        //If Bedingung dient dazu, herauszufinden ob das Fragment mit oder ohne Dialog gestartet werden soll
        //wenn Fragment mit Dialog gestartet werden soll
        if (dia == 1) {
            //Dialog wird erstellt mit Titel, Text und Buttons
            final Dialog dialog = new Dialog(getActivity());

            dialog.setContentView(R.layout.dialog_add_messung);
            dialog.setTitle("Messung hinzufügen");
            dialog.setCancelable(true);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    Log.d("dia = 0", "user cancelling authentication");
                    uebertrage = false;
                    dia = 0;

                }
            });

            TextView text = (TextView) dialog.findViewById(R.id.tv_measuretext);
            text.setText("Messung hinzufügen!");
            ImageView image = (ImageView) dialog.findViewById(R.id.iv_measureimage);
            image.setImageResource(R.drawable.messung);

            measurementedit = (EditText) dialog.findViewById(R.id.et_wertedit);
            btnSave = (Button) dialog.findViewById(R.id.bt_add_measure);
            timepicker = (EditText) dialog.findViewById(R.id.et_timeedit_measure);

            //Kalender für die Anzeige des Datums
            Calendar mcurrentTime = Calendar.getInstance();

            final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            final int minute = mcurrentTime.get(Calendar.MINUTE);
            final int date = mcurrentTime.get(Calendar.DATE);
            final int month = mcurrentTime.get(Calendar.MONTH);
            final int year = mcurrentTime.get(Calendar.YEAR);

            SimpleDateFormat sdfT1 = new SimpleDateFormat("HH:mm");
            timepicker.setText(sdfT1.format(mcurrentTime.getTime()));

            final SimpleDateFormat sdfT2 = new SimpleDateFormat("HHmm");
            testtime = mcurrentTime.getTime();

            //Ein Picker für die aktuelle Zeit
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

                            time = (strHour + strMin + "00");
                            try{
                                testtime = sdfT2.parse(time);
                            }catch(ParseException pe){
                                pe.printStackTrace();
                            }
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }
            });

            datepicker = (EditText) dialog.findViewById(R.id.et_dateedit_measure);

            SimpleDateFormat sdfD1 = new SimpleDateFormat("yyyy-MM-dd");
            datepicker.setText(sdfD1.format(mcurrentTime.getTime()));//date + "." + month + "." + year

            //Datum zum Pushen in die  Datenbank
            final SimpleDateFormat sdfD2 = new SimpleDateFormat("yyyyMMdd");
            testdate = mcurrentTime.getTime();

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
                            if ((selectedMonth < 10) && (selectedDate < 10)) {
                                strMon = "0" + String.valueOf(selectedMonth + 1);
                                strDate = "0" + String.valueOf(selectedDate);
                            } else if (selectedDate < 10) {
                                strDate = "0" + String.valueOf(selectedDate);
                            } else if (selectedMonth < 10) {
                                strMon = "0" + String.valueOf(selectedMonth + 1);
                            }
                            datepicker.setText(strYear + "-" + strMon + "-" + strDate);

                            datumpushstr = (strYear + strMon + strDate);
                            try{
                                testdate = sdfD2.parse(datumpushstr);
                                Log.e("DATUM", " " + datumpushstr);
                            }catch(ParseException pe){
                                pe.printStackTrace();
                            }

                        }
                    }, 2014, 3, 12);
                    mDatePicker.setTitle("Select Date");
                    mDatePicker.show();
                }
            });
            // Spinner der mit dem Array der möglichen Einheiten befüllt wird
            spMeasureGroup = (Spinner) dialog.findViewById(R.id.sp_MeasureGroup);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                    getActivity(), R.array.zuckereinheit, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spMeasureGroup.setAdapter(adapter2);

            spMeasureGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parent, View arg1,
                                           int arg2, long arg3) {
                    String selItem = parent.getSelectedItem().toString();
                    Log.d("MeasurementFragment", "SELECTED ITEM: " + selItem);
                }

                public void onNothingSelected(AdapterView<?> arg0) {

                }
            });
            //Hinzufügen Button in Dialog

            btnSave.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    messwert = measurementedit.getText().toString();
                    setMesswert(messwert);
                    userid = RestfulUser.activeUser.getId().toString();
                    setUserid(userid);
                    setDatumPush(testdate);
                    setZeit(testtime);


                    checkUserID();
                    dialog.dismiss();
                    dia = 0;
                }
            });

            dialog.show();
        }

        return view;
    }




    public void refresh() {

        Log.e("REFRESH", "OK");

        Log.e("schedulTASK", "" + scheduleTaskExecutor.isShutdown());


        changeFragment = new MeasurementFragment();
        ft.replace(R.id.fl_content_frame, changeFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        scheduleTaskExecutor.shutdownNow();
        swtch = 0;

    }

    /**
     * Gibt aktuelles Fragment zurück
     * @return sfItem
     */
    public int getSfItem() {
        return sfItem;
    }

    /**
     * Setzt aktuelles Fragment
     * @param sfItem
     */
    public void setSfItem(int sfItem) {
        this.sfItem = sfItem;
    }



    @Override
    public void onStop() {
        super.onPause();
        Log.e("STOPPED", "LUCKY?");
        scheduleTaskExecutor.shutdownNow();

    }

    @Override
    public void checkUserID(){

        RestfulMeasure.PUSH_MEASURE(this);

    }

    @Override
    public void setMeasurementReady(){
        Log.d("SETMEASUREMENT", "READY");
        refresh();

    }




}


