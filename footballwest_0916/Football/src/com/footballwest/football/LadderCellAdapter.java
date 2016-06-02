package com.footballwest.football;


import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LadderCellAdapter extends ArrayAdapter<HashMap<String, String>> {

    private final Activity context;
	private final ArrayList<HashMap<String, String>> list;
	private LadderFragment ladderFr;
	
    public LadderCellAdapter(Activity context, ArrayList<HashMap<String, String>> list) {
    	super(context, R.layout.ladder_list_row, list);
		this.context = context;
		this.list = list;
    }
    
    public void setFragment(LadderFragment fr)
    {
    	ladderFr = fr;
    }

   
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	LayoutInflater inflator = context.getLayoutInflater();
        final View view = inflator.inflate(R.layout.ladder_list_row, null);
        
        TextView txtNumber = (TextView) view.findViewById(R.id.ladder_cell_no);
        TextView txtTeam = (TextView) view.findViewById(R.id.ladder_cell_team);
        TextView txtPlayed = (TextView) view.findViewById(R.id.ladder_cell_played);
        TextView txtWon = (TextView) view.findViewById(R.id.ladder_cell_won);
        TextView txtDrawn = (TextView) view.findViewById(R.id.ladder_cell_drawn);
        TextView txtLost = (TextView) view.findViewById(R.id.ladder_cell_lost);
        TextView txtValue = (TextView) view.findViewById(R.id.ladder_cell_value);
        
        
        HashMap<String, String> data = list.get(position);
        
        String number = String.valueOf(position + 1);
        txtNumber.setText(number);
        txtTeam.setText((String)data.get("Team"));
        txtPlayed.setText((String)data.get("P"));
        txtWon.setText((String)data.get("W"));
        txtDrawn.setText((String)data.get("D"));
        txtLost.setText((String)data.get("L"));
        txtValue.setText((String)data.get("Pts"));
            
        view.setOnClickListener(new FixturesDetailOnClickListener(position){
        	
        	
        	public void onClick(View v)
        	{
        		for (int i = 0; i < ladderFr.contentList.getChildCount(); i++) 
        		{
        			ladderFr.contentList.getChildAt(i).setBackgroundResource(R.drawable.cellfw);
        		}  
                view.setBackgroundResource(R.drawable.cellfw_selected);
                    
                
        	}
        });
        
        
        return view;
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

