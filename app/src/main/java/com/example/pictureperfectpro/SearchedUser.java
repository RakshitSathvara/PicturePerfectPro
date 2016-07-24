package com.example.pictureperfectpro;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchedUser
{
	
	private String user_id,user_name,collections,followers,following,user_camera,user_email,first_name,last_name,is_following;
	
	

	

	public void setIs_following(String is_following) {
		this.is_following = is_following;
	}

	public String getFollowing() {
		return following;
	}

	public String getUser_camera() {
		return user_camera;
	}

	public String getUser_email() {
		return user_email;
	}

	public String getFirst_name() {
		return first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public SearchedUser(JSONObject obj) throws JSONException
	{
		user_id=obj.getString("user_id");
		user_name=obj.getString("user_name");
		collections=obj.getString("collection");
		followers=obj.getString("followers");
		following=obj.getString("following");
		user_camera=obj.getString("user_camera");
		user_email=obj.getString("user_email");
		first_name=obj.getString("first_name");
		last_name=obj.getString("last_name");
		is_following=obj.getString("is_following");
	}
	
	public String getIs_following() {
		return is_following;
	}
	
	public String getUser_name() {
		return user_name;
	}

	public String getUser_id() {
		return user_id;
	}


	public String getCollections() {
		return collections;
	}

	public String getFollowers() {
		return followers;
	}


}
