package com.footballwest.football;


import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class NewsDetailFragment extends Fragment {

	private WebView contentView;
	private String url;
	
	public NewsDetailFragment(String urlLink)
	{
		url = urlLink;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		

        View view = inflater.inflate(R.layout.news_detail_layout, container, false);
        
        MainActivity activity = (MainActivity)getActivity();
    	activity.getSupportActionBar().setTitle("News");
    	activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
    	
        contentView = (WebView) view.findViewById(R.id.web_news_detail);
        contentView.getSettings().setJavaScriptEnabled(true);
        contentView.loadUrl(url);

        return view;
    }
	
}
