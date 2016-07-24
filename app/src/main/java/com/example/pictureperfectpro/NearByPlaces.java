package com.example.pictureperfectpro;

import org.json.JSONException;
import org.json.JSONObject;

public class NearByPlaces
{
	
	private String photo_url,location;
	
	public NearByPlaces(JSONObject obj) throws JSONException
	{
		try
		{
			
			photo_url=obj.getString("photo_url");
			location=obj.getString("location");
		}
		catch (JSONException e)
		{
			throw e;
		}
	}

	public String getPhoto_url() {
		return photo_url;
	}

	public String getLocation() {
		return location;
	}

}
