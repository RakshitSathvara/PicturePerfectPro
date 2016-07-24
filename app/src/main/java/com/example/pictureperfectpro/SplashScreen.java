package com.example.pictureperfectpro;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class SplashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		Thread background = new Thread() {
        public void run() {
             
            try {
                // Thread will sleep for 5 seconds
                sleep(1500);
                 
                // After 5 seconds redirect to another intent
                Intent i=new Intent(getBaseContext(),StartScreen.class);
                startActivity(i);
                 
                //Remove activity
                finish();
                 
            } catch (Exception e) {
             
            }
        }
    };
     
    // start thread
    background.start();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


}
