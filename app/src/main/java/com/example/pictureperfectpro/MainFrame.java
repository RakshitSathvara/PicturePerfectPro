package com.example.pictureperfectpro;

import java.io.File;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.Toast;

public class MainFrame extends FragmentActivity implements ActionBar.TabListener {
	
	ActionBar actionBar;
	
	ViewPager viewPager;
	
	String data;
	
	FragmentPageAdapter ft;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String result;
		try
		{
			result=new PPP(this).execute("1",getIntent().getStringExtra("uid"),getIntent().getStringExtra("pwd")).get();
			data=result;
		}
		catch (Exception e)
		{
			
			e.printStackTrace();
		}
		
		
		setContentView(R.layout.activity_main_frame);
		
		
		viewPager=(ViewPager) findViewById(R.id.pager);
		
		
		
		
		ft=new FragmentPageAdapter(getSupportFragmentManager(),data);
		
		
		viewPager.setAdapter(ft);
	
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				actionBar.setSelectedNavigationItem(arg0);
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		actionBar=getActionBar();
		
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		actionBar.addTab(actionBar.newTab().setText("ME").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("FEEDS").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("COLLECTIONS").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("EXIF").setTabListener(this));
		
	}
	
	
	
		   
	   
	   @Override
	public void onBackPressed()
	{

		   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		    
			alertDialogBuilder.setTitle("Alert");
	 
			alertDialogBuilder
					.setMessage("Are you sure you want to Logout?")
					.setCancelable(false)
					.setPositiveButton("Logout",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							
						MainFrame.this.finish();
							
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


	
	

	
	

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
		viewPager.setCurrentItem(tab.getPosition());
		
	}



	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}


	

}
