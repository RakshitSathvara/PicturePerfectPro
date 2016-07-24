package com.example.pictureperfectpro;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowCollectionDetails extends Activity implements AdapterView.OnItemClickListener {
	
	
	String sesToken,cid,page="1";
	
	TextView lbl_cname,lbl_cImgCount,lbl_cbody,lbl_clikes,lbl_cfavs,lbl_cwatchers;
	
	GridView gridCollectionImages;
	
	String result;
	
	ImageView imgLikeCollection,imgWatchCollection;
	
	
	ArrayList<Photo> singlePhoto = new ArrayList<Photo>();
	
	ArrayList<String> src=new ArrayList<String>();
	
	QuickSearchAdapter adapter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_show_collection_details);
		
		
		lbl_cname=(TextView) findViewById(R.id.lbl_collectionNameDetails);
		lbl_cImgCount=(TextView) findViewById(R.id.lbl_collectionPhotoCount);
		lbl_cbody=(TextView) findViewById(R.id.lbl_collectionDescBody);
		lbl_clikes=(TextView) findViewById(R.id.lbl_collectionLikes);
		lbl_cfavs=(TextView) findViewById(R.id.lbl_collectionFavs);
		lbl_cwatchers=(TextView) findViewById(R.id.lbl_collectionWatchers);
		
		imgLikeCollection=(ImageView) findViewById(R.id.img_likeCollection);
		imgWatchCollection=(ImageView) findViewById(R.id.img_addCollectionToWatchList);
		
		
		sesToken=getIntent().getStringExtra("sestoken");
		
		cid=getIntent().getStringExtra("cid");
		
		if(getIntent().getStringExtra("lflag").equals("1"))
		{
			imgLikeCollection.setImageResource(R.drawable.liked);
		}
		else
		{
			imgLikeCollection.setImageResource(R.drawable.like);
		}
		
		if(getIntent().getStringExtra("wflag").equals("1"))
		{
			imgWatchCollection.setImageResource(R.drawable.watcher2);
		}
		else
		{
			imgWatchCollection.setImageResource(R.drawable.watcher);
		}

		
		imgLikeCollection.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				try
				{
					String resultString= new PPP(v.getContext()).execute("15",cid,sesToken).get();
					Response res=new Response(resultString);
					if(res.getStatus().equals("1"))
					{
						imgLikeCollection.setImageResource(R.drawable.liked);
						
						
						lbl_clikes.setText(res.getComment());
					}
					else
					{
						lbl_clikes.setText(res.getComment());
						imgLikeCollection.setImageResource(R.drawable.like);
					}
				}
				catch (Exception e)
				{
					Toast.makeText(v.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		
		imgWatchCollection.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				
				try
				{
					String resultString= new PPP(v.getContext()).execute("16",cid,sesToken).get();
					Response res=new Response(resultString);
					if(res.getStatus().equals("1"))
					{
						imgWatchCollection.setImageResource(R.drawable.watcher2);
						
						
						lbl_cwatchers.setText(res.getComment());
					}
					else
					{
						lbl_cwatchers.setText(res.getComment());
						
						imgWatchCollection.setImageResource(R.drawable.watcher);
					}
				}
				catch (Exception e)
				{
					Toast.makeText(v.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
				}
				
			}
		});

		
		
		
		gridCollectionImages=(GridView) findViewById(R.id.gridView_collectionImages);		
		
		lbl_cname.setText(getIntent().getStringExtra("cname"));
		lbl_cImgCount.setText(getIntent().getStringExtra("cimgs"));
		lbl_cbody.setText(getIntent().getStringExtra("cdesc"));
		lbl_clikes.setText(getIntent().getStringExtra("clikes"));
		lbl_cfavs.setText(getIntent().getStringExtra("cfavs"));
		lbl_cwatchers.setText(getIntent().getStringExtra("cwatchers"));		
		
		
		
		
		//Toast.makeText(this, cid, Toast.LENGTH_LONG).show();
		
		getImages();
		
		adapter=new QuickSearchAdapter(this, src);
		
		gridCollectionImages.setAdapter(adapter);
		
		gridCollectionImages.setOnItemClickListener(this);
		
		
	}
	
	
	
	public void getImages()
	{
		
		try
		{

				result=new PPP(this).execute("11",cid,page,sesToken).get();
				
				//Toast.makeText(this, result, Toast.LENGTH_LONG).show();

		}
		catch(Exception e)
		{
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
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
	    
	    if(src.size() % 20 == 0 && src.size()>0)
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
			
			getImages();
			
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
