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
import fh.kl.wamomu.database.databaseMeasurements;
import fh.kl.wamomu.database.databasePushMeasurement;

/**
 * Created by Thundernator on 04.11.13.
 */
public class MeasurementFragment extends Fragment {

    static public int dia = 0;

    public static databaseMeasurements dbMeasurements;
    public static databasePushMeasurement dbPushMeasurements;

    private static String messwert;
    private static String datumPush;
    private static String zeit;
    private static String userid;

    public ListView overview_listview;
    public int sfItem = 0;
    Fragment msf;
    SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM");
    SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
    OverviewArrayAdapter adapter;
    private Button btnSave;
    private EditText timepicker, datepicker, measurementedit;
    private Spinner spMeasureGroup;

    public static String getMesswert() {
        return messwert;
    }

    public static void setMesswert(String messwert) {
        MeasurementFragment.messwert = messwert;
    }

    public static String getDatumPush() {
        return datumPush;
    }

    public static void setDatumPush(String datumPush) {
        MeasurementFragment.datumPush = datumPush;
    }

    public static String getZeit() {
        return zeit;
    }

    public static void setZeit(String zeit) {
        MeasurementFragment.zeit = zeit;
    }

    public static String getUserid() {
        return userid;
    }

    public static void setUserid(String userid) {
        MeasurementFragment.userid = userid;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.measurement,
                container, false);
        getActivity().setTitle("Messungen");

        dbMeasurements = new databaseMeasurements();
        dbPushMeasurements = new databasePushMeasurement();

        msf = new MeasurementFragment();

        String[] art = new String[databaseMeasurements.measurements.size()];
        String[] value = new String[databaseMeasurements.measurements.size()];
        String[] datum = new String[databaseMeasurements.measurements.size()];

        for (int i = 0; i < databaseMeasurements.measurements.size(); i++) {
            art[i] = "Messung";
            value[i] = databaseMeasurements.measurements.get(i).getmvalue() + " mmol/l";
            datum[i] = sdfDate.format(databaseMeasurements.measurements.get(i).getDate()) + "/" + sdfTime.format(databaseMeasurements.measurements.get(i).getTime());
        }
        overview_listview = (ListView) view.findViewById(R.id.lv_measurement);
        Context context = getActivity();
        adapter = new OverviewArrayAdapter(context, art, value, datum);
        overview_listview.setAdapter(adapter);
        overview_listview.setItemsCanFocus(true);

        // delay, da sonst der smoothscroll nicht funzt, wenn man von der Statistik drauf zugreift
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
                Toast.makeText(
                        getActivity(),
                        "Selected Value: " + position, Toast.LENGTH_SHORT).show();
                System.out.println("POSITION ITEM :  " + position);
                System.out.println("ID ITEM :  " + id);
                overview_listview.smoothScrollToPosition(getSfItem());
            }
        });

        if (dia == 1) {
            final Dialog dialog = new Dialog(getActivity());

            dialog.setContentView(R.layout.dialog_add_messung);
            dialog.setTitle("Messung hinzufügen");
            dialog.setCancelable(true);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    Log.d("dia = 0", "user cancelling authentication");
                    dia = 0;

                }
            });

            TextView text = (TextView) dialog.findViewById(R.id.tv_measuretext);
            text.setText("Hello, this is a custom dialog!");
            ImageView image = (ImageView) dialog.findViewById(R.id.iv_measureimage);
            image.setImageResource(R.drawable.messung);

            measurementedit = (EditText) dialog.findViewById(R.id.et_wertedit);

            btnSave = (Button) dialog.findViewById(R.id.bt_add_measure);
            timepicker = (EditText) dialog.findViewById(R.id.et_timeedit_measure);
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

            datepicker = (EditText) dialog.findViewById(R.id.et_dateedit_measure);

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
                            if ((selectedMonth < 10) && (selectedDate < 10)) {
                                strMon = "0" + String.valueOf(selectedMonth + 1);
                                strDate = "0" + String.valueOf(selectedDate);
                            } else if (selectedDate < 10) {
                                strDate = "0" + String.valueOf(selectedDate);
                            } else if (selectedMonth < 10) {
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
            spMeasureGroup = (Spinner) dialog.findViewById(R.id.sp_MeasureGroup);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                    getActivity(), R.array.zuckereinheit, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spMeasureGroup.setAdapter(adapter2);

            spMeasureGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parent, View arg1,
                                           int arg2, long arg3) {
                    String selItem = parent.getSelectedItem().toString();
                    System.out.println("SELECTED ITEM: " + selItem);
                }

                public void onNothingSelected(AdapterView<?> arg0) {

                }
            });
//            Hinzufügen Button in Dialog

            btnSave.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    messwert = measurementedit.getText().toString();
                    userid = String.valueOf(database.getUsersID()).toString();

                    System.out.println("SELECTED ITEM: " + messwert + " " + datumPush + " " + zeit + " " + userid);

                    dbPushMeasurements.accessWebService();
                    dbMeasurements.accessWebService();

                    // halbe Sek delay, da sonst Nullpointerexcpetopn, wahrsch. wegen access WebService aber ka genau
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    dbMeasurements.checkMeasurment(database.getUsersID());  // Daten aus database anzeigen

                    Toast.makeText(getActivity(), "Measurement added", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    dia = 0;
                }
            });
            // Fragment updaten um alle values anzuzeigen
            DialogInterface.OnDismissListener listener = new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fl_content_frame, msf);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                }
            };
            dialog.setOnDismissListener(listener);

            dialog.show();
        }

        return view;
    }

    public int getSfItem() {
        return sfItem;
    }

    public void setSfItem(int sfItem) {
        this.sfItem = sfItem;
    }
}


