package fh.kl.wamomu.dialogs;


import android.app.Dialog;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import fh.kl.wamomu.ui.ChildItem;
import fh.kl.wamomu.ui.ExpandableListViewAdapter;
import fh.kl.wamomu.ui.ParentItem;
import fh.kl.wamomu.R;

public class custom_dialog2 extends Dialog {





    public custom_dialog2(Context context) {
        super(context);
        setContentView(R.layout.dialog_add_mahlzeit);

        List<ParentItem> itemList = new ArrayList<ParentItem>();

        ParentItem parent1 = new ParentItem();
        parent1.getChildItemList().add(new ChildItem());
        parent1.getChildItemList().add(new ChildItem());
        parent1.getChildItemList().add(new ChildItem());

        ParentItem parent2 = new ParentItem();
        parent2.getChildItemList().add(new ChildItem());
        parent2.getChildItemList().add(new ChildItem());
        parent2.getChildItemList().add(new ChildItem());

        itemList.add(parent1);
        itemList.add(parent2);

        ExpandableListViewAdapter adapter = new ExpandableListViewAdapter(context, itemList);


    }
//        ExpandableListView expandbleLis = new ExpandableListView(this,);
//        expandbleLis.setDividerHeight(2);
//        expandbleLis.setGroupIndicator(null);
//        expandbleLis.setClickable(true);
//
//        setGroupData();
//        setChildGroupData();
//
//        ExpandableListViewAdapter mNewAdapter = new ExpandableListViewAdapter(groupItem, childItem);
//        mNewAdapter
//                .setInflater(
//                        (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),
//                        this);
//        getExpandableListView().setAdapter(mNewAdapter);
//        expandbleLis.setOnChildClickListener(this);
//
//        setTitle("Mahlzeit auswählen");
//
//        dialogButton = (Button) findViewById(R.id.dialogButtonOK);
//                // if button is clicked, close the custom dialog
//                dialogButton.setOnClickListener(new View.OnClickListener() {
//                   @Override
//                    public void onClick(View v) {
//                        dismiss();
//                    }
//                });
//        setCancelable(true);
//
//    }
//
//    public void setGroupData() {
//        groupItem.add("Frühstück");
//        groupItem.add("Mittagessen");
//        groupItem.add("Abendessen");
//        groupItem.add("Sonstiges");
//    }
//
//    ArrayList<String> groupItem = new ArrayList<String>();
//    ArrayList<Object> childItem = new ArrayList<Object>();
//
//    public void setChildGroupData() {
//        /**
//         * Add Data For TecthNology
//         */
//        ArrayList<String> child = new ArrayList<String>();
//        child.add("Frühstück");
//        childItem.add(child);
//
//        /**
//         * Add Data For Mobile
//         */
//        child = new ArrayList<String>();
//        child.add("Mittagessen");
//        childItem.add(child);
//        /**
//         * Add Data For Manufacture
//         */
//        child = new ArrayList<String>();
//        child.add("Abendessen");
//
//        childItem.add(child);
//        /**
//         * Add Data For Extras
//         */
//        child = new ArrayList<String>();
//        child.add("Sonstiges");
//        childItem.add(child);
//    }
//
//
//    public boolean onChildClick(ExpandableListView parent, View v,
//                                int groupPosition, int childPosition, long id) {
//        Log.d("Child clicked", "clicked");
//
//        return true;
//    }


}
