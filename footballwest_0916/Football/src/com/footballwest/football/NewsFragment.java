package com.footballwest.football;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
//import android.app.Fragment;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class NewsFragment extends Fragment 
{
	public ListView contentList;
	private NewsCellAdapter contentAdapter;
	private ProgressDialog mProgressDialog;
	private ArrayList<HashMap<String, String>> m_data = null;
	

	
	public NewsFragment()
	{
		m_data = DBManager.shareDBManager().getNews();
	}
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        contentList = (ListView) view.findViewById(R.id.list_news);
        
        MainActivity activity = (MainActivity)getActivity();
    	activity.getSupportActionBar().setTitle("News");
    	activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha);
        if (m_data == null || m_data.size() == 0)
        	(new Title()).execute();
        else
        {
        	showData();
        	(new Title()).execute();
        }
        return view;

    }
	
	private void showData()
	{
		contentAdapter = new NewsCellAdapter(getActivity(), m_data);
        contentAdapter.setFragment(this);
		contentList.setAdapter(contentAdapter);
        
	}
	
	
    public void showDetailFragment(String url) {
        
    	Global.g_nDeep += 1;

    	Fragment fr = new NewsDetailFragment(url);
        
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_fixtures_content, fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
	

	
	
	private class Title extends AsyncTask<Void, Void, Void> {
		
		
		String url = "http://www.footballwest.com.au/index.php?id=2&type=100";
		
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
			try {
				// Connect to the web site
				Document document = Jsoup.connect(url).get();
				
				///=========================================================================================
				ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
				Elements links = document.getElementsByTag("item");
				
				for (org.jsoup.nodes.Element element : links) {
					
					HashMap<String, String> row = new HashMap<String, String>();
					
					String title = element.getElementsByTag("title").first().text();						
					row.put("title", title);
					
					String link = element.getElementsByTag("link").first().text();
					row.put("link", link);
					
					String pubDate = element.getElementsByTag("pubDate").first().text();
					row.put("pubDate", pubDate);
					
					String description = element.getElementsByTag("description").first().text();
					row.put("description", description);
					
					data.add(row);
				}
				
				if(data.size() > 0)
					DBManager.shareDBManager().saveNews(data);
				data = DBManager.shareDBManager().getNews();
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
		protected void onPostExecute(Void result) 
		{
			
			if (mProgressDialog != null)
				mProgressDialog.dismiss();
			showData();
			Global.g_bLoading = false;
		}
	}
	
}
