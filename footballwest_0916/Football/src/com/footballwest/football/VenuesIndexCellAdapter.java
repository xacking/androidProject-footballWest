package com.footballwest.football;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class VenuesIndexCellAdapter extends ArrayAdapter<HashMap<String, String>> {
	
	private final Activity context;
	private final ArrayList<HashMap<String, String>> list;
	private VenuesFragment venuesFr;
	
    public VenuesIndexCellAdapter(Activity context, ArrayList<HashMap<String, String>> list) {
    	super(context, R.layout.venues_list_row, list);
		this.context = context;
		this.list = list;
    }
    
    public void sendFragment(VenuesFragment fr)
    {
    	venuesFr = fr;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	LayoutInflater inflator = context.getLayoutInflater();
        final View view = inflator.inflate(R.layout.venues_index_row, null);
        
        TextView txtLabel = (TextView) view.findViewById(R.id.venues_index_cell_text);
        
        HashMap<String, String> data = list.get(position);
        String strCharacter = (String)data.get("character");
        txtLabel.setText(strCharacter);
        
        String strPosition = (String)data.get("position");
        int contentPosition = Integer.parseInt(strPosition);
        view.setOnClickListener(new VenuesIndexOnClickListener(position, contentPosition) {
			
			@Override
			public void onClick(View v) {

				venuesFr.contentList.setSelection(this.contentPosition);
				
			}
		});
        
        return view;
    }
    
    private class VenuesIndexOnClickListener implements View.OnClickListener
    {
    	public int position;
    	public int contentPosition;
    	public VenuesIndexOnClickListener(int i, int contentPosition)
    	{
    		this.position = i;
    		this.contentPosition = contentPosition;
    	}
    	
    	@Override
    	public void onClick(View v) {
						
		}
    }

}
