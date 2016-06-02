package com.footballwest.football;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FixturesFragmentDetail extends Fragment implements ActionSheet.ActionSheetListener{


	public ListView contentList;
    private FixturesDetailCellAdapter contentAdapter;
    private ProgressDialog mProgressDialog;
    private ArrayList<HashMap<String, String>> m_data;
    private int nSelectedIndex;
    
    private String strFixtureURL = "http://119.252.88.88/fbtw/compfixture?cm_id=";
    private String strLadderURL = "http://119.252.88.88/fbtw/compladder?cm_id=";
    
    public FixturesFragmentDetail(String complist) {
        // Required empty public constructor
    	strFixtureURL += complist;
    	strLadderURL += complist;
    	nSelectedIndex = 0;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
    	View view = inflater.inflate(R.layout.fragment_fixtures_detail_layout, container, false);
        contentList = (ListView) view.findViewById(R.id.list_fixtures_detail);

        MainActivity activity = (MainActivity)getActivity();
    	
    	activity.getSupportActionBar().setTitle("Back");
    	activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
    	
    	
    	activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    	activity.getSupportActionBar().setHomeButtonEnabled(true);
    	
        (new Title()).execute();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.main, menu);
        
        super.onCreateOptionsMenu(menu, inflater);
    }

    

    private void showData()
    {
    	contentAdapter = new FixturesDetailCellAdapter(getActivity(), m_data);
    	contentAdapter.setFragment(this);
        contentList.setAdapter(contentAdapter);
        //contentList.setOnItemClickListener(this);
    }
    
    
    public void showActionSheet(int index)
    {
    	//=====actionsheet
    	nSelectedIndex = index;
    	ActionSheet.createBuilder(this.getActivity(), getFragmentManager()).setCancelButtonTitle("Cancel").setOtherButtonTitles("Display Location", "Save to Calendar").setCancelableOnTouchOutside(true).setListener(this).show();
        
    }
    
    
    @Override
	public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
		
    	
	}
	@Override
	public void onOtherButtonClick(ActionSheet actionSheet, int index) {
		
		if (index == 0)
		{
			HashMap<String, String> data = m_data.get(nSelectedIndex);
			
			String strURL = "http://maps.apple.com/maps?f=g&h1=em&q="; 
			String param = "&ie=UTF8&iwloc=addr&om=1";
			String latutude = (String) data.get("VenueLat");
			String longitude = (String) data.get("VenueLong");
			strURL += (latutude + "," + longitude + param);
			
			Global.g_nDeep += 1;
	    	
	        Fragment fr = new MapViewFragment(strURL, null);
	        
	        FragmentManager fm = getFragmentManager();
	        FragmentTransaction fragmentTransaction = fm.beginTransaction();
	        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
	        
	        fragmentTransaction.replace(R.id.fragment_fixtures_content, fr);
	        fragmentTransaction.addToBackStack(null);
	        fragmentTransaction.commit();
			
		}
		else if(index == 1)
		{
			Calendar cal = Calendar.getInstance();              
			Intent intent = new Intent(Intent.ACTION_EDIT);
			intent.setType("vnd.android.cursor.item/event");
			intent.putExtra("beginTime", cal.getTimeInMillis());
			intent.putExtra("allDay", true);
			intent.putExtra("rrule", "FREQ=YEARLY");
			intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
			intent.putExtra("title", "A Test Event from android app");
			startActivity(intent);
		}
		
	}

	
	
    private class Title extends AsyncTask<Void, Void, Void> {
		
		private boolean bFailed = false;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(getActivity());
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.show();
			Global.g_bLoading = true;
		}
 
		@Override
		protected Void doInBackground(Void... params) {
			
			try {
				// Connect to the web site
				Document document = Jsoup.connect(strFixtureURL).get();
				
				m_data = new ArrayList<HashMap<String, String>>();// sending data============================================
				Elements links = document.getElementsByTag("Match");
				
				for (org.jsoup.nodes.Element element : links) {
					
					HashMap<String, String> row = new HashMap<String, String>();
					
					String CompName = element.getElementsByTag("CompName").first().text();
					row.put("CompName", CompName);
					
					String Date = element.getElementsByTag("Date").first().text();						
					row.put("Date", Date);
					
					String HomeName = element.getElementsByTag("HomeName").first().text();
					row.put("HomeName", HomeName);
					
					String HomeScore = element.getElementsByTag("HomeScore").first().text();
					row.put("HomeScore", HomeScore);
					
					String AwayName = element.getElementsByTag("AwayName").first().text();
					row.put("AwayName", AwayName);
					
					String AwayScore = element.getElementsByTag("AwayScore").first().text();
					row.put("AwayScore", AwayScore);
					
					String VenueName = element.getElementsByTag("VenueName").first().text();
					row.put("VenueName", VenueName);
					
					String VenueLat = element.getElementsByTag("VenueLat").first().text();
					row.put("VenueLat", VenueLat);
					
					String VenueLong = element.getElementsByTag("VenueLong").first().text();
					row.put("VenueLong", VenueLong);
					
					m_data.add(row);
				}
			} catch (IOException e) {
				e.printStackTrace();
				bFailed = true;
				return null;
			}
			
			try {
				// Connect to the web site
				
				Document document = Jsoup.connect(strLadderURL).get();
				Global.g_ladderData = new ArrayList<HashMap<String, String>>();
				Elements links = document.getElementsByTag("Team");
				
				for (org.jsoup.nodes.Element element : links) {
					
					HashMap<String, String> row = new HashMap<String, String>();

					String TeamName = element.getElementsByTag("TeamName").first().text();						
					row.put("Team", TeamName);
					
					String Played = element.getElementsByTag("Played").first().text();
					row.put("P", Played);
					
					String Won = element.getElementsByTag("Won").first().text();
					row.put("W", Won);
					
					String Drawn = element.getElementsByTag("Drawn").first().text();
					row.put("D", Drawn);
					
					String Lost = element.getElementsByTag("Lost").first().text();
					row.put("L", Lost);
					
					String Value = element.getElementsByTag("Value").first().text();
					row.put("Pts", Value);
					
					Global.g_ladderData.add(row);
				}
			} catch (IOException e) {
				e.printStackTrace();
				bFailed = true;
				return null;
			}
			
			return null;
		}
 
		@Override
		protected void onPostExecute(Void result) {
			
			mProgressDialog.dismiss();
			
			if(bFailed == true)
			{
				
			}
			else
			{
				setHasOptionsMenu(true);
				showData();
			}
			Global.g_bLoading = false;
		}
	}
    
}