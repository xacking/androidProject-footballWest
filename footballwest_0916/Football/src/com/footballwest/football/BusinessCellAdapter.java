package com.footballwest.football;

/**
 * Created by a on 8/23/15.
 */
import java.util.ArrayList;
import java.util.HashMap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BusinessCellAdapter extends ArrayAdapter<HashMap<String, String>> {

    
	private final Activity context;
	private final ArrayList<HashMap<String, String>> list;
	private BusinessFragment businessFr;
	
    public BusinessCellAdapter(Activity context, ArrayList<HashMap<String, String>> list) {
    	super(context, R.layout.directory_list_row, list);
		this.context = context;
		this.list = list;
    }

    public void sendFragment(BusinessFragment fr)
    {
    	businessFr = fr;
    }
    

    public void showDetail(HashMap<String, String> data)
    {
    	Global.g_nDeep += 1;
    	Fragment fr = new BusinessDetail(data);
        
        FragmentManager fm = businessFr.getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        
        fragmentTransaction.replace(R.id.fragment_fixtures_content, fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	LayoutInflater inflator = context.getLayoutInflater();
        final View view = inflator.inflate(R.layout.directory_list_row, null);
        
        view.setOnClickListener(new BusinessOnClickListener(position) {
			
			@Override
			public void onClick(View v) {

				for (int i = 0; i < businessFr.contentList.getChildCount(); i++) 
				{
					businessFr.contentList.getChildAt(i).setBackgroundResource(R.drawable.cellfw);
		        }
				view.setBackgroundResource(R.drawable.cellfw_selected);
				
				HashMap<String, String> tmpData = list.get(this.position);
				showDetail(tmpData);
			}
		});
        
        
        TextView txtName = (TextView) view.findViewById(R.id.directory_cell_categoryname);
        
        HashMap<String, String> data = list.get(position);
        txtName.setText((String)data.get("name"));
        
        
        
        
        return view;
    }
    
    private class BusinessOnClickListener implements View.OnClickListener
    {
    	public int position;
    	public BusinessOnClickListener(int i)
    	{
    		this.position = i;
    	}
    	
    	@Override
    	public void onClick(View v) {
						
		}
    }
    
  
}

