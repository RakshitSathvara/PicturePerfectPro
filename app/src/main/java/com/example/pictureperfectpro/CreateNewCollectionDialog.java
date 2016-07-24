package com.example.pictureperfectpro;

import org.json.JSONException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateNewCollectionDialog extends Activity {
	
	
	Button btn_createCollection,btn_undo;
	
	EditText txt_name,txt_desc;
	
	String sesToken,result;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_create_new_collection_dialog);
		
		
		sesToken=getIntent().getStringExtra("sestoken");
		
		
		txt_name=(EditText) findViewById(R.id.txt_collectionName);
		txt_desc=(EditText) findViewById(R.id.txt_collectionDescription);
		
		btn_undo=(Button) findViewById(R.id.btn_undoCreateCollection);
		
		btn_undo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				finish();
				
			}
		});
		
		btn_createCollection=(Button) findViewById(R.id.btn_createCollection);
		
		btn_createCollection.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(!TextUtils.isEmpty(txt_name.getText()) && !TextUtils.isEmpty(txt_desc.getText()))
				{
					try
					{
						java.util.Date dt = new java.util.Date();

						java.text.SimpleDateFormat sdf = 
						     new java.text.SimpleDateFormat("yyyy-MM-dd");

						String currentTime = sdf.format(dt);
						
						//Toast.makeText(v.getContext(), currentTime, Toast.LENGTH_LONG).show();
						
						
						
						result=new PPP(v.getContext()).execute("10",txt_name.getText().toString(),txt_desc.getText().toString(),currentTime,sesToken).get();
						//Toast.makeText(v.getContext(), result, Toast.LENGTH_LONG).show();
						Response res=new Response(result);
						if(res.getStatus().equals("ok"))
						{
							Toast.makeText(v.getContext(), res.getComment(), Toast.LENGTH_LONG).show();
							
							Intent intent=new Intent();
							intent.putExtra("status", "ok");
							setResult(Activity.RESULT_OK,intent);
							
							finish();
						}
					}
					catch (JSONException ex) {
						
						try
						{
							ErrorObj errorObj=new ErrorObj(result);
							Toast.makeText(v.getContext(), errorObj.comment, Toast.LENGTH_SHORT).show();					
						}
						catch (Exception ex2)
						{
							Toast.makeText(v.getContext(), ex2.toString(), Toast.LENGTH_SHORT).show();
						}
						
						
					}
					catch(Exception e)
					{
						Toast.makeText(v.getContext(), e.toString(), Toast.LENGTH_LONG).show();
					}
				}
				else
				{
					Toast.makeText(v.getContext(), "enter name and description", Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
		
	}

}
