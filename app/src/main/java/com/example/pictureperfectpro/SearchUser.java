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
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchUser extends Activity
{
	
	ListView lstSearchedUser;
	EditText txtuserId;

	
	String result;
	
	LstAdapter adapter;
	
	Integer page=1;
	
	String sesToken;
	
	ArrayList<SearchedUser> searchedUserArray=new ArrayList<SearchedUser>();
	
	Context context;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		
		setContentView(R.layout.activity_search_user);
		
		lstSearchedUser=(ListView) findViewById(R.id.lst_userSearch);
		
		txtuserId=(EditText) findViewById(R.id.txt_userSearch);
		
		sesToken=getIntent().getStringExtra("sestoken");
		
		
		
		txtuserId.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH)
				{
					if(!TextUtils.isEmpty(txtuserId.getText()))
					{
						try
						{
							searchedUserArray.clear();
							
							result=new PPP(v.getContext()).execute("13",txtuserId.getText().toString(),page.toString(),sesToken).get();
							
							JSONObject jsonResponse=new JSONObject(result);
							
							JSONArray users=jsonResponse.getJSONArray("users");
							
							for(int i=0;i<users.length();i++)
							{
								JSONObject user=users.getJSONObject(i);
								SearchedUser su=new SearchedUser(user);
								searchedUserArray.add(su);								
							}
							
							//Toast.makeText(v.getContext(), userName[0].toString(), Toast.LENGTH_LONG).show();
							
							adapter=new LstAdapter(v.getContext(), searchedUserArray,sesToken);
							
							
							
							lstSearchedUser.setAdapter(adapter);
							
							lstSearchedUser.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									Intent singleUser=new Intent(arg1.getContext(),SingleUserDetails.class);
									singleUser.putExtra("sestoken", sesToken);
									singleUser.putExtra("followers", searchedUserArray.get(arg2).getFollowers());
									singleUser.putExtra("following", searchedUserArray.get(arg2).getFollowing());
									singleUser.putExtra("fname", searchedUserArray.get(arg2).getFirst_name());
									singleUser.putExtra("lname", searchedUserArray.get(arg2).getLast_name());
									singleUser.putExtra("collections", searchedUserArray.get(arg2).getCollections());
									singleUser.putExtra("user", searchedUserArray.get(arg2).getUser_id());
									singleUser.putExtra("isfollowing", searchedUserArray.get(arg2).getIs_following());
									startActivity(singleUser);
									
								}
							});
							
							
						}
						catch(JSONException ex)
						{
							Toast.makeText(v.getContext(), ex.toString(), Toast.LENGTH_LONG).show();
						}
						catch (Exception e)
						{
							Toast.makeText(v.getContext(), e.toString(), Toast.LENGTH_LONG).show();
						}
						
						
						
						
						return true;
					}
				}
				return false;
			}
		});
		
		
			
	}


	

}


class SingleRowHolder
{
	TextView txt_userName;
	TextView txt_followers;
	TextView txt_collections;
	ImageView img_userImg;	
	Button btn_follow;
	
	SingleRowHolder(View v)
	{
		txt_userName=(TextView) v.findViewById(R.id.txt_singleUserName);
		txt_followers=(TextView) v.findViewById(R.id.txt_single_followers);
		txt_collections=(TextView) v.findViewById(R.id.txt_single_collections);
		img_userImg=(ImageView) v.findViewById(R.id.img_singleUserImg);	
		btn_follow=(Button) v.findViewById(R.id.btn_single_follow_user);
	}
	
}


class LstAdapter extends ArrayAdapter<SearchedUser>
{
	ArrayList<SearchedUser> searchedUserArray=new ArrayList<SearchedUser>();
	
	String sesToken;
	
	Context context;
	
	public LstAdapter(Context context, ArrayList<SearchedUser> searchedUserArray,String sesToken)
	{
		super(context, R.layout.single_user_search_item,searchedUserArray);
		this.sesToken=sesToken;
		this.context=context;
		this.searchedUserArray=searchedUserArray;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View v=convertView;
		SingleRowHolder holder=null;
		
		
		if(v==null)
		{
			LayoutInflater inflator=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v=inflator.inflate(R.layout.single_user_search_item, parent,false);
			holder=new SingleRowHolder(v);
			v.setTag(holder);
		}
		else
		{
			holder=(SingleRowHolder) v.getTag();
		}
		
		/*TextView txt_userName=(TextView) v.findViewById(R.id.txt_singleUserName);
		TextView txt_followers=(TextView) v.findViewById(R.id.txt_single_followers);
		TextView txt_collections=(TextView) v.findViewById(R.id.txt_single_collections);
		ImageView img_userImg=(ImageView) v.findViewById(R.id.img_singleUserImg);	
		Button btn_follow=(Button) v.findViewById(R.id.btn_single_follow_user);*/
		
		if(searchedUserArray.get(position).getIs_following().equals("1"))
		{
			holder.btn_follow.setText("following");
		}
		
		
		
		holder.btn_follow.setOnClickListener(new followBtnOnClickListner(holder,context,searchedUserArray.get(position),sesToken));
		
		holder.txt_userName.setText(searchedUserArray.get(position).getUser_name());
		holder.txt_followers.setText("followers "+searchedUserArray.get(position).getFollowers());
		holder.txt_collections.setText("collections "+searchedUserArray.get(position).getCollections());
		
		
		return v;
		
	}	
	
}

class followBtnOnClickListner implements OnClickListener
{
	Context context;
	SearchedUser searchedUser;
	SingleRowHolder holder;
	String sesToken;
	
	public followBtnOnClickListner(SingleRowHolder holder, Context context ,SearchedUser searchedUser, String sesToken)
	{
		this.sesToken=sesToken;
		this.holder=holder;
		this.context=context;
		this.searchedUser=searchedUser;
	}

	@Override
	public void onClick(View v)
	{
		//Toast.makeText(context, searchedUser.getUser_name().toString(), Toast.LENGTH_SHORT).show();
		
		try
		{
			String resultString= new PPP(context).execute("14",searchedUser.getUser_id().toString(),sesToken).get();
			Response res=new Response(resultString);
			if(res.getStatus().equals("1"))
			{
				holder.btn_follow.setText("following");
				
			}
			else
			{
				holder.btn_follow.setText("follow");
			}
		}
		catch (Exception e)
		{
			Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
		}
		
	}
	
	

}
