package com.footballwest.football;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class DirectoryFragment extends Fragment {


	public ListView contentList;
    private DirectoryCellAdapter contentAdapter;
    private ProgressDialog mProgressDialog;
    private ArrayList<HashMap<String, String>> m_data;
    
    private String strURL = "http://coast260.anchor.net.au/cpftmb/clubinfo?club_id=23";


    public DirectoryFragment() {
        // Required empty public constructor
    	m_data = DBManager.shareDBManager().getBusiness();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
    	View view = inflater.inflate(R.layout.directory_fragment_layout, container, false);
        contentList = (ListView) view.findViewById(R.id.list_directory);

        MainActivity activity = (MainActivity)getActivity();
    	
    	activity.getSupportActionBar().setTitle("Directory");
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
    	contentAdapter = new DirectoryCellAdapter(getActivity(), m_data);
    	contentAdapter.sendFragment(this);
        contentList.setAdapter(contentAdapter);
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
		
			// Connect to the web site
			try{
				
				ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
				
				Document document = Jsoup.connect(strURL).get();
				
				Elements links = document.getElementsByTag("club_bus_dir");

				for (org.jsoup.nodes.Element element : links) 
				{
					HashMap<String, String> row = new HashMap<String, String>();
					
					String bus_category_name = element.getElementsByTag("bus_category_name").first().text();						
					row.put("bus_category_name", bus_category_name);
					
					String name = element.getElementsByTag("name").first().text();
					row.put("name", name);
					
					String detail = element.getElementsByTag("detail").first().text();
					row.put("detail", detail);
					
					String address = element.getElementsByTag("address").first().text();
					row.put("address", address);
					
					String city = element.getElementsByTag("city").first().text();
					row.put("city", city);
					
					String state = element.getElementsByTag("state").first().text();
					row.put("state", state);
					
					String country = element.getElementsByTag("country").first().text();
					row.put("country", country);
					
					String postcode = element.getElementsByTag("postcode").first().text();
					row.put("postcode", postcode);
					
					String phone = element.getElementsByTag("phone").first().text();
					row.put("phone", phone);
					
					String fax = element.getElementsByTag("fax").first().text();
					row.put("fax", fax);
					
					String email = element.getElementsByTag("email").first().text();
					row.put("email", email);
					
					String web_url = element.getElementsByTag("web_url").first().text();
					row.put("web_url", web_url);
					
					String google_rul = element.getElementsByTag("google_url").first().text();
					row.put("google_rul", google_rul);
					
					String sponsor_text = element.getElementsByTag("sponsor_text").first().text();
					row.put("sponsor_text", sponsor_text);
					
					String moresponsorurl = element.getElementsByTag("moresponsorurl").first().text();
					row.put("moresponsorurl", moresponsorurl);
					
					String sponsor_img_url = element.getElementsByTag("sponsor_img_url").first().text();
					row.put("sponsor_img_url", sponsor_img_url);
					
					// bus_category_name,name,detail,address,city,state,country,postcode,phone,fax,email,web_url,
					//google_url,sponsor_text,moresponsorurl,sponsor_img_url
					
					data.add(row);
					
				}
				
				if (data.size() > 0)
					DBManager.shareDBManager().saveBusiness(data);
				
				data = DBManager.shareDBManager().getBusiness();
				if (data != null && !(data.isEmpty()))
				{
					m_data = data;
				}
				
				
			} catch (IOException e) {
				e.printStackTrace();
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