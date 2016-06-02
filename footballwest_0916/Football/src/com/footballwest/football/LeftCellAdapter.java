package com.footballwest.football;

/**
 * Created by a on 8/23/15.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LeftCellAdapter extends BaseAdapter {

    private Activity activity;
    private String[] label;
    private static LayoutInflater inflater=null;
    
    public LeftCellAdapter(Activity a,String[] s) {
        activity = a;
        label = s;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return label.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
        {
            vi = inflater.inflate(R.layout.left_list_row, null);
        }
        TextView labelTextView = (TextView)vi.findViewById(R.id.left_list_row_label); 
        // Setting all values in listview
        labelTextView.setText(label[position]);
        return vi;
    }
}
