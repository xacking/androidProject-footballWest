package com.footballwest.football;

/**
 * Created by a on 8/23/15.
 */
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class VenuesCellAdapter extends ArrayAdapter<HashMap<String, String>> {

    
	private final Activity context;
	private final ArrayList<HashMap<String, String>> list;
	private VenuesFragment venuesFr;
	
    public VenuesCellAdapter(Activity context, ArrayList<HashMap<String, String>> list) {
    	super(context, R.layout.venues_list_row, list);
		this.context = context;
		this.list = list;
    }

    public void sendFragment(VenuesFragment fr)
    {
    	venuesFr = fr;
    }
    
    public void showAlert(final int position)
    {
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(venuesFr.getActivity());
    	LayoutInflater inflater = venuesFr.getActivity().getLayoutInflater();
    	View view = inflater.inflate(R.layout.alert_layout, null);
    	TextView txtTitle = (TextView)view.findViewById(R.id.alert_text_title);
    	TextView txtContent = (TextView)view.findViewById(R.id.alert_text_content);
    	txtTitle.setText("Locate Venue");
    	
    	HashMap<String, String> selectedData = list.get(position);
    	String address = (String) selectedData.get("address");
        String suburb = (String) selectedData.get("suburb");
        String postcode = (String) selectedData.get("postcode");
        String strDetail = address + ", " + suburb + " " + postcode;
    	txtContent.setText(strDetail);
    	
    	builder.setView(view);
    	AlertDialog alertDialog = builder.create();
    	
        alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
        	public void onClick(DialogInterface dialog, final int which) {
        		
        		showMap(position);
        			
        	}
        });
        alertDialog.setButton2("No", new DialogInterface.OnClickListener() {
        	public void onClick(DialogInterface dialog, int which) {
        		// here you can add functions
        		
        	}
        });        
        
        alertDialog.show();
    }
    
    
    public void showMap(int position)
    {
    	HashMap<String, String> selectedData = list.get(position);
		String strURL = "http://maps.apple.com/maps?f=g&h1=em&q="; 
		String param = "&ie=UTF8&iwloc=addr&om=1";
		String latutude = (String) selectedData.get("latitude");
		String longitude = (String) selectedData.get("longitude");
		strURL += (latutude + "," + longitude + param);
		
		Global.g_nDeep += 1;
    	
        Fragment fr = new MapViewFragment(strURL, null);
        
        FragmentManager fm = venuesFr.getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        
        fragmentTransaction.replace(R.id.fragment_fixtures_content, fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
		
		
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	LayoutInflater inflator = context.getLayoutInflater();
        final View view = inflator.inflate(R.layout.venues_list_row, null);
        
        view.setOnClickListener(new VenuesOnClickListener(position) {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for (int i = 0; i < venuesFr.contentList.getChildCount(); i++) 
				{
	            	venuesFr.contentList.getChildAt(i).setBackgroundResource(R.drawable.cellfw);
		        }
				view.setBackgroundResource(R.drawable.cellfw_selected);
				
				// if selected, show Mapview
				showAlert(this.position);
				
			}
		});
        
        
        TextView txtSeparator = (TextView) view.findViewById(R.id.venues_cell_separator);
        TextView txtTitle = (TextView) view.findViewById(R.id.venues_cell_title);
        TextView txtDetail = (TextView) view.findViewById(R.id.venues_cell_detail);
        
        
        HashMap<String, String> data = list.get(position);
        String strSeparator = (String)data.get("character");
        
        if (strSeparator == null || strSeparator.equals(""))
        	txtSeparator.setVisibility(View.GONE);
        else
        	txtSeparator.setText(strSeparator);
        txtTitle.setText((String)data.get("name"));
        
        String address = (String) data.get("address");
        String suburb = (String) data.get("suburb");
        String postcode = (String) data.get("postcode");
        String strDetail = address + ", " + suburb + " " + postcode;
        txtDetail.setText(strDetail);
        
        
        
        return view;
    }
    
    
    
    private class VenuesOnClickListener implements View.OnClickListener
    {
    	public int position;
    	public VenuesOnClickListener(int i)
    	{
    		this.position = i;
    	}
    	
    	@Override
    	public void onClick(View v) {
						
		}
    }
    
  
}

