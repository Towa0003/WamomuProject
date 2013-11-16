package fh.kl.wamomu.ui;

/**
 * Created by Thundernator on 04.11.13.
 */

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import fh.kl.wamomu.R;

public class UebersichtFragment extends Fragment {

    private ListView overview_listview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.overview,
                container, false);
        getActivity().setTitle("Übersicht");
        String[] art =      new String[]{"Frühstück" , "Messung" , "Mittagessen" , "Messung" , "Abendessen" , "Messung"};
        String[] gericht =  new String[]{"Nutellabrot" , "32 mg" , "Gulasch" , "50 mg" , "Salamibrot" , "30 mg"};

        overview_listview = (ListView) view.findViewById(R.id.overview_listView);
        Context context = getActivity();
        OverviewArrayAdapter adapter = new OverviewArrayAdapter(context ,art,gericht);
        overview_listview.setAdapter(adapter);

        return view;
    }

}