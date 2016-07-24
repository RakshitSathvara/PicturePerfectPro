package com.example.pictureperfectpro;

import java.net.URLEncoder;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
	
	public
	Button btn_login;
	TextView txt_uid,txt_pwd;
	CheckBox chk_rememberMe;
	SharedPreferences sharedPref;
	String result;
	
	public static final String prefName = "shared_pref";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_login);
		
		btn_login=(Button) findViewById(R.id.btn_login);
		txt_uid=(TextView) findViewById(R.id.txt_emailId);
		txt_pwd=(TextView) findViewById(R.id.txt_pwd);
		chk_rememberMe=(CheckBox) findViewById(R.id.chk_rememberMe);
		
		chk_rememberMe.setChecked(false);
		
		sharedPref=getSharedPreferences(prefName, MODE_PRIVATE);
		String suid=sharedPref.getString("savedUserId", null);
		String spass=sharedPref.getString("savedPassword", null);
		
		if(suid!=null && spass!=null)
		{
			txt_uid.setText(suid);
			txt_pwd.setText(spass);
			chk_rememberMe.setChecked(true);
		}
		
		
	}
	
	
	public void login(View v)
	{
		
		
		if(!TextUtils.isEmpty(txt_uid.getText()) && !TextUtils.isEmpty(txt_pwd.getText()))
		{
			try
			{
				//String result;
				
				String password=ExtraFunctions.md5(txt_pwd.getText().toString());
				
				//Toast.makeText(this, password, Toast.LENGTH_LONG).show();
				
				result=new PPP(this).execute("1",txt_uid.getText().toString(),password).get();
				JSONObject object=new JSONObject(result).getJSONObject("user");
				if(!object.getString("session_token").isEmpty())
				{
					
					SharedPreferences.Editor edt=sharedPref.edit();
					if(chk_rememberMe.isChecked())
					{
						edt.putString("savedUserId", txt_uid.getText().toString());
						edt.putString("savedPassword", txt_pwd.getText().toString());
						edt.commit();
					}
					else
					{
						edt.putString("savedUserId", null);
						edt.putString("savedPassword", null);
						edt.commit();
					}
					
					Intent i=new Intent(getBaseContext(),MainFrame.class);
					i.putExtra("uid",txt_uid.getText().toString());
					i.putExtra("pwd",password);
			        startActivity(i);
				}
				
			}

			catch(Exception e)
			{
				try
				{
					ErrorObj errorObj=new ErrorObj(result);
					Toast.makeText(this, errorObj.comment, Toast.LENGTH_SHORT).show();					
				}
				catch (Exception ex)
				{
					Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
				}
				
			}
			
			
		}
		
	}


}
