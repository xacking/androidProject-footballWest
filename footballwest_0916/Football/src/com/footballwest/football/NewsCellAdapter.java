package com.footballwest.football;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class NewsCellAdapter extends ArrayAdapter<HashMap<String, String>> {
	
	private final Activity context;
	private final ArrayList<HashMap<String, String>> list;
	private NewsFragment newsFr;
	
	public NewsCellAdapter(Activity context, ArrayList<HashMap<String, String>> list)
	{
		super(context, R.layout.news_list_row, list);
		this.context = context;
		this.list = list;
		
	}
	
	public void setFragment(NewsFragment fr)
	{
		newsFr = fr;
	}
	
	
	@Override
	public View getView(int position, final View convertView, ViewGroup parent)
	{
		
        LayoutInflater inflator = context.getLayoutInflater();
        final View view = inflator.inflate(R.layout.news_list_row, null);
        
        TextView textTitle = (TextView) view.findViewById(R.id.news_cell_title);
        TextView textDate = (TextView) view.findViewById(R.id.news_cell_date);
        TextView textContent = (TextView) view.findViewById(R.id.news_cell_content);
        
 
   
        textTitle.setText(list.get(position).get("title"));
        textDate.setText(list.get(position).get("pubDate"));
        textContent.setText(list.get(position).get("description"));
        
        view.setOnClickListener(new NewsOnClickListener(position){
        	
        	public void onClick(View v)
        	{
        		for (int i = 0; i < newsFr.contentList.getChildCount(); i++) {
        			newsFr.contentList.getChildAt(i).setBackgroundResource(R.drawable.cellfw);
                }
        		view.setBackgroundResource(R.drawable.cellfw_selected);
            	String strURL = list.get(position).get("link");
            	newsFr.showDetailFragment(strURL);
        	}
        });
        
        return view;
	}
	
	private class NewsOnClickListener implements View.OnClickListener
    {
    	public int position;
    	public NewsOnClickListener(int i)
    	{
    		this.position = i;
    	}
    	
    	@Override
    	public void onClick(View v) {
						
		}
    }


}


