package fh.kl.wamomu.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private static final class ViewHolder {
        TextView textLabel;
    }

    private final List<ParentItem> itemList;
    private final LayoutInflater inflater;

    public ExpandableListViewAdapter(Context context, List<ParentItem> itemList) {
        this.inflater = LayoutInflater.from(context);
        this.itemList = itemList;
    }

    @Override
    public ChildItem getChild(int groupPosition, int childPosition) {

        return itemList.get(groupPosition).getChildItemList().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return itemList.get(groupPosition).getChildItemList().size();
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
                             final ViewGroup parent) {
        View resultView = convertView;
        ViewHolder holder;


        if (resultView == null) {

            resultView = inflater.inflate(android.R.layout.test_list_item, null); //TODO change layout id
            holder = new ViewHolder();
            holder.textLabel = (TextView) resultView.findViewById(android.R.id.title); //TODO change view id
            resultView.setTag(holder);
        } else {
            holder = (ViewHolder) resultView.getTag();
        }

        final ChildItem item = getChild(groupPosition, childPosition);

        holder.textLabel.setText(item.toString());

        return resultView;
    }

    @Override
    public ParentItem getGroup(int groupPosition) {
        return itemList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return itemList.size();
    }

    @Override
    public long getGroupId(final int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View theConvertView, ViewGroup parent) {
        View resultView = theConvertView;
        ViewHolder holder;

        if (resultView == null) {
            resultView = inflater.inflate(android.R.layout.test_list_item, null); //TODO change layout id
            holder = new ViewHolder();
            holder.textLabel = (TextView) resultView.findViewById(android.R.id.title); //TODO change view id
            resultView.setTag(holder);
        } else {
            holder = (ViewHolder) resultView.getTag();
        }

        final ParentItem item = getGroup(groupPosition);

        holder.textLabel.setText(item.toString());

        return resultView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}


//package fh.kl.wamomu;
//
//import android.app.Activity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseExpandableListAdapter;
//import android.widget.Button;
//import android.widget.CheckedTextView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//
///**
// * Created by Max on 07.11.13.
// */
//public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
//
//    public ArrayList<String> groupItem, tempChild;
//    public ArrayList<Object> Childtem = new ArrayList<Object>();
//    public LayoutInflater minflater;
//    public Activity activity;
//
//
//    public ExpandableListViewAdapter(ArrayList<String> grList, ArrayList<Object> childItem) {
//        groupItem = grList;
//        this.Childtem = childItem;
//    }
//
//    public void setInflater(LayoutInflater mInflater, Activity act) {
//        this.minflater = mInflater;
//        activity = act;
//    }
//
//    @Override
//    public Object getChild(int groupPosition, int childPosition) {
//        return null;
//    }
//
//    @Override
//    public long getChildId(int groupPosition, int childPosition) {
//        return 0;
//    }
//
//    @Override
//    public View getChildView(int groupPosition, final int childPosition,
//                             boolean isLastChild, View convertView, ViewGroup parent) {
//
//
//        tempChild = (ArrayList<String>) Childtem.get(groupPosition);
//        TextView text = null;
//        if (convertView == null) {
//            convertView = minflater.inflate(R.layout.childrow, null);
//        }
//        text = (TextView) convertView.findViewById(R.id.textView1);
//        text.setText(tempChild.get(childPosition));
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(activity, tempChild.get(childPosition),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//        //Hinzufügen-Button
//        Button add = (Button) convertView.findViewById(R.id.add);
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "Clicked On Add",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//        //setText actual time
//        Calendar c = Calendar.getInstance();
//        int mHour = c.get(Calendar.HOUR);
//        int mMinute = c.get(Calendar.MINUTE);
//        int mYear = c.get(Calendar.YEAR);
//        int mMonth = c.get(Calendar.MONTH);
//        int mDate = c.get(Calendar.DATE);
//
//
//        TextView textView = (TextView) convertView.findViewById(R.id.time);
//        textView .setText(mHour +":"+ mMinute);
//
//        TextView DateView = (TextView) convertView.findViewById(R.id.date);
//        DateView .setText(mDate  +"."+ mMonth + "." + mYear);
//
//
//        return convertView;
//    }
//
//
//    @Override
//    public int getChildrenCount(int groupPosition) {
//        return ((ArrayList<String>) Childtem.get(groupPosition)).size();
//    }
//
//    @Override
//    public Object getGroup(int groupPosition) {
//        return null;
//    }
//
//    @Override
//    public int getGroupCount() {
//        return groupItem.size();
//    }
//
//    @Override
//    public void onGroupCollapsed(int groupPosition) {
//        super.onGroupCollapsed(groupPosition);
//    }
//
//    @Override
//    public void onGroupExpanded(int groupPosition) {
//        super.onGroupExpanded(groupPosition);
//    }
//
//    @Override
//    public long getGroupId(int groupPosition) {
//        return 0;
//    }
//
//    @Override
//    public View getGroupView(int groupPosition, boolean isExpanded,
//                             View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = minflater.inflate(R.layout.grouprow, null);
//        }
//        ((CheckedTextView) convertView).setText(groupItem.get(groupPosition));
//        ((CheckedTextView) convertView).setChecked(isExpanded);
//        return convertView;
//    }
//
//    @Override
//    public boolean hasStableIds() {
//        return false;
//    }
//
//    @Override
//    public boolean isChildSelectable(int groupPosition, int childPosition) {
//        return false;
//    }
//
//}
