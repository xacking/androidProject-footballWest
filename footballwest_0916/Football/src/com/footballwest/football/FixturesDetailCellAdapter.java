package com.footballwest.football;

/**
 * Created by a on 8/23/15.
 */
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FixturesDetailCellAdapter extends ArrayAdapter<HashMap<String, String>> {

    private String strTitle = "";
	private final Activity context;
	private final ArrayList<HashMap<String, String>> list;
	private FixturesFragmentDetail fixturesDetailFr;
	
    public FixturesDetailCellAdapter(Activity context, ArrayList<HashMap<String, String>> list) {
    	super(context, R.layout.fixtures_detail_list_row, list);
		this.context = context;
		this.list = list;
    }
    
    public void setFragment(FixturesFragmentDetail fr)
    {
    	fixturesDetailFr = fr;
    }

   
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	LayoutInflater inflator = context.getLayoutInflater();
        final View view = inflator.inflate(R.layout.fixtures_detail_list_row, null);
        TextView separator = (TextView) view.findViewById(R.id.fixtures_detail_cell_separator);
        TextView date = (TextView) view.findViewById(R.id.fixtures_detail_cell_date);
        TextView homeName = (TextView) view.findViewById(R.id.fixtures_detail_cell_homename);
        TextView homeScore = (TextView) view.findViewById(R.id.fixtures_detail_cell_homescore);
        TextView awayName = (TextView) view.findViewById(R.id.fixtures_detail_cell_awayname);
        TextView awayScore = (TextView) view.findViewById(R.id.fixtures_detail_cell_awayscore);
        TextView venueName = (TextView) view.findViewById(R.id.fixtures_detail_cell_venuename);
        
        
        HashMap<String, String> data = list.get(position);
        String strSeparator = (String)data.get("CompName");
        separator.setText(strSeparator);
        date.setText((String)data.get("Date"));
        homeName.setText((String)data.get("HomeName"));
        String strHomeScore = (String)data.get("HomeScore");
        if (strHomeScore == null || strHomeScore.isEmpty())
        	strHomeScore = "-";
        homeScore.setText(strHomeScore);
        awayName.setText((String)data.get("AwayName"));
        String strAwayScore = (String)data.get("AwayScore");
        if (strAwayScore == null || strAwayScore.isEmpty())
        	strAwayScore = "-";
        awayScore.setText(strAwayScore);
        
        venueName.setText((String)data.get("VenueName"));
        
        if (strTitle.equals(strSeparator) && position != 0)
        {
        	separator.setVisibility(View.GONE);
        }else
        {
        	strTitle = strSeparator;
        }
        
        view.setOnClickListener(new FixturesDetailOnClickListener(position){
        	
        	
        	public void onClick(View v)
        	{
        		for (int i = 0; i < fixturesDetailFr.contentList.getChildCount(); i++) 
        		{
                    fixturesDetailFr.contentList.getChildAt(i).setBackgroundResource(R.drawable.fixture);
        		}  
                view.setBackgroundResource(R.drawable.custom_tile_bg);

                showActionSheet(this.position);
                
        	}
        });
        
        
        return view;
    }
    
    public void showActionSheet(int position)
    {
    	fixturesDetailFr.showActionSheet(position);
    	
    }
    
    
    
    private class FixturesDetailOnClickListener implements View.OnClickListener
    {
    	public int position;
    	public FixturesDetailOnClickListener(int i)
    	{
    		this.position = i;
    	}
    	
    	@Override
    	public void onClick(View v) {
						
		}
    }
    
  
}

