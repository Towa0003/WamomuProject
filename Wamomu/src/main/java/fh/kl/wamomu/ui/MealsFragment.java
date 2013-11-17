package fh.kl.wamomu.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
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

import java.util.Calendar;

import fh.kl.wamomu.R;

/**
 * Created by Thundernator on 04.11.13.
 */
public class MealsFragment extends Fragment {

    private ListView overview_listview;
    private Button btnadd;
    private EditText timepicker, datepicker;
    private Spinner spMealGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.meals,
                container, false);
        getActivity().setTitle("Mahlzeiten");

        String[] art =      new String[]{"Frühstück"  , "Mittagessen" , "Abendessen"  };
        String[] gericht =  new String[]{"Nutellabrot"  , "Gularsch" ,  "Salamibrot" };

        overview_listview = (ListView) view.findViewById(R.id.overview_listView);
        Context context = getActivity();
        OverviewArrayAdapter adapter = new OverviewArrayAdapter(context ,art,gericht);
        overview_listview.setAdapter(adapter);


        Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.dialog_add_mahlzeit);
        dialog.setTitle("Mahlzeit");
        dialog.setCancelable(true);

        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("Mahlzeit hinzufügen");
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        image.setImageResource(R.drawable.ic_launcher);
        btnadd = (Button)dialog.findViewById(R.id.btnAdd);
        timepicker = (EditText)dialog.findViewById(R.id.timeedit);
        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
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
        datepicker = (EditText)dialog.findViewById(R.id.editdate);
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int date = mcurrentTime.get(Calendar.DATE);
                int month = mcurrentTime.get(Calendar.MONTH);
                int year = mcurrentTime.get(Calendar.YEAR);
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
        spMealGroup = (Spinner)dialog.findViewById(R.id.spMealGroup);
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

            }
        });
        dialog.show();


        return view;
    }
}


