package com.example.pictureperfectpro;

import org.json.JSONException;
import org.json.JSONObject;

public class Response {
	
	String status,comment;
	
	public String getStatus() {
		return status;
	}


	public String getComment() {
		return comment;
	}

	public Response(String data) throws JSONException {
		
		try
		{
			JSONObject obj=new JSONObject(data).getJSONObject("response");
			status=obj.getString("status");
			comment=obj.getString("comment");
		}
		catch(JSONException e)
		{
			throw e;
		}
	}

}
