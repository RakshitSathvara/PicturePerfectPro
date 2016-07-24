package com.example.pictureperfectpro;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Photo extends JSONObject implements Parcelable {
	
	String photo_id,name,description,src,camera_model,aperture,shutter,
	iso,focal_length,datetime,day_night,lens,favorite_count,like_count,
	share_count,keyword,tags,provider,max_color,like_flag,fav_flag;
	
	public void setFavorite_count(String favorite_count) {
		this.favorite_count = favorite_count;
	}


	public void setLike_count(String like_count) {
		this.like_count = like_count;
	}


	public void setLike_flag(String like_flag) {
		this.like_flag = like_flag;
	}


	public void setFav_flag(String fav_flag) {
		this.fav_flag = fav_flag;
	}


	public Photo(Parcel p)
	{
		photo_id=p.readString();
		name=p.readString();
		description=p.readString();
		src=p.readString();
		camera_model=p.readString();
		aperture=p.readString();
		shutter=p.readString();
		iso=p.readString();
		focal_length=p.readString();
		datetime=p.readString();
		day_night=p.readString();
		lens=p.readString();
		favorite_count=p.readString();
		like_count=p.readString();
		share_count=p.readString();
		keyword=p.readString();
		tags=p.readString();
		provider=p.readString();
		max_color=p.readString();
		like_flag=p.readString();
		fav_flag=p.readString();
		
	}
	
	public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {

		@Override
		public Photo createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Photo(source);
		}

		@Override
		public Photo[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Photo[size];
		}
	};
	
	public Photo(JSONObject obj) throws JSONException {

		try
		{
			//JSONObject obj=new JSONObject(data).getJSONObject("");
			photo_id=obj.getString("photo_id");
			name=obj.getString("name");
			description=obj.getString("description");
			src=obj.getString("src");
			camera_model=obj.getString("camera_model");
			aperture=obj.getString("aperture");
			shutter=obj.getString("shutter");
			iso=obj.getString("iso");
			focal_length=obj.getString("focal_length");
			datetime=obj.getString("datetime");
			day_night=obj.getString("day_night");
			lens=obj.getString("lens");
			favorite_count=obj.getString("favorite_count");
			like_count=obj.getString("like_count");
			share_count=obj.getString("share_count");
			keyword=obj.getString("keywords");
			tags=obj.getString("tags");
			provider=obj.getString("provider");
			max_color=obj.getString("max_color");
			like_flag=obj.getString("like_flag");
			fav_flag=obj.getString("fav_flag");
		
		}
		catch(JSONException e)
		{
			throw e;
		}
	
	}
	

	public String getPicture_id() {
		return photo_id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getSrc() {
		return src;
	}

	public String getCamera_model() {
		return camera_model;
	}

	public String getAperture() {
		return aperture;
	}

	public String getShutter() {
		return shutter;
	}

	public String getIso() {
		return iso;
	}

	public String getFocal_length() {
		return focal_length;
	}

	public String getDatetime() {
		return datetime;
	}

	public String getDay_night() {
		return day_night;
	}

	public String getLens() {
		return lens;
	}

	public String getFavorite_count() {
		return favorite_count;
	}

	public String getLike_count() {
		return like_count;
	}

	public String getShare_count() {
		return share_count;
	}

	public String getKeyword() {
		return keyword;
	}

	public String getTags() {
		return tags;
	}

	public String getProvider() {
		return provider;
	}

	public String getMax_color() {
		return max_color;
	}


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return hashCode();
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(photo_id);
		dest.writeString(name);
		dest.writeString(description);
		dest.writeString(src);
		dest.writeString(camera_model);
		dest.writeString(aperture);
		dest.writeString(shutter);
		dest.writeString(iso);
		dest.writeString(focal_length);
		dest.writeString(datetime);
		dest.writeString(day_night);
		dest.writeString(lens);
		dest.writeString(favorite_count);
		dest.writeString(like_count);
		dest.writeString(share_count);
		dest.writeString(keyword);
		dest.writeString(tags);
		dest.writeString(provider);
		dest.writeString(max_color);	
		dest.writeString(like_flag);
		dest.writeString(fav_flag);
	}
	
	
	
	

}
