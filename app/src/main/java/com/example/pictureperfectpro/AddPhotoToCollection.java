package com.example.pictureperfectpro;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera.Size;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AddPhotoToCollection extends Activity {
	
	ListView lstUserCollections;
	
	String[] arrCollectionName;
	
	String result,sesToken;
	
	String result2,pid;
	
	ArrayList<UserCollectionObj> collectionObjArr=new ArrayList<UserCollectionObj>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_add_photo_to_collection);
		
		lstUserCollections=(ListView) findViewById(R.id.lst_userCollections);
		
		sesToken=getIntent().getStringExtra("sestoken");
		
		pid=getIntent().getStringExtra("pid");
		
		
		getCollections();
		
		
		 lstUserCollections.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					
					
					if(arg2!=arrCollectionName.length-1)
					{
						try
						{
								result2=new PPP(arg1.getContext()).execute("12",collectionObjArr.get(arg2).getCollection_id(),pid,sesToken).get();
								Response res=new Response(result2);
								if(res.getStatus().equals("ok"))
								{
									Toast.makeText(arg1.getContext(), res.getComment(), Toast.LENGTH_SHORT).show();
									finish();
								}
						}
						catch (JSONException e)
						{
							try
							{
								ErrorObj err=new ErrorObj(result2);
								Toast.makeText(arg1.getContext(), err.getComment(), Toast.LENGTH_LONG).show();
							}
							catch (Exception ex)
							{
								Toast.makeText(arg1.getContext(), e.toString(), Toast.LENGTH_LONG).show();
							}
						
						}
						catch(Exception e)
						{
							Toast.makeText(arg1.getContext(), e.toString(), Toast.LENGTH_LONG).show();
						}
					}
					else
					{
						Intent i=new Intent(arg1.getContext(),CreateNewCollectionDialog.class);
						i.putExtra("sestoken", sesToken);
						startActivityForResult(i, 1);
					}
					
					
				}
			});		
			
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		
		if(resultCode==Activity.RESULT_OK)
		{			
			if(data.getStringExtra("status").equals("ok"))
			{
				getCollections();
			}
		}
		
	}
	
	
	public void getCollections()
	{
		
		try
		{
			result=new PPP(this).execute("9",sesToken,"").get();
			
			JSONObject jsonResponse = new JSONObject(result);
	        JSONArray arrOfCollection = jsonResponse.getJSONArray("collection");			
			
	        collectionObjArr.clear();
	        
	        for(int i=0;i<arrOfCollection.length();i++)
	        {
	            JSONObject collectionObjJSON = arrOfCollection.getJSONObject(i);
	            UserCollectionObj collectionObj = new UserCollectionObj(collectionObjJSON);
		        collectionObjArr.add(collectionObj);
	        }
		        
	        arrCollectionName=new String[collectionObjArr.size()+1];
	        
	        //Toast.makeText(this, String.valueOf(collectionObjArr.size()), Toast.LENGTH_LONG).show();
		        
		        for(int j=0;j<collectionObjArr.size();j++)
			    {
			    	arrCollectionName[j]=collectionObjArr.get(j).getCollection_name();
			    }
		        
		        
		        arrCollectionName[collectionObjArr.size()]="+ Create New";
		        
		        //Toast.makeText(this, arrCollectionName[1], Toast.LENGTH_LONG).show();
		        
		        CollectionsListAdapter adapter =new CollectionsListAdapter(this, arrCollectionName);
		        
		        lstUserCollections.setAdapter(adapter);	
			
		}
		catch(JSONException e)
		{
			try
			{
				ErrorObj err=new ErrorObj(result);
				Toast.makeText(this, err.comment, Toast.LENGTH_LONG).show();
				arrCollectionName=new String[1];
				arrCollectionName[0]="+ Create New";
				CollectionsListAdapter adapter =new CollectionsListAdapter(this, arrCollectionName);
		        
		        lstUserCollections.setAdapter(adapter);	
				
			}
			catch (Exception ex)
			{
				Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
			}
			
		}
		catch (Exception e)
		{
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
		
	}
	

}


class CollectionsListAdapter extends ArrayAdapter<String>
{
	
	Context context;
	
	String[] names;
	
	public CollectionsListAdapter(Context context,String[] names) {
		super(context, R.layout.collection_single_item,R.id.lbl_singleCollectionName, names);
		
		this.context=context;
		this.names=names;

	}

	
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		
		LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View contentRow= inflater.inflate(R.layout.collection_single_item,parent,false);
		
		TextView lbl_name=(TextView) contentRow.findViewById(R.id.lbl_singleCollectionName);
		
		lbl_name.setText(names[position]);
		
		return contentRow;

	}
	
	

	
	
	
}
