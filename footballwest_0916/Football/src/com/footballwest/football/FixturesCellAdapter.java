package com.footballwest.football;

/**
 * Created by a on 8/23/15.
 */
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FixturesCellAdapter extends ArrayAdapter<HashMap<String, Object>> {

    
	private final Activity context;
	private final ArrayList<HashMap<String, Object>> list;
	private FixturesFragment fixturesFr;
	
    public FixturesCellAdapter(Activity context, ArrayList<HashMap<String, Object>> list) {
    	super(context, R.layout.fixtures_list_row, list);
		this.context = context;
		this.list = list;
    }

    public void setFragment(FixturesFragment fr)
    {
    	this.fixturesFr = fr;
    }
   
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	LayoutInflater inflator = context.getLayoutInflater();
        final View view = inflator.inflate(R.layout.fixtures_list_row, null);
        TextView textTitle = (TextView) view.findViewById(R.id.fixtures_cell_text);
        ImageView image = (ImageView) view.findViewById(R.id.fixtures_cell_image);
        
        HashMap<String, Object> data = (HashMap<String, Object>)list.get(position);
        String title = (String)data.get("title");
        textTitle.setText(title);
        String imageName = (String)data.get("image");
        image.setImageAlpha(0);
        
        if (imageName != null && !(imageName.equals("")))
        {
        	imageName = imageName.toLowerCase().substring(0, imageName.length() - 4);
            Log.e("Image Loading Error", imageName);
            image.setImageResource(context.getResources().getIdentifier(imageName, "drawable", context.getPackageName()));
            image.setImageAlpha(255);
        }
        
        view.setOnClickListener(new FixturesOnClickListener(position){
        	
        	
        	public void onClick(View v)
        	{
        		for (int i = 0; i < fixturesFr.contentList.getChildCount(); i++) 
				{
        			fixturesFr.contentList.getChildAt(i).setBackgroundResource(R.drawable.fixture);
		        }
        		view.setBackgroundResource(R.drawable.custom_tile_bg);
                
                String complist = (String)list.get(position).get("complist");
                if (complist == null || complist.isEmpty())
                {
                	ArrayList<HashMap<String, Object>> selectedFixtures = (ArrayList<HashMap<String, Object>>)list.get(position).get("children");
        	        String str = (String)list.get(position).get("title");
        	        fixturesFr.showNextFragment(selectedFixtures, str);
                }
                else
                {
                	fixturesFr.showDetailFragment(complist);
                }
                
        	}
        });
        
        
        return view;
    }
    
    private class FixturesOnClickListener implements View.OnClickListener
    {
    	public int position;
    	public FixturesOnClickListener(int i)
    	{
    		this.position = i;
    	}
    	
    	@Override
    	public void onClick(View v) {
						
		}
    }
    
  
}

