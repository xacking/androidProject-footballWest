package com.footballwest.football;

import java.util.ArrayList;
import java.util.HashMap;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class BusinessFragment extends Fragment{

	public ListView contentList;
    private BusinessCellAdapter contentAdapter;
    private ArrayList<HashMap<String, String>> m_data;
	
	public BusinessFragment(ArrayList<HashMap<String, String>> data)
	{
		m_data = data;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
    	View view = inflater.inflate(R.layout.directory_fragment_layout, container, false);
        contentList = (ListView) view.findViewById(R.id.list_directory);

        MainActivity activity = (MainActivity)getActivity();
    	
    	activity.getSupportActionBar().setTitle("Business");
    	activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
    	
    	contentAdapter = new BusinessCellAdapter(getActivity(), m_data);
    	contentAdapter.sendFragment(this);
        contentList.setAdapter(contentAdapter);
        
        return view;
    }
	
}
