package com.footballwest.football;


import java.util.ArrayList;
import java.util.HashMap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import xmlwise.XmlParseException;
/**
 * A simple {@link Fragment} subclass.
 */
public class FixturesFragment extends Fragment{

	
    public ListView contentList;
    private FixturesCellAdapter contentAdapter;
    private ProgressDialog mProgressDialog;
    private ArrayList<HashMap<String, Object>> m_data;
    private String m_title;
    private boolean m_bFirstPage;
    
    public FixturesFragment(ArrayList<HashMap<String, Object>> fixtures, String title) {
        // Required empty public constructor
    	m_data = fixtures;
    	m_title = title;
    	m_bFirstPage = false;
    }

    public FixturesFragment(String title) {
        // Required empty public constructor
    	m_data = DBManager.shareDBManager().getFixtures();
    	m_title = title;
    	m_bFirstPage = true;
    }

    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fixtures_layout, container, false);
        contentList = (ListView) view.findViewById(R.id.list_fixtures);
        
        MainActivity activity = (MainActivity)getActivity();
    	activity.getSupportActionBar().setTitle(m_title);
    	if (!m_title.equals("Fixtures"))
    		activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
    	else
    	{
    		activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha);
    		
    	}
        if(m_data == null || m_data.size() == 0)
        {
        	(new Title()).execute();
        }
        else
        {
        	showData();
        	if (m_bFirstPage)
        		(new Title()).execute();
        }
       
       
        return view;

    }
    
    private void showData()
    {
    	contentAdapter = new FixturesCellAdapter(getActivity(), m_data);
    	contentAdapter.setFragment(this);
        contentList.setAdapter(contentAdapter);
       
    }

    public void showNextFragment(ArrayList<HashMap<String, Object>> fixtures, String title) {
       
    	Global.g_nDeep += 1;
    	
        Fragment fr = new FixturesFragment(fixtures, title);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        
        fragmentTransaction.replace(R.id.fragment_fixtures_content, fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
       
        
       
    }
    
    public void showDetailFragment(String complist)
    {
    	Global.g_nDeep += 1;
    	FixturesFragmentDetail fr = new FixturesFragmentDetail(complist);
        
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        
        fragmentTransaction.replace(R.id.fragment_fixtures_content, fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    

    
    
    private class Title extends AsyncTask<Void, Void, Void> {
		
		
		String url = "http://www.jurcevic.com/fw/FixtureList.plist";
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			if(m_data == null || m_data.size() == 0)
			{
				mProgressDialog = new ProgressDialog(getActivity());
				mProgressDialog.setMessage("Loading...");
				mProgressDialog.setIndeterminate(false);
				mProgressDialog.show();
			}
			
			Global.g_bLoading = true;
		}
 
		@Override
		protected Void doInBackground(Void... params) {
			
			StringBuffer fileData = new StringBuffer(1024);
		     
			 URL sourceUrl = null;
			 try {
				 sourceUrl = new URL(url);
			 } catch (MalformedURLException e) {
				 e.printStackTrace();
				 return null;
			 } 
		
			 /** BufferReader will read the file content. **/
			 BufferedReader in = null;
			 try {
				 in = new BufferedReader(new InputStreamReader(sourceUrl.openStream()));
			 } catch (IOException e) {
				 e.printStackTrace();
				 return null;
			 } 
		
			 String inputLine;
		
			 /** Read the BufferReader line by line and append into "inputLine" String **/
		     try {
		    	 while ((inputLine = in.readLine()) != null){
		    		 fileData.append(inputLine);
		    	 }
		     } catch (IOException e) {
		    	 // TODO Auto-generated catch block
		    	 e.printStackTrace();
		    	 return null;
		     }
		
		     /** Close the BufferReader connection **/
		     try {
		    	 in.close();
		     } catch (IOException e) {
		    	 // TODO Auto-generated catch block
		    	 e.printStackTrace();
		    	 return null;
		     }
		
			 /** Parse the "inputLine" String into HashMap **/
			 /** HashMap is known as NSDictionary in IOS development **/
			 HashMap<Object, Object> hashMap = null;
			 try {
				 hashMap = (HashMap<Object, Object>) Plist.objectFromXml(fileData.toString());
			 } catch (XmlParseException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
				 return null;
			 }
		
			 /** The HashMap should contain an ArrayList, you can get the Array by the "Key" **/
			 
			 
			 ArrayList<HashMap<String, Object>> data = (ArrayList<HashMap<String, Object>>) hashMap.get("fixtures");
			 
			 if(data.size() > 0)
				 DBManager.shareDBManager().saveFixtures(data);
			 
			 data = DBManager.shareDBManager().getFixtures();
			 if(data.size() > 0)
			 {
				 m_data = data;
			 }

			 return null;
		}
 
		@Override
		protected void onPostExecute(Void result) {
			
			if (mProgressDialog != null)
				mProgressDialog.dismiss();
			showData();
			
			Global.g_bLoading = false;
			
		}
		
		
		
	}
    
  
}
