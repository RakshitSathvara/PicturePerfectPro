package com.example.pictureperfectpro;

import org.json.JSONException;
import org.json.JSONObject;

public class EXIFData
{
	String camera,aperture,shutter,iso,lens,focalLength;
	
	public String[] getIso() {
		return iso.split(",");
	}

	public String[] getCamera() {		
		return camera.split(",");
	}

	public String[] getAperture() {
		return aperture.split(",");
	}

	public String[] getShutter() {
		return shutter.split(",");
	}

	public String[] getLens() {
		return lens.split(",");
	}

	public String[] getFocalLength() {
		return focalLength.split(",");
	}

	public EXIFData(String data) throws JSONException
	{
		try
		{
			JSONObject obj=new JSONObject(data).getJSONObject("exif");
			camera=obj.getString("camera");
			aperture=obj.getString("aperture");
			shutter=obj.getString("shutter");
			iso=obj.getString("iso");
			lens=obj.getString("lens");
			focalLength=obj.getString("focal_length");			
		}
		catch(JSONException e)
		{
			throw e;
		}
	}

}
