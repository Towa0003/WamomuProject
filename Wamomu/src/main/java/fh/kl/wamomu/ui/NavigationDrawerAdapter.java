package fh.kl.wamomu.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import fh.kl.wamomu.R;

/**
 * Created by Christian on 21.12.13.
 */
public class NavigationDrawerAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<NavigationDrawerItem> navDrawerItems;

    public NavigationDrawerAdapter(Context context, ArrayList<NavigationDrawerItem> navDrawerItems){
        this.context = context;
        this.navDrawerItems = navDrawerItems;
    }
    /**
     * Gibt die Position zurück die gedrückt wurde
     * @return navDrawerItems.size()
     */
    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    /**
     * Gibt das aktuele Fragment zurück
     * @return navDrawerItems.get(position)
     */
    @Override
    public Object getItem(int position) {
        return navDrawerItems.get(position);
    }

    /**
     * Gibt die Position zurück an der das Element steht
     * @return position
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Darstellung der NavigationDrawer Items
     * @return convertView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.text1);
        TextView txtCount = (TextView) convertView.findViewById(R.id.counter);

        imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
        txtTitle.setText(navDrawerItems.get(position).getTitle());

        // displaying count
        // check whether it set visible or not
        if(navDrawerItems.get(position).getCounterVisibility()){
            txtCount.setText(navDrawerItems.get(position).getCount());
        }else{
            // hide the counter view
            txtCount.setVisibility(View.GONE);
        }

        return convertView;
    }
}
