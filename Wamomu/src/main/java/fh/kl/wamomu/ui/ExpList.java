package fh.kl.wamomu.ui;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Max on 07.11.13.
 */
public class ExpList extends ExpandableListActivity implements
        ExpandableListView.OnChildClickListener {
    private Button add;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExpandableListView expandbleLis = getExpandableListView();
        expandbleLis.setDividerHeight(2);
        expandbleLis.setGroupIndicator(null);
        expandbleLis.setClickable(true);

        setGroupData();
        setChildGroupData();

//        ExpandableListViewAdapter mNewAdapter = new ExpandableListViewAdapter(groupItem, childItem);
//        mNewAdapter
//                .setInflater(
//                        (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),
//                        this);
//        getExpandableListView().setAdapter(mNewAdapter);s
//        expandbleLis.setOnChildClickListener(this);


      
    }

    public void setGroupData() {
        groupItem.add("Frühstück");
        groupItem.add("Mittagessen");
        groupItem.add("Abendessen");
        groupItem.add("Sonstiges");
    }

    ArrayList<String> groupItem = new ArrayList<String>();
    ArrayList<Object> childItem = new ArrayList<Object>();

    public void setChildGroupData() {
        /**
         * Add Data For TecthNology
         */
        ArrayList<String> child = new ArrayList<String>();
        child.add("Frühstück");
        childItem.add(child);

        /**
         * Add Data For Mobile
         */
        child = new ArrayList<String>();
        child.add("Mittagessen");
        childItem.add(child);
        /**
         * Add Data For Manufacture
         */
        child = new ArrayList<String>();
        child.add("Abendessen");

        childItem.add(child);
        /**
         * Add Data For Extras
         */
        child = new ArrayList<String>();
        child.add("Sonstiges");
        childItem.add(child);
    }



    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
                                int groupPosition, int childPosition, long id) {
        Toast.makeText(ExpList.this, "Clicked On Child",
                Toast.LENGTH_SHORT).show();
        return true;
    }
}


