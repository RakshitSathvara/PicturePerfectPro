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

import android.R.array;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Collections extends Fragment {
	
	
	User user;
	
	GridView gridCollections;
	
	ArrayList<UserCollectionObj> collectionObjArr=new ArrayList<UserCollectionObj>();
	
	ArrayList<String> thumbnailSrcArr=new ArrayList<String>();
	ArrayList<String> nameArr=new ArrayList<String>();
	
	String result;
	
	
	public Collections(String data)
	{
		try {
			user=new User(data);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		

		
		View collections = inflater.inflate(R.layout.collections_layout, container,false);
		
		
		
		/////////////////////////////////// UNIVERSAL IMAGE LOADER SETUP
				
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
		.cacheOnDisc(true).cacheInMemory(true)
		.imageScaleType(ImageScaleType.EXACTLY)
		.displayer(new FadeInBitmapDisplayer(300)).build();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
		.defaultDisplayImageOptions(defaultOptions)
		.memoryCache(new WeakMemoryCache()).build();
		
		ImageLoader.getInstance().init(config);
		
		/////////////////////////////////// END - UNIVERSAL IMAGE LOADER SETUP
		
		
		gridCollections=(GridView) collections.findViewById(R.id.gridView_collections);
		
		
		
		getCollections();
		
		
		
		CollectionsGridAdapter adapter=new CollectionsGridAdapter(getActivity(), thumbnailSrcArr, nameArr);
		
		
		
		gridCollections.setAdapter(adapter);
		
		
		
		gridCollections.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				if(arg2==0)
				{
				
					Intent i=new Intent(getActivity(),CreateNewCollectionDialog.class);
					i.putExtra("sestoken", user.getSesToken());
					startActivityForResult(i, 1);				
				
				}
				else
				{
					Intent i=new Intent(getActivity(),ShowCollectionDetails.class);
					i.putExtra("cname", collectionObjArr.get(arg2-1).getCollection_name());
					i.putExtra("cimgs", collectionObjArr.get(arg2-1).getTotal_images());
					i.putExtra("cdesc", collectionObjArr.get(arg2-1).getCollection_desc());
					i.putExtra("clikes", collectionObjArr.get(arg2-1).getCollection_likes());
					i.putExtra("cfavs", collectionObjArr.get(arg2-1).getCollection_favs());
					i.putExtra("cwatchers", collectionObjArr.get(arg2-1).getCollection_watchers());
					i.putExtra("sestoken", user.getSesToken());
					i.putExtra("cid", collectionObjArr.get(arg2-1).getCollection_id());
					i.putExtra("lflag", collectionObjArr.get(arg2-1).getLike_flag());
					i.putExtra("wflag", collectionObjArr.get(arg2-1).getWatch_flag());
					startActivity(i);
				}
				
			}
		});
		
		
		
		
		return collections;
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(resultCode==Activity.RESULT_OK)
		{
			
			if(data.getStringExtra("status").equals("ok"))
			{
				getCollections();
			}
		}
	}
	

	@Override
	public void onResume() {
		
		getCollections();
		
		super.onResume();
		
		
		
	}
	
	
	public void getCollections()
	{
		
		try
		{
			collectionObjArr.clear();
			
			thumbnailSrcArr.clear();
	        nameArr.clear();
	        
	        thumbnailSrcArr.add("http://thoughtensemble.com/wp-content/uploads/2014/03/news-more-icon.png");
	        nameArr.add("New Collection");
	        
			
			result=new PPP(getActivity()).execute("9",user.getSesToken(),"").get();
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
				Toast.makeText(getActivity(), err.comment, Toast.LENGTH_LONG).show();
			}
			catch (Exception ex)
			{
				Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
			}
		}
		catch (Exception e) {
			Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
		}
		
	}

}




class ItemHolder
{
	ImageView imgItem;
	TextView lbl_name;
	ItemHolder(View v)
	{
		imgItem=(ImageView) v.findViewById(R.id.img_collectionItem);
		lbl_name=(TextView) v.findViewById(R.id.lbl_collectionName);
	}
}


class CollectionsGridAdapter extends BaseAdapter
{
	Context context;
	
	ArrayList<String> thumbnailSrcArr=new ArrayList<String>();
	ArrayList<String> nameArr=new ArrayList<String>();
	
	ImageLoader imageLoader;
	
	
	public CollectionsGridAdapter(Context context,ArrayList<String> thm,ArrayList<String> nm)
	{
		this.context=context;
		thumbnailSrcArr=thm;
		nameArr=nm;
		imageLoader=ImageLoader.getInstance();
	}
	

	@Override
	public int getCount() {
		
		return thumbnailSrcArr.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return thumbnailSrcArr.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		View v=arg1;
		ItemHolder holder=null;
		
		if(v==null)
		{
			LayoutInflater inflator=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v=inflator.inflate(R.layout.collection_item,arg2,false);
			holder=new ItemHolder(v);
			v.setTag(holder);
		}
		else
		{
			holder=(ItemHolder) v.getTag();
		}
		
		try
		{
			DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
					.cacheOnDisc(true).resetViewBeforeLoading(true).build();
			
			imageLoader.displayImage(thumbnailSrcArr.get(arg0),holder.imgItem,options);
			holder.lbl_name.setText(nameArr.get(arg0).toString());
		}
		catch (Exception e) {
			Toast.makeText(context, "val: "+nameArr.get(arg0).toString()+" adapter: "+e.toString(), Toast.LENGTH_LONG).show();
		}
		
		return v;
		
		
	}
	
	
	
}

