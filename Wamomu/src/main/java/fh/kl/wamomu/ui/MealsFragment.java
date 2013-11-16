package fh.kl.wamomu.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import fh.kl.wamomu.R;

/**
 * Created by Thundernator on 04.11.13.
 */
public class MealsFragment extends Fragment {

    private ListView overview_listview;

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

        return view;
    }
}


