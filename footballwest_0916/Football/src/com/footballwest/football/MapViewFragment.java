package com.footballwest.football;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MapViewFragment extends Fragment{
	
	private WebView contentView;
	
	private String strURL;
	private String strTitle;
	
	public MapViewFragment(String url, String title)
	{
		strURL = url;
		strTitle = title;
		if (title == null || title.equals(""))
			strTitle = "Back";
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
    	View view = inflater.inflate(R.layout.mapview_layout, container, false);
        contentView = (WebView) view.findViewById(R.id.content_webview);

        MainActivity activity = (MainActivity)getActivity();
    	
    	activity.getSupportActionBar().setTitle(strTitle);
    	activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
    	
    	
    	
        contentView.getSettings().setJavaScriptEnabled(true);
        contentView.setWebViewClient(new WebViewClient());
        contentView.loadUrl(strURL);
    	
    	
        return view;
    }

}
