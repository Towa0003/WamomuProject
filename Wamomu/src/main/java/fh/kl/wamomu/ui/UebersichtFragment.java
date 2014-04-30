package fh.kl.wamomu.ui;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.text.SimpleDateFormat;
import fh.kl.wamomu.R;
import fh.kl.wamomu.database.databaseMeals;
import fh.kl.wamomu.database.databaseMeasurements;

public class UebersichtFragment extends Fragment {

    private ListView overview_listview;
    int size = (databaseMeals.meals.size() + databaseMeasurements.measurements.size());

    String[] art = new String[size];
    String[] gericht =  new String[size];
    String[] datum = new String[size];
    SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM");
    SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
    int countermeals = 0;
    int countermeas = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.overview,
                container, false);
        getActivity().setTitle("Übersicht");
        Log.d("UebersichtFragment", "SIZE: " + size);

        //Die Datenbankelemente werden durchlaufen und in die TextViews gefüllt
        for (int i = 0; i < art.length; i++){
            if (i == 0)
            {
                art[i] = "Messung";
                gericht[i] = databaseMeasurements.measurements.get(countermeas).getmvalue() + " mmol/l";
                datum[i] = sdfDate.format(databaseMeasurements.measurements.get(countermeas).getDate()) + "/" + sdfTime.format(databaseMeasurements.measurements.get(countermeas).getTime());
                countermeas++;
            }
            else if(((i-1)%3 == 0) && (countermeals < databaseMeals.meals.size()))
            {
                art[i] = databaseMeals.meals.get(countermeals).getFoodkind();
                gericht[i] = databaseMeals.meals.get(countermeals).getFood();
                datum[i] = sdfDate.format(databaseMeals.meals.get(countermeals).getDate()) + "/" + sdfTime.format(databaseMeals.meals.get(countermeals).getTime());
                countermeals++;
            }
            else
            {
                if(countermeas >= databaseMeasurements.measurements.size()){
                    art[i] = databaseMeals.meals.get(countermeals).getFoodkind();
                    gericht[i] = databaseMeals.meals.get(countermeals).getFood();
                    datum[i] = sdfDate.format(databaseMeals.meals.get(countermeals).getDate()) + "/" + sdfTime.format(databaseMeals.meals.get(countermeals).getTime());
                    countermeals++;
                }
                else{
                    art[i] = "Messung";
                    gericht[i] = databaseMeasurements.measurements.get(countermeas).getmvalue() + " mmol/l";
                    datum[i] = sdfDate.format(databaseMeasurements.measurements.get(countermeas).getDate()) + "/" + sdfTime.format(databaseMeasurements.measurements.get(countermeas).getTime());
                    countermeas++;
                }
            }
        }
        overview_listview = (ListView) view.findViewById(R.id.lv_meals);
        Context context = getActivity();
        OverviewArrayAdapter adapter = new OverviewArrayAdapter(context ,art,gericht, datum);
        overview_listview.setAdapter(adapter);

        return view;
    }

}