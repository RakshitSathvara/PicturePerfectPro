package com.example.pictureperfectpro;

import org.json.JSONException;
import org.json.JSONObject;

public class User extends JSONObject
{

	private String ui_id,first_name,last_name,gender,camera,email,following,followed_by,collections,location,sesToken,user_name;

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	User(String data) throws JSONException
	{
		
		JSONObject obj=new JSONObject(data).getJSONObject("user");
		ui_id = obj.getString("user_id");
		user_name=obj.getString("user_name");
		first_name=obj.getString("first_name");
		last_name=obj.getString("last_name");
		gender=obj.getString("gender");
		location=obj.getString("user_location");
		camera=obj.getString("user_camera");
		email=obj.getString("user_email");
		following=obj.getString("following");
		followed_by=obj.getString("followed_by");
		collections=obj.getString("collection_count");
		sesToken=obj.getString("session_token");
		

	}
	
	public String getSesToken() {
		return sesToken;
	}

	public String getUi_id() {
		return ui_id;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getFname() {
		return first_name;
	}

	public void setFname(String fname) {
		this.first_name = fname;
	}

	public String getLname() {
		return last_name;
	}

	public void setLname(String lname) {
		this.last_name = lname;
	}

	public String getCamera() {
		return camera;
	}

	public void setCamera(String camera) {
		this.camera = camera;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFollowing() {
		return following;
	}

	public String getFollowed_by() {
		return followed_by;
	}

	public String getCollections() {
		return collections;
	}

	public String getLocation() {
		return location;
	}
	
	
}
