package com.footballwest.football;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class SplashActivity extends Activity{
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);

       overridePendingTransition(R.anim.abc_fade_out, R.anim.abc_fade_in);
        
        new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent i = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(i);
			}
		}, 3000);
        
    }

}
