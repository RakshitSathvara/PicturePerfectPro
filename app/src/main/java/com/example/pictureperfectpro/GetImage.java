package com.example.pictureperfectpro;

import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;


public class GetImage extends AsyncTask<String, Void, Bitmap> {


		Bitmap bmp=null;
		
		@Override
		protected Bitmap doInBackground(String... params)
		{
			try
			{
				URL url = new URL(params[0]);
				bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
				
			}
			catch(Exception e)
			{
				
			}
			return bmp;
		}

}
