package com.example.pictureperfectpro;

import org.json.JSONException;
import org.json.JSONObject;

public class UserCollectionObj
{
	
	private String collection_id,collection_name,collection_desc,collection_likes,collection_favs,collection_watchers,thumbnail,total_images,like_flag,watch_flag;
	
	
	public String getWatch_flag() {
		return watch_flag;
	}


	public UserCollectionObj(JSONObject obj) throws JSONException
	{
		try
		{
			collection_id=obj.getString("collection_id");
			collection_name=obj.getString("collection_name");
			collection_desc=obj.getString("collection_description");
			collection_likes=obj.getString("collection_likes");
			collection_favs=obj.getString("collection_favs");
			collection_watchers=obj.getString("collection_watchers");
			total_images=obj.getString("total_images");
			thumbnail=obj.getString("thumbnail");
			like_flag=obj.getString("like_flag");
			watch_flag=obj.getString("watch_flag");
		}
		catch (JSONException e) {
			throw e;
		}
	}


	public String getLike_flag() {
		return like_flag;
	}


	public String getTotal_images() {
		return total_images;
	}


	public String getCollection_desc() {
		return collection_desc;
	}


	public String getCollection_likes() {
		return collection_likes;
	}


	public String getCollection_favs() {
		return collection_favs;
	}


	public String getCollection_watchers() {
		return collection_watchers;
	}


	public String getCollection_id() {
		return collection_id;
	}


	public String getCollection_name() {
		return collection_name;
	}


	public String getThumbnail() {
		return thumbnail;
	}
	
	

}
