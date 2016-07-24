package com.example.pictureperfectpro;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class ShowExplored extends Activity implements AdapterView.OnItemClickListener {
	
	String result,lat,lng,sesToken;
	
	GridView gridNearByPhotos;
	
	ArrayList<NearByPlaces> nbp=new ArrayList<NearByPlaces>();
	
	ArrayList<String> src=new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_show_explored);
		
		
		/////////////////////////////////// UNIVERSAL IMAGE LOADER SETUP
		
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
		.cacheOnDisc(true).cacheInMemory(true)
		.imageScaleType(ImageScaleType.EXACTLY)
		.displayer(new FadeInBitmapDisplayer(300)).build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.defaultDisplayImageOptions(defaultOptions)
				.memoryCache(new WeakMemoryCache()).build();

		ImageLoader.getInstance().init(config);
		
		/////////////////////////////////// END - UNIVERSAL IMAGE LOADER SETUP
		
		
		
		gridNearByPhotos=(GridView) findViewById(R.id.gridView_nearByPhotos);
		
		gridNearByPhotos.setOnItemClickListener(this);		
		
		
		GPSTracker gpsTracker=new GPSTracker(this);
		
		if(gpsTracker.canGetLocation())
		{
		
			lat=String.valueOf(gpsTracker.latitude);
			lng=String.valueOf(gpsTracker.longitude);
			
			
			try
			{
				result=new PPP(this).execute("8",lat,lng).get();
				JSONObject jsonResponse = new JSONObject(result);
		        JSONArray arrOfNBP = jsonResponse.getJSONArray("nbp");
		        
		        for(int i=0;i<arrOfNBP.length();i++){
		            JSONObject nbpJSON = arrOfNBP.getJSONObject(i);
		            NearByPlaces nbpObj = new NearByPlaces(nbpJSON);
			        nbp.add(nbpObj);	
			        
			        src.clear();
			        
			        for(int j=0;j<nbp.size();j++)
				    {
				    	
				    	src.add(nbp.get(j).getPhoto_url());
				    }
			        

		        }
		        
			}
			catch (Exception e)
			{
				Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
			}
			
			
			
			photoGridAdapter adapter=new photoGridAdapter(this, src);
			
			gridNearByPhotos.setAdapter(adapter);
			
		}
		else
		{
			Toast.makeText(this, "cannot get location", Toast.LENGTH_LONG).show();
		}
		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_explored, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
		
		Intent intent=new Intent(this,ExploredImageSwipeView.class);
		intent.putExtra("address", nbp.get(arg2).getLocation());
		intent.putExtra("url", src.get(arg2));
		startActivity(intent);
		
	}

}


class ImageViewHolder
{
	ImageView imgItem;
	ImageViewHolder(View v)
	{
		imgItem=(ImageView) v.findViewById(R.id.nbp_image);
	}
}


class photoGridAdapter extends BaseAdapter
{
	
	
	Context context;
	
	ImageLoader imageLoader;
	
	ArrayList<String> src=new ArrayList<String>();
	
	public photoGridAdapter(Context context, ArrayList<String> src)
	{
		this.context=context;
		this.src=src;
		imageLoader=ImageLoader.getInstance();
	}

	@Override
	public int getCount()
	{
		return src.size();
	}

	@Override
	public Object getItem(int arg0)
	{
		return src.get(arg0);
	}

	@Override
	public long getItemId(int arg0) 
	{
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2)
	{
		View v=arg1;
		ImageViewHolder holder=null;
		
		if(v==null)
		{
			LayoutInflater inflator=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v=inflator.inflate(R.layout.near_by_photo_item, arg2,false);
			holder=new ImageViewHolder(v);
			v.setTag(holder);
			
		
		}
		else
		{
			holder=(ImageViewHolder) v.getTag();
		}
		
		
		try
		{
			DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
					.cacheOnDisc(true).resetViewBeforeLoading(true).build();
			
			imageLoader.displayImage("https://maps.googleapis.com/maps/api/place/photo?maxwidth=200&photoreference="+src.get(arg0)+"&key=AIzaSyC4cIubZFDzBbWsoeBNVqhoXlXLBZVXBDw",holder.imgItem,options);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		return v;
		
		
	}
	
}
