package com.footballwest.football;

import java.util.ArrayList;
import java.util.HashMap;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class LadderFragment extends Fragment
{
	public ListView contentList;
    private LadderCellAdapter contentAdapter;
    private ArrayList<HashMap<String, String>> m_data;
    
	public LadderFragment()
	{
		m_data = Global.g_ladderData;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
    	View view = inflater.inflate(R.layout.ladder_fragment_layout, container, false);
        contentList = (ListView) view.findViewById(R.id.list_ladder);

        MainActivity activity = (MainActivity)getActivity();
    	
    	activity.getSupportActionBar().setTitle("Ladder");
    	activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    	activity.getSupportActionBar().setHomeButtonEnabled(false);
    	
    	contentAdapter = new LadderCellAdapter(getActivity(), m_data);
    	contentAdapter.setFragment(this);
        contentList.setAdapter(contentAdapter);
        
        setHasOptionsMenu(true);
        
        return view;
    }
	
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.main, menu);
        MenuItem itemLadder = (MenuItem)menu.getItem(0);
        itemLadder.setTitle("Done");
        
        super.onCreateOptionsMenu(menu, inflater);
    }
	
	

}
