package com.footballwest.football;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import android.support.v4.app.Fragment;
import xmlwise.XmlParseException;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class VenuesFragment extends Fragment {


	public ListView contentList;
	public ListView indexList;
    private VenuesCellAdapter contentAdapter;
    private VenuesIndexCellAdapter indexAdapter;
    private ProgressDialog mProgressDialog;
    private ArrayList<HashMap<String, String>> m_data;
    private ArrayList<HashMap<String, String>> m_indexData;

    
    private String strURL = "http://www.jurcevic.com/fw/VenueList.plist";
    
    public VenuesFragment()
    {
    	m_data = DBManager.shareDBManager().getVenues();
    	setIndexData(); 
    }
    
    public void setIndexData()
    {
    	m_indexData = new ArrayList<HashMap<String,String>>();
    	
    	for (int i = 0; i < m_data.size(); i++)
    	{
    		
    		HashMap<String, String> data = m_data.get(i);
    		String character = data.get("character");
    		
    		if (character != null && character.length() > 0)
    		{
    			HashMap<String, String> indexData = new HashMap<String, String>();
    			indexData.put("character", (String) character);
    			indexData.put("position", String.valueOf(i));
    			m_indexData.add(indexData);    			
    		}
    		
    	}
    	
    	
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
    	View view = inflater.inflate(R.layout.venues_fragment_layout, container, false);
        contentList = (ListView) view.findViewById(R.id.list_venues);
        indexList = (ListView) view.findViewById(R.id.list_venues_index);
        
        MainActivity activity = (MainActivity)getActivity();
    	
    	activity.getSupportActionBar().setTitle("Venues");
    	activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha);
    	
    	if(m_data == null || m_data.size() == 0) 
        {
        	(new Title()).execute();
        }
        else
        {
        	showData();
        	(new Title()).execute();
        }
        
        
        return view;
    }

    private void showData()
    {
    	contentAdapter = new VenuesCellAdapter(getActivity(), m_data);
    	contentAdapter.sendFragment(this);
        contentList.setAdapter(contentAdapter);

        indexAdapter = new VenuesIndexCellAdapter(getActivity(), m_indexData);
        indexAdapter.sendFragment(this);
        indexList.setAdapter(indexAdapter);
        
    }

  
    private class Title extends AsyncTask<Void, Void, Void> {

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
				sourceUrl = new URL(strURL);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				this.cancel(true);
				return null;
			} 

			BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(sourceUrl.openStream()));
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} 

			String inputLine;
	
			try {
				while ((inputLine = in.readLine()) != null){
				     fileData.append(inputLine);
				 }
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}

			 try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}


			 HashMap<?, ?> hashMap = null;
			 try {
				hashMap = (HashMap<?, ?>)Plist.objectFromXml(fileData.toString());
			} catch (XmlParseException e) {
				e.printStackTrace();
				return null;
			}
			 
			 Set<?> charSetList = hashMap.keySet();
			 Object tmpObject[] = charSetList.toArray();
			 
			 Arrays.sort(tmpObject);
			 
			 ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();


			 for(int i = 0; i < tmpObject.length; i ++)
			 {					 
				 ArrayList<?>childs = (ArrayList<?>)hashMap.get(tmpObject[i]);
				 
 				 for(int j = 0; j < childs.size(); j ++)
				 {
					 HashMap<String, String> child = (HashMap<String, String>) childs.get(j);
					 if (j == 0)
					 {
						 child.put("character", (String) tmpObject[i]);

					 }
					 data.add(child);//================sending data
					 
//					 "name", "address", "suburb", "postcode", "latitude", "longitude", "character"
				 }
			 }
			
			 if (data.size() > 0)
				 DBManager.shareDBManager().saveVenues(data);
			 
			 data = DBManager.shareDBManager().getVenues();
			 if (data != null && !(data.isEmpty()))
			 {
				 m_data = data;
				 setIndexData();
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