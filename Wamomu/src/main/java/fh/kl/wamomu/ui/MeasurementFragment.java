package fh.kl.wamomu.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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

/**
 * Created by Thundernator on 04.11.13.
 */
public class MeasurementFragment extends Fragment {

    public ListView overview_listview;
    private Button btnSave;
    private EditText timepicker,datepicker;
    private Spinner spMeasureGroup;
    static public int dia = 0;
    SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM");
    SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");

    OverviewArrayAdapter adapter;

    public int sfItem = 0;


    public int getSfItem() {
        return sfItem;
    }

    public void setSfItem(int sfItem) {
        this.sfItem = sfItem;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.measurement,
                container, false);
        getActivity().setTitle("Messungen");

        String[] art = new String[databaseMeasurements.measurements.size()];
        String[] value =  new String[databaseMeasurements.measurements.size()];
        String[] datum = new String[databaseMeasurements.measurements.size()];

        for (int i = 0; i < databaseMeasurements.measurements.size(); i++){
            art[i] = "Messung";
            value[i] = databaseMeasurements.measurements.get(i).getmvalue() + " mmol/l";
            datum[i] = sdfDate.format(databaseMeasurements.measurements.get(i).getDate()) + "/" + sdfTime.format(databaseMeasurements.measurements.get(i).getTime());
        }
        overview_listview = (ListView) view.findViewById(R.id.lv_measurement);
        Context context = getActivity();
        adapter = new OverviewArrayAdapter(context ,art, value, datum);
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

        if(dia == 1) {
        final Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.dialog_add_messung);
        dialog.setTitle("Messung hinzufügen");
        dialog.setCancelable(true);
            dialog.setOnCancelListener(new  DialogInterface.OnCancelListener() {
                public  void  onCancel(DialogInterface dialog) {
                    Log.d("dia = 0", "user cancelling authentication");
                    dia = 0;

                }
            });

        TextView text = (TextView) dialog.findViewById(R.id.tv_measuretext);
        text.setText("Hello, this is a custom dialog!");
        ImageView image = (ImageView) dialog.findViewById(R.id.iv_measureimage);
        image.setImageResource(R.drawable.ic_launcher);
        btnSave = (Button)dialog.findViewById(R.id.bt_add_measure);
        timepicker = (EditText)dialog.findViewById(R.id.et_timeedit_measure);
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
                },hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });
        datepicker = (EditText)dialog.findViewById(R.id.et_dateedit_measure);
            datepicker.setText(date + "." + month + "." +year);
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedDate, int selectedMonth, int selectedYear) {
                        datepicker.setText( selectedDate + ":" + selectedMonth + ":" + selectedYear);
                    }
                }, date, month, year);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });
        spMeasureGroup = (Spinner)dialog.findViewById(R.id.sp_MeasureGroup);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                getActivity(), R.array.zuckereinheit, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMeasureGroup.setAdapter(adapter2);

        spMeasureGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int arg2, long arg3) {
                //String selItem = parent.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    dia = 0;
               dialog.dismiss();
            }
        });
        dialog.show();
        }

        return view;



    }
}


