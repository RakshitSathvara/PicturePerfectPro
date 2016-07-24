package com.example.pictureperfectpro;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class ExploredImageSwipeView extends Activity {
	
	TextView lbl_address;
	
	ImageView img_exploredImage;
	

	
	com.example.pictureperfectpro.ImageLoader imageLoader;	
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_explored_image_swipe_view);
		
		lbl_address=(TextView) findViewById(R.id.lbl_ExploredPhotoTitle);
		
		
		lbl_address.setText(getIntent().getStringExtra("address"));
		
		img_exploredImage=(ImageView) findViewById(R.id.img_exploredImage);
		
		imageLoader=new com.example.pictureperfectpro.ImageLoader(this);
		
		imageLoader.DisplayImage("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+getIntent().getStringExtra("url")+"&key=AIzaSyC4cIubZFDzBbWsoeBNVqhoXlXLBZVXBDw", img_exploredImage);
		
	}


}



