package fh.kl.wamomu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Thundernator on 04.11.13.
 */
public class Overview extends Activity {

    private ListView overview_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview);

        String[] art =      new String[]{"Frühstück" , "Messung" , "Mittagessen" , "Messung" , "Abendessen" , "Messung"};
        String[] gericht =  new String[]{"Nutellabrot" , "32 mg" , "Gulasch" , "50 mg" , "Salamibrot" , "30 mg"};

        overview_listview = (ListView) findViewById(R.id.overview_listView);
        OverviewArrayAdapter adapter = new OverviewArrayAdapter(this,art,gericht);
        overview_listview.setAdapter(adapter);


    }
}
