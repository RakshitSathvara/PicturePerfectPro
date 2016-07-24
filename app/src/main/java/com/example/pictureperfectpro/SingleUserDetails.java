package com.example.pictureperfectpro;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SingleUserDetails extends Activity {
	
	
	TextView lblUserName,lblFollowers,lblFollowing,lblColloections;
	
	Button btnFollow;
	
	String sesToken;
	
	GridView gridCollections;
	
	ArrayList<UserCollectionObj> collectionObjArr=new ArrayList<UserCollectionObj>();
	
	ArrayList<String> thumbnailSrcArr=new ArrayList<String>();
	ArrayList<String> nameArr=new ArrayList<String>();
	
	String result;
	
	String user;
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_single_user_details);
		
		lblUserName=(TextView) findViewById(R.id.lbl_userFullName);	
		lblFollowers=(TextView) findViewById(R.id.lbl_userFollow);
		lblColloections=(TextView) findViewById(R.id.lbl_userCollections);
		
		btnFollow=(Button) findViewById(R.id.btn_userFollowBtn);
		
		if(getIntent().getStringExtra("isfollowing").equals("1"))
		{
			btnFollow.setText("following");
		}
		
		lblUserName.setText(getIntent().getStringExtra("fname")+" "+getIntent().getStringExtra("lname"));
		lblFollowers.setText(getIntent().getStringExtra("followers")+" followers"+"     "+getIntent().getStringExtra("following")+" following");
		lblColloections.setText(getIntent().getStringExtra("collections")+" collections");
		
		
		sesToken=getIntent().getStringExtra("sestoken");
		
		user=getIntent().getStringExtra("user");
		
		btnFollow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
			
				try
				{
					String resultString= new PPP(v.getContext()).execute("14",user,sesToken).get();
					Response res=new Response(resultString);
					if(res.getStatus().equals("1"))
					{
						btnFollow.setText("following");
						
					}
					else
					{
						btnFollow.setText("follow");
					}
				}
				catch (Exception e)
				{
					Toast.makeText(v.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		
		/////////////////////////////////// UNIVERSAL IMAGE LOADER SETUP
				
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
		.cacheOnDisc(true).cacheInMemory(true)
		.imageScaleType(ImageScaleType.EXACTLY)
		.displayer(new FadeInBitmapDisplayer(300)).build();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
		.defaultDisplayImageOptions(defaultOptions)
		.memoryCache(new WeakMemoryCache()).build();
		
		ImageLoader.getInstance().init(config);
		
		/////////////////////////////////// END - UNIVERSAL IMAGE LOADER SETUP
		
		
		gridCollections=(GridView) findViewById(R.id.gridView_singleUserCollections);
		
		getCollections();

		CollectionsGridAdapter adapter=new CollectionsGridAdapter(this, thumbnailSrcArr, nameArr);
		
		
		
		gridCollections.setAdapter(adapter);
		
		
		
		gridCollections.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				
					Intent i=new Intent(arg1.getContext(),ShowCollectionDetails.class);
					i.putExtra("cname", collectionObjArr.get(arg2).getCollection_name());
					i.putExtra("cimgs", collectionObjArr.get(arg2).getTotal_images());
					i.putExtra("cdesc", collectionObjArr.get(arg2).getCollection_desc());
					i.putExtra("clikes", collectionObjArr.get(arg2).getCollection_likes());
					i.putExtra("cfavs", collectionObjArr.get(arg2).getCollection_favs());
					i.putExtra("cwatchers", collectionObjArr.get(arg2).getCollection_watchers());
					i.putExtra("sestoken", sesToken);
					i.putExtra("cid", collectionObjArr.get(arg2).getCollection_id());
					i.putExtra("lflag", collectionObjArr.get(arg2).getLike_flag());
					i.putExtra("wflag", collectionObjArr.get(arg2).getWatch_flag());
					startActivity(i);
				
				
			}
		});

		
		
	}
	
	
	
	public void getCollections()
	{
		
		try
		{
			collectionObjArr.clear();
			
			thumbnailSrcArr.clear();
	        nameArr.clear();
	        
			
			result=new PPP(this).execute("9",sesToken,user).get();
			JSONObject jsonResponse = new JSONObject(result);
	        JSONArray arrOfCollection = jsonResponse.getJSONArray("collection");
	        
	        
	        
	        for(int i=0;i<arrOfCollection.length();i++)
	        {
	            JSONObject collectionObjJSON = arrOfCollection.getJSONObject(i);
	            UserCollectionObj collectionObj = new UserCollectionObj(collectionObjJSON);
		        collectionObjArr.add(collectionObj);	
	        }
		        
		        
		        
	        for(int j=0;j<collectionObjArr.size();j++)
	        {
			    thumbnailSrcArr.add(collectionObjArr.get(j).getThumbnail());
			    nameArr.add(collectionObjArr.get(j).getCollection_name());
	        }
		        
		       
		        

	        
		}
		catch(JSONException e)
		{
			try
			{
				ErrorObj err=new ErrorObj(result);
				Toast.makeText(this, err.comment, Toast.LENGTH_LONG).show();
			}
			catch (Exception ex)
			{
				Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
			}
		}
		catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
		
	}

	
	
	
	


}


