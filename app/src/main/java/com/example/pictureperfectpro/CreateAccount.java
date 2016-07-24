package com.example.pictureperfectpro;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class CreateAccount extends Activity {

	public
	Button btn_login;
	TextView txt_uid,txt_pwd,txt_cpwd,txt_fname,txt_lname;
	CheckBox chk_rememberMe;	
	
	RadioButton male,female;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_create_account);
		
		
		btn_login=(Button) findViewById(R.id.btn_signUp);
		txt_uid=(TextView) findViewById(R.id.txt_emailId);
		txt_pwd=(TextView) findViewById(R.id.txt_pwd);
		txt_cpwd=(TextView) findViewById(R.id.txt_cpwd);
		txt_fname=(TextView) findViewById(R.id.txt_userFirstName);
		txt_lname=(TextView) findViewById(R.id.txt_user_LastName);
		
		male=(RadioButton) findViewById(R.id.rdo_male);
		female=(RadioButton) findViewById(R.id.rdo_female);
		
	}
	
	
	
	
	
	public void createAccount(View v)
	{
		//Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
		
		String result="";
		
		if(!TextUtils.isEmpty(txt_uid.getText().toString()) && !TextUtils.isEmpty(txt_pwd.getText().toString()) && !TextUtils.isEmpty(txt_cpwd.getText().toString()) && !TextUtils.isEmpty(txt_fname.getText().toString()) && !TextUtils.isEmpty(txt_lname.getText().toString()))
		{
			if(TextUtils.equals(txt_pwd.getText(),txt_cpwd.getText()))
			{
				try
				{
					String gender=male.isChecked()?"1":"0";
					
					String pwd_hash=ExtraFunctions.md5(txt_pwd.getText().toString());
					
					String location;
					
					GPSTracker gps=new GPSTracker(this);
					if(gps.canGetLocation())
					{
						location=gps.getLocality(this)+", "+gps.getCountryName(this);
					}
					else
					{
						location="NA";
					}
					
					result=new PPP(this).execute("0",txt_uid.getText().toString(),pwd_hash,txt_fname.getText().toString(),txt_lname.getText().toString(),gender,location).get();
					//Toast.makeText(this, result, Toast.LENGTH_LONG).show();
					Response res=new Response(result);
					Toast.makeText(this, res.getComment(), Toast.LENGTH_SHORT).show();
					if(res.getStatus().equals("ok"))
					{
						finish();
					}
					
				}
				catch(Exception e)
				{
					try
					{
						ErrorObj err=new ErrorObj(result);
						Toast.makeText(this, err.getComment(), Toast.LENGTH_LONG);
					}
					catch (Exception ex)
					{
						Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
					}
				}
			}
			else
			{
				Toast.makeText(this, "password does not match", Toast.LENGTH_SHORT).show();
			}
		}
		else
		{
			Toast.makeText(this, "all fields are mandatory", Toast.LENGTH_SHORT).show();
		}
	
	}
	


}
