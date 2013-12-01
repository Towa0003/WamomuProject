package fh.kl.wamomu.ui;

/**
 * Created by Thundernator on 04.11.13.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import fh.kl.wamomu.R;

public class OverviewArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values_art;
    private final String[] values_gericht;
    private final String[] values_date;

    public OverviewArrayAdapter(Context context, String[] values_art, String[] values_gericht, String[] values_date) {
        super(context, R.layout.overview_row, values_art);
        this.context = context;
        this.values_art = values_art;
        this.values_gericht = values_gericht;
        this.values_date = values_date;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.overview_row, parent, false);
        TextView art = (TextView) rowView.findViewById(R.id.tv_art);
        TextView gericht = (TextView) rowView.findViewById(R.id.tv_gericht);
        TextView date = (TextView) rowView.findViewById(R.id.tv_datum);
        art.setText(values_art[position]);
        gericht.setText(values_gericht[position]);
        date.setText(values_date[position]);

        return rowView;
    }

}
