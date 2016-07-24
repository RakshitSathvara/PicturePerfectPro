package com.example.pictureperfectpro;

import org.json.JSONException;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ME extends Fragment {

	String data;
	TextView name,follow,collections;
	EditText keyword;
	Button editProfile,btnExploreNrBy,searchUser;
	
	User user;
	
	Fragment f;
	
	public ME(String data)
	{
		this.data=data;
		try {
			user=new User(data);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		
		View me = inflater.inflate(R.layout.me_layout, container,false);
		/*try
		{
		UserNav userNav=new UserNav();
		
		FragmentManager manager=getChildFragmentManager();
		
		android.support.v4.app.FragmentTransaction transaction=manager.beginTransaction();
		
		transaction.add(R.id.pager2, userNav,"UserNavFragment");
		
		transaction.commit();
		}
		catch (Exception e) {
			Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
		}*/
				
		Typeface segoUI=Typeface.createFromAsset(getResources().getAssets(), "fonts/segoeui.ttf");
		
		name=(TextView) me.findViewById(R.id.txt_fullName);
		follow=(TextView) me.findViewById(R.id.txt_follow);
		editProfile=(Button) me.findViewById(R.id.btn_editProfile);
		btnExploreNrBy=(Button) me.findViewById(R.id.btn_exploreNrBy);
		searchUser=(Button) me.findViewById(R.id.btn_searchUser);
		
		searchUser.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				Intent intent=new Intent(getActivity(),SearchUser.class);
				
				intent.putExtra("sestoken",user.getSesToken());
				
		        startActivity(intent);				
			}
		});
		
		editProfile.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v) {
				
				Intent intent=new Intent(getActivity(),EditProfile.class);
				intent.putExtra("first_name",user.getFirst_name());
				intent.putExtra("last_name", user.getLast_name());
				intent.putExtra("email",user.getEmail());
				intent.putExtra("camera",user.getCamera());
				intent.putExtra("sestoken",user.getSesToken());
				
		        startActivity(intent);
				
			}
		});
		
		btnExploreNrBy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i=new Intent(getActivity(),ShowExplored.class);
				startActivity(i);
				
			}
		});

		collections=(TextView) me.findViewById(R.id.lbl_userCollections);
		
		keyword=(EditText) me.findViewById(R.id.txt_keyword);
		
		keyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH)
				{
					if(!TextUtils.isEmpty(keyword.getText()))
					{
						Intent i=new Intent(getActivity(),QuickSearch.class);
						i.putExtra("sestoken",user.getSesToken());
						i.putExtra("keyword", keyword.getText().toString());
				        startActivity(i);
						return true;
					}
				}
				return false;
			}
		});
				
		
		name.setTypeface(segoUI);
		follow.setTypeface(segoUI);
		name.setText(user.getFirst_name()+ " " + user.getLast_name());	
		follow.setText(user.getFollowed_by()+" followers"+"     "+user.getFollowing()+" following");
		editProfile.setTypeface(segoUI);

		collections.setText(user.getCollections()+" collections");
		
		
		return me;
	}
	
	
	
	
	

}
