package com.example.pictureperfectpro;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;


public class QuickSearch extends Activity implements AdapterView.OnItemClickListener  {

	String sesToken,result,keyword,page="1",camera,aperture,shutter,iso,lens,focalLength,dayNight,color;
	
	GridView quickSearch;
	
	ArrayList<Photo> singlePhoto = new ArrayList<Photo>();
	
	ArrayList<String> src=new ArrayList<String>();
	
	QuickSearchAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_quick_search);
		
		// UNIVERSAL IMAGE LOADER SETUP
				DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheOnDisc(true).cacheInMemory(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.displayer(new FadeInBitmapDisplayer(300)).build();

				ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
						getApplicationContext())
						.defaultDisplayImageOptions(defaultOptions)
						.memoryCache(new WeakMemoryCache()).build();

				ImageLoader.getInstance().init(config);
				
		// END - UNIVERSAL IMAGE LOADER SETUP
		//Toast.makeText(this, "before if", Toast.LENGTH_SHORT).show();
		sesToken=getIntent().getStringExtra("sestoken");
		keyword=getIntent().getStringExtra("keyword");
		if(TextUtils.equals(keyword,""))
		{
			//Toast.makeText(this, "inside if", Toast.LENGTH_SHORT).show();
			camera=getIntent().getStringExtra("camera");
			aperture=getIntent().getStringExtra("aperture");
			shutter=getIntent().getStringExtra("shutter");
			iso=getIntent().getStringExtra("iso");
			lens=getIntent().getStringExtra("lens");
			focalLength=getIntent().getStringExtra("focal_length");
			dayNight=getIntent().getStringExtra("day_night");
			color=getIntent().getStringExtra("color");
			
			if(TextUtils.equals(camera,"ANY"))
			{
				camera="%";
			}
			if(TextUtils.equals(aperture,"ANY"))
			{
				aperture="%";
			}
			if(TextUtils.equals(shutter,"ANY"))
			{
				shutter="%";
			}
			if(TextUtils.equals(iso,"ANY"))
			{
				iso="%";
			}
			if(TextUtils.equals(lens,"ANY"))
			{
				lens="%";
			}
			if(TextUtils.equals(focalLength,"ANY"))
			{
				focalLength="%";
			}
			if(TextUtils.equals(dayNight,"ANY"))
			{
				dayNight="%";
			}
			if(TextUtils.equals(color,"ANY"))
			{
				color="%";
			}
			
		}
		
		quickSearch=(GridView) findViewById(R.id.gridView_quickSearch);

		
		getImages(keyword, page, camera, aperture, shutter, iso, lens, focalLength, dayNight, color, sesToken);
		
		adapter=new QuickSearchAdapter(this, src);		
		
		quickSearch.setAdapter(adapter);
		
		quickSearch.setOnItemClickListener(this);
			
	}

	
	
	@Override
	public void onBackPressed() {
		ImageLoader.getInstance().clearDiskCache();
		ImageLoader.getInstance().clearMemoryCache();
		finish();
	}
	
	
	
	public void getImages(String ky, String pg, String camera, String aperture, String shutter, String iso, String lens, String focalLength, String dayNight, String color, String st)
	{
		
		try
		{
			if(TextUtils.equals(ky,""))
			{
				result=new PPP(this).execute("6",pg,camera,aperture,shutter,iso,lens,focalLength,dayNight,color,st).get();
			}
			else
			{
				result=new PPP(this).execute("2",ky,pg,st).get();
			}
		}
		catch(Exception e)
		{
			
		}
		
		JSONObject jsonResponse;
	    try {
	        
	        jsonResponse = new JSONObject(result);
	        JSONArray photos = jsonResponse.getJSONArray("photos");
	        
	        for(int i=0;i<photos.length();i++){
	            JSONObject photo = photos.getJSONObject(i);
	            Photo p = new Photo(photo);
		        singlePhoto.add(p);	  

	        }
	        
	        
	        
	    } catch (JSONException e) {
	        // TODO Auto-generated catch block
	        Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
	    }
	    
	    src.clear();
	    
	    for(int i=0;i<singlePhoto.size();i++)
	    {
	    	
	    	src.add(singlePhoto.get(i).src);
	    }
	    
	    if(src.size() % 20 == 0)
	    {
	    
	    	src.add("http://thoughtensemble.com/wp-content/uploads/2014/03/news-more-icon.png");
	    
	    }
	    
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
		
		if(arg2==singlePhoto.size())
		{
			//load more
			
			//Toast.makeText(this, "LOAD MORE", Toast.LENGTH_LONG).show();
			
			src.remove(src.size()-1);
			
			Integer i=Integer.parseInt(page) + 1;
			page=i.toString();
			getImages(keyword, page, camera, aperture, shutter, iso, lens, focalLength, dayNight, color, sesToken);
			
			adapter.notifyDataSetChanged();
			
		}
		else
		{
			
			Intent i=new Intent(this,ImageSwipeView.class);
						
			Integer x=arg2;
			
			Bundle b=new Bundle();
			b.putParcelableArrayList("photoList", singlePhoto);
			
			i.putExtras(b);
			i.putExtra("index", x.toString());
			i.putExtra("tkn", sesToken);
			
			
					
			startActivityForResult(i, 1);
			
		}
		
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		

			if(resultCode==Activity.RESULT_OK)
			{
				//Toast.makeText(this, "inside", Toast.LENGTH_SHORT).show();
				Bundle b=data.getExtras();
				if(b!=null)
				{
					
					singlePhoto=b.getParcelableArrayList("photoListBack");
				}
			}
		
	}


}


class ViewHolder
{
	ImageView imgItem;
	ViewHolder(View v)
	{
		imgItem=(ImageView) v.findViewById(R.id.imageView);
	}
}



class QuickSearchAdapter extends BaseAdapter
{
	
	
	ImageLoader imageLoader;
	
	//ArrayList<Photo> singleImageItemList;
	
	Context context;
	
	ArrayList<String> src=new ArrayList<String>();
	
	
	QuickSearchAdapter(Context context,ArrayList<String> data)
	{
		this.context=context;
		
		//singleImageItemList=new ArrayList<Photo>();
		
		src=data;
		
		imageLoader=ImageLoader.getInstance();		
		
		/*JSONObject jsonResponse;
	    try {
	        
	        jsonResponse = new JSONObject(data);
	        JSONArray movies = jsonResponse.getJSONArray("photos");
	        
	        for(int i=0;i<movies.length();i++){
	            JSONObject movie = movies.getJSONObject(i);
	            Photo p = new Photo(movie);
		        singleImageItemList.add(p);	  

	        }
	        
	    } catch (JSONException e) {
	        // TODO Auto-generated catch block
	        Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
	    }*/
	    
	    

		

		
	}

	@Override
	public int getCount()
	{
		//return singleImageItemList.size();
		return src.size();
	}

	@Override
	public Object getItem(int arg0) 
	{
		//return singleImageItemList.get(arg0);
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
		
		View row=arg1;
		ViewHolder holder=null;
		
		if(row==null)
		{
			LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row=inflater.inflate(R.layout.quick_search_item_layout, arg2,false);
			holder=new ViewHolder(row);
			row.setTag(holder);
		}
		else
		{
			holder=(ViewHolder) row.getTag();
		}
		
		
		
		try
		{
			
			//holder.imgItem.setImageBitmap(new GetImage().execute(singleImageItemList.get(arg0).src).get());
			
			
			DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
					.cacheOnDisc(true).resetViewBeforeLoading(true).build();
			
			imageLoader.displayImage(src.get(arg0),holder.imgItem,options);				

			
		}
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return row;
	}

	
	
}
