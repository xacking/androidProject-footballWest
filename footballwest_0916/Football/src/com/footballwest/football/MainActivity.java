package com.footballwest.football;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;



public class MainActivity extends ActionBarActivity {

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private LeftCellAdapter mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
  

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_fixtures_layout);
        
        Global.g_context = this;
        
        selectFrag(0);
        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        
        View drawerView = (View)findViewById(R.id.drawer_view);
        drawerView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				return;
			}
		});
        
        
        mActivityTitle = Global.leftListArray[0];

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(mActivityTitle);
        
    }
        
    private void addDrawerItems() {

       
        mAdapter = new LeftCellAdapter(this,Global.leftListArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	
            	for (int i = 0; i < mDrawerList.getChildCount(); i++) {
                    if (position == i) {
                        mDrawerList.getChildAt(i).setBackgroundResource(R.drawable.cellfw_selected);
                    } else {
                        mDrawerList.getChildAt(i).setBackgroundResource(R.drawable.cellfw);
                    }
                }
                
                
                selectFrag(position);
                
                mDrawerLayout.closeDrawers();
                
            }
            
        });
        
        
    }
    
    
    public void selectFrag(int position) {
    	
    
        Fragment fr = null;
        FragmentManager fm = getSupportFragmentManager(); 
        FragmentTransaction ft = fm.beginTransaction();
        
        // clear stack
        int count = fm.getBackStackEntryCount();
        while(count > 1)
        {
        	fm.popBackStack();
        	count--;
        }
        
        switch (position) {
		case 0:
			mActivityTitle = Global.leftListArray[position];
			
			//fr = new FixturesFragment(Global.g_fixtures, mActivityTitle);
			fr = new FixturesFragment(mActivityTitle);
			
			ft.replace(R.id.fragment_fixtures_content, fr);
		
			break;
		case 1:
			mActivityTitle = Global.leftListArray[position];
			
			fr = new NewsFragment();
			
			ft.replace(R.id.fragment_fixtures_content, fr);
			
			break;
		case 2:
			mActivityTitle = Global.leftListArray[position];
			
			fr = new VenuesFragment();
			ft.replace(R.id.fragment_fixtures_content, fr);
			break;
		case 3:
			mActivityTitle = Global.leftListArray[position];
			
			fr = new DirectoryFragment();
			ft.replace(R.id.fragment_fixtures_content, fr);
			break;

		default:
			//fr = new FixturesFragment(Global.g_fixtures, mActivityTitle);
			break;
		}

        getSupportActionBar().setTitle(mActivityTitle);
        Global.g_nDeep = 0;
        ft.commit();
    }

    private void setupDrawer() {
    	
    	mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            ///* Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
       
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // if process is loading...
        if (Global.g_bLoading)
        {
        	return true;
        }
        
        //Ladder
        if (id == R.id.action_ladder)
        {
        	if (item.getTitle().equals("Ladder"))
        			showLadderFragment();
        	else if (item.getTitle().equals("Done"))
        	{
        		Global.g_nDeep -= 1;
        		getSupportFragmentManager().popBackStack();
        	}
            return true;
        }
        
        if (Global.g_nDeep > 0)
        {
        	FragmentManager fm = getSupportFragmentManager();
        	fm.popBackStack();
        	Global.g_nDeep -= 1;
        	return true;
        }
        
        
        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    public void showLadderFragment()
    {
    	Global.g_nDeep += 1;
    	
        Fragment fr = new LadderFragment();
        
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        
        fragmentTransaction.replace(R.id.fragment_fixtures_content, fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    
    public void onPhone(View v)
    {
    	TextView view = (TextView)v;
    	final String strPhoneNumber = (String)view.getText();
		if (strPhoneNumber.isEmpty())
			return;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	AlertDialog alertDialog = builder.create();
    	alertDialog.setTitle("Really call " +strPhoneNumber+"?");
        alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
        	public void onClick(DialogInterface dialog, final int which) {
        		String uri = "tel:"+strPhoneNumber;
        		Intent callIntent = new Intent(Intent.ACTION_CALL);
        		callIntent.setData(Uri.parse(uri));
        		startActivity(callIntent);
        	}
        });
        alertDialog.setButton2("No", new DialogInterface.OnClickListener() {
        	public void onClick(DialogInterface dialog, int which) {
        		// here you can add functions
        		
        	}
        });        
        
        alertDialog.show();
    }
    
    public void onFax(View v)
    {
    	TextView view = (TextView)v;
    	final String strFax = (String)view.getText();
    	if (strFax.isEmpty())
    		return;
    	
    	// fax
    }
    
    public void onEmail(View v)
    {
    	TextView view = (TextView)v;
    	final String strEmail = (String)view.getText();
    	if (strEmail.isEmpty())
    		return;
    	
    	Intent emailIntent = new Intent(Intent.ACTION_SEND);
    	emailIntent.setType("plain/text");

    	  emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
    	          new String[] { "abc@gmail.com" });

    	  emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
    	          "Email Subject");

    	  emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
    	          "Email Body");

    	  startActivity(Intent.createChooser(
    	          emailIntent, "Send mail..."));
    
    }
    
    public void onWebURL(View v)
    {
    	TextView view = (TextView)v;
    	final String strWebURL = (String)view.getText();
		if (strWebURL.isEmpty())
			return;
		
		Uri uri = Uri.parse(strWebURL);
		Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(launchBrowser);
		
    }
    
    
    public void onGoogleURL(View v)
    {
    	TextView view = (TextView)v;
    	final String strGoogleURL = (String)view.getText();
		if (strGoogleURL.isEmpty())
			return;
		Uri uri = Uri.parse(strGoogleURL);
		Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(launchBrowser);
		
		
    }
    
    
}

