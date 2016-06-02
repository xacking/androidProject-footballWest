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

public class DirectoryCellAdapter extends ArrayAdapter<HashMap<String, String>> {

    
	private final Activity context;
	private final ArrayList<HashMap<String, String>> list;
	private DirectoryFragment directoryFr;
	
    public DirectoryCellAdapter(Activity context, ArrayList<HashMap<String, String>> list) {
    	super(context, R.layout.directory_list_row, list);
		this.context = context;
		this.list = list;
    }

    public void sendFragment(DirectoryFragment fr)
    {
    	directoryFr = fr;
    }
    
    public void gotoBusiness(ArrayList<HashMap<String, String>> data)
    {
    	
    	Global.g_nDeep += 1;
    	Fragment fr = new BusinessFragment(data);
        
        FragmentManager fm = directoryFr.getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        
        fragmentTransaction.replace(R.id.fragment_fixtures_content, fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	LayoutInflater inflator = context.getLayoutInflater();
        final View view = inflator.inflate(R.layout.directory_list_row, null);
        
        view.setOnClickListener(new DirectoryOnClickListener(position) {
			
			@Override
			public void onClick(View v) {
			
				for (int i = 0; i < directoryFr.contentList.getChildCount(); i++) 
				{
					directoryFr.contentList.getChildAt(i).setBackgroundResource(R.drawable.cellfw);
		        }
				view.setBackgroundResource(R.drawable.cellfw_selected);
				
				ArrayList<HashMap<String, String>> tmpData = new ArrayList<HashMap<String, String>>();
				tmpData.add(list.get(this.position));
				
				gotoBusiness(tmpData);
				
				
				
			}
		});
        
        
        TextView txtCategoryName = (TextView) view.findViewById(R.id.directory_cell_categoryname);
        
        HashMap<String, String> data = list.get(position);
        txtCategoryName.setText((String)data.get("bus_category_name"));
        
        
        
        
        return view;
    }
    
    private class DirectoryOnClickListener implements View.OnClickListener
    {
    	public int position;
    	public DirectoryOnClickListener(int i)
    	{
    		this.position = i;
    	}
    	
    	@Override
    	public void onClick(View v) {
						
		}
    }
    
  
}

