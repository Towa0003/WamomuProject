package fh.kl.wamomu.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fh.kl.wamomu.ExpList;
import fh.kl.wamomu.ExpandableListAdapter;
import fh.kl.wamomu.MealsFragment;
import fh.kl.wamomu.R;

public class custom_dialog2 extends Dialog implements ExpandableListAdapter {

    TextView t;
    ImageView image;
    Button dialogButton;

    public custom_dialog2(Context context) {
        super(context);
        setContentView(R.layout.custom);

        ExpandableListView expandbleLis = getExpandableListView();
        expandbleLis.setDividerHeight(2);
        expandbleLis.setGroupIndicator(null);
        expandbleLis.setClickable(true);

        setGroupData();
        setChildGroupData();

        ExpandableListAdapter mNewAdapter = new ExpandableListAdapter(groupItem, childItem);
        mNewAdapter
                .setInflater(
                        (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),
                        this);
        getExpandableListView().setAdapter(mNewAdapter);
        expandbleLis.setOnChildClickListener(this);

        setTitle("Mahlzeit auswählen");

        dialogButton = (Button) findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                   @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
        setCancelable(true);

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


    public boolean onChildClick(ExpandableListView parent, View v,
                                int groupPosition, int childPosition, long id) {
        Log.d("Child clicked", "clicked");

        return true;
    }


}
