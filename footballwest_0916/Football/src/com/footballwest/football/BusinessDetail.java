package com.footballwest.football;

import java.util.HashMap;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BusinessDetail extends Fragment {
	
	private HashMap<String, String> m_data;
	private TextView txtName;
	private TextView txtDetails;
	private TextView txtAddress;
	private TextView txtCity;
	private TextView txtState;
	private TextView txtCountry;
	private TextView txtPhone;
	private TextView txtFax;
	private TextView txtEmail;
	private TextView txtWebURL;
	private TextView txtGoogleURL;
	
	public BusinessDetail(HashMap<String, String> data)
	{
		m_data = data;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    {
    	View view = inflater.inflate(R.layout.business_detail_layout, container, false);

        MainActivity activity = (MainActivity)getActivity();
    	
    	activity.getSupportActionBar().setTitle("Business Details");
    	activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
    	
    	txtName = (TextView)view.findViewById(R.id.business_detail_name);
    	txtDetails = (TextView)view.findViewById(R.id.business_detail_detail);
    	txtAddress = (TextView)view.findViewById(R.id.business_detail_address);
    	txtCity = (TextView)view.findViewById(R.id.business_detail_city);
    	txtState = (TextView)view.findViewById(R.id.business_detail_state);
    	txtCountry = (TextView)view.findViewById(R.id.business_detail_country);
    	txtPhone = (TextView)view.findViewById(R.id.business_detail_phone);
    	txtFax = (TextView)view.findViewById(R.id.business_detail_fax);
    	txtEmail = (TextView)view.findViewById(R.id.business_detail_email);
    	txtWebURL = (TextView)view.findViewById(R.id.business_detail_weburl);
    	txtGoogleURL = (TextView)view.findViewById(R.id.business_detail_googleurl);
    	
    	showData();
        
        return view;
    }
	
	public void showData()
	{
		txtName.setText((String)m_data.get("name"));
		txtDetails.setText((String)m_data.get("detail"));
		txtAddress.setText((String)m_data.get("address"));
		txtCity.setText((String)m_data.get("city"));
		txtState.setText((String)m_data.get("state"));
		txtCountry.setText((String)m_data.get("country"));
		
		txtPhone.setText((String)m_data.get("phone"));
		txtFax.setText((String)m_data.get("fax"));
		txtEmail.setText((String)m_data.get("email"));
		txtWebURL.setText((String)m_data.get("web_url"));
		txtGoogleURL.setText((String)m_data.get("google_rul"));
		
//		txtPhone.setText("123456");
//		txtFax.setText("FDSFSFS");
//		txtEmail.setText("TEST");
//		txtWebURL.setText("http://www.baidu.com/");
//		txtGoogleURL.setText("http://www.baidu.com/");
		
		
		
		
	}
	
	
	

	
}
