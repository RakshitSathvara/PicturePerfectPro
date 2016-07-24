package com.example.pictureperfectpro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class PPP extends AsyncTask<String, Void, String>
{
	ProgressDialog mDialog;
	Context context;
	
	String url;
	
	//String domainName="http://pictureperfectpro.herokuapp.com/";				//ONLINE
	
	//String domainName="http://192.168.10.106/ppp/";							//LOCAL HOME WIFI
	
	//String domainName="http://192.168.1.100/ppp/";							//LOCAL PHOTON WIFI
	
	//String domainName="http://192.168.10.120/ppp/";							//COLLEGE LAN
	
	String domainName="http://192.168.1.110/pictureperfectpro/";				//COLLEGE LAN
	
	
	
	PPP(Context context)
	{
		this.context = context;
	}

	
	void createAccount(String userName,String pwd,String fname,String lname,String gen,String loc)
	{
		url=domainName+"webservice.php?method=createAccount&user_name="+URLEncoder.encode(userName)+"&password="+pwd+"&first_name="+fname+"&last_name="+lname+"&gender="+gen+"&location="+URLEncoder.encode(loc);
	}
	
	void login(String id,String pwd)
	{
		url=domainName+"webservice.php?method=login&user_name="+id+"&password="+pwd;
	}
	
	void getFeeds(String sesToken)
	{
		url=domainName+"webservice.php?method=getFeeds&p1="+sesToken;
	}
	
	void quickSearch(String keyword,String page, String sesToken)
	{
		url=domainName+"webservice.php?method=quickSearch&keyword="+URLEncoder.encode(keyword)+"&page="+page+"&session_token="+sesToken;
	}
	
	void likePhoto(String pid,String sesToken)
	{
		url=domainName+"webservice.php?method=likePhoto&photo_id="+pid+"&session_token="+sesToken;
	}
	
	void favPhoto(String pid,String sesToken)
	{
		url=domainName+"webservice.php?method=favoritePhoto&photo_id="+pid+"&session_token="+sesToken;
	}
	
	void getEXIF(String sesToken)
	{
		url=domainName+"webservice.php?method=getEXIF&session_token="+sesToken;
	}
	
	void searchByEXIF(String page, String camera, String aperture, String shutter, String iso, String lens, String focalLength, String dayNight, String color, String sesToken)
	{
		url=domainName+"webservice.php?method=searchByEXIF&page="+page+"&camera="+URLEncoder.encode(camera)+"&aperture="+URLEncoder.encode(aperture)+"&shutter="+URLEncoder.encode(shutter)+"&iso="+URLEncoder.encode(iso)+"&lens="+URLEncoder.encode(lens)+"&focal_length="+URLEncoder.encode(focalLength)+"&day_night="+URLEncoder.encode(dayNight)+"&color="+URLEncoder.encode(color)+"&session_token="+sesToken;
	}
	
	void updateProfile(String fname, String lname, String email, String camera, String sesToken)
	{
		url=domainName+"webservice.php?method=updateProfile&first_name="+fname+"&last_name="+lname+"&email="+URLEncoder.encode(email)+"&camera="+URLEncoder.encode(camera)+"&session_token="+sesToken;
	}
	
	void getNearByPlaces(String lat, String lng)
	{
		url=domainName+"/webservice.php?method=googlePlaces&lat="+lat+"&long="+lng;
	}
	
	void getUserCollections(String sesToken, String user)
	{
			url=domainName+"/webservice.php?method=getUserCollection&session_token="+sesToken+"&user_id="+user;		
	}
	
	void createNewCollection(String name,String desc,String dt, String sesToken)
	{
		
		
		url=domainName+"/webservice.php?method=createNewCollection&name="+URLEncoder.encode(name)+"&description="+URLEncoder.encode(desc)+"&datetime="+URLEncoder.encode(dt)+"&session_token="+sesToken;
	}
	
	void getCollectionData(String cid,String page,String sesToken)
	{
		
		url=domainName+"/webservice.php?method=getCollectionData&collection_id="+cid+"&page="+page+"&session_token="+sesToken;
		
	}
	
	void addPhotoToCollection(String cid, String pid, String sesToken)
	{
		
		url=domainName+"/webservice.php/?method=addPhotoToCollection&collection_id="+cid+"&photo_id="+pid+"&session_token="+sesToken;
		
	}
	
	void searchUser(String uname, String page, String sesToken)
	{
		
		url=domainName+"/webservice.php/?method=searchUser&user_name="+uname+"&page="+page+"&session_token="+sesToken;
		
	}
	
	void followUser(String uid, String sesToken)
	{
		url=domainName+"/webservice.php/?method=followUser&user_id="+uid+"&session_token="+sesToken;
	}
	
	void likeCollection(String cid,String sesToken)
	{
		url=domainName+"/webservice.php/?method=likeCollection&collection_id="+cid+"&session_token="+sesToken;
	}
	
	void addCollectionToWatch(String cid,String sesToken)
	{
		url=domainName+"/webservice.php/?method=addCollectionToWatch&collection_id="+cid+"&session_token="+sesToken;
	}
	
	@Override
	protected String doInBackground(String... params) {
		
		
		switch(Integer.parseInt(params[0]))
		{
			case 0: createAccount(params[1], params[2], params[3], params[4], params[5], params[6]);
				
				break;
		
			case 1: login(params[1], params[2]);
				
				break;
				
			case 2: quickSearch(params[1], params[2], params[3]);
				
				break;
				
			case 3: likePhoto(params[1], params[2]);
				
				break;
	
			case 4: favPhoto(params[1], params[2]);
			
				break;
				
			case 5: getEXIF(params[1]);
			
				break;
				
			case 6: searchByEXIF(params[1],params[2],params[3],params[4],params[5],params[6],params[7],params[8],params[9],params[10]);
			
				break;
				
			case 7: updateProfile(params[1], params[2], params[3], params[4], params[5]);
			
				break;
				
			case 8: getNearByPlaces(params[1],params[2]);
			
				break;
				
			case 9: getUserCollections(params[1],params[2]);
				
				break;
				
			case 10: createNewCollection(params[1], params[2], params[3],params[4]);
			
				break;
				
			case 11: getCollectionData(params[1], params[2], params[3]);
			
				break;
				
			case 12: addPhotoToCollection(params[1],params[2],params[3]); 
				
				break;
				
			case 13: searchUser(params[1], params[2], params[3]);
			
				break;
				
			case 14: followUser(params[1], params[2]);
			
				break;
				
			case 15: likeCollection(params[1], params[2]);
			
				break;
			
			case 16: addCollectionToWatch(params[1], params[2]);
			
				break;
			
		
		}
		
		BufferedReader in =null;
		String data =null;
		
		try
		{
		
			HttpClient client=new DefaultHttpClient();			
			
			URI uri=new URI(url);
			
			HttpGet request=new HttpGet();
			
			request.setURI(uri);
			
			HttpResponse response=client.execute(request);
			
			in =new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			
			StringBuffer sb=new StringBuffer("");
			
			String l="";
			
			while((l=in.readLine())!=null)
			{
				sb.append(l);
			}
			
			in.close();
			
			data=sb.toString();
			
			return data;
		
		}
		catch(Exception e)
		{
			
			data="Exception: "+e.toString();
			return data;
		}
		finally
		{
			if(in!=null)
			{
				try
				{
					in.close();
					return data;
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}			
		}
		
	}
	
	
	
	@Override
    protected void onPreExecute() {
        super.onPreExecute();

        mDialog = new ProgressDialog(context);
        mDialog.setMessage("Please wait...");
        mDialog.show();
    }
	
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
	    mDialog.dismiss();
	       
	}
	

}
