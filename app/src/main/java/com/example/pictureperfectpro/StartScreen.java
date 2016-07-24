package com.example.pictureperfectpro;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class StartScreen extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_start_screen);
		
		
		
         
		
	}
	
	
	@Override
	public void onBackPressed()
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	    
		alertDialogBuilder.setTitle("Alert");
 
		alertDialogBuilder
				.setMessage("Exit Picture Perfect Pro?")
				.setCancelable(false)
				.setPositiveButton("Exit",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						
					StartScreen.this.finish();
						
					}
					
				
				  })
				  .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							
							dialog.cancel();
								
							}
							
						
						  });   
		
		AlertDialog alertDialog = alertDialogBuilder.create();
		 
		alertDialog.show();  
		
		
	}
	
	
	public void showSignUp(View v)
	{
		Intent i=new Intent(getBaseContext(),CreateAccount.class);
        startActivity(i);		
	}
	
	public void showLogin(View v)
	{
		Intent i=new Intent(getBaseContext(),Login.class);
        startActivity(i);
	}
	

}
