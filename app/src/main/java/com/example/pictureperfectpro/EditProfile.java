package com.example.pictureperfectpro;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfile extends Activity {
	
	EditText fname,lname,email,camera;
	
	Button btnSaveProfileDetails;
	
	String editFname,editLname,editEmail,editCamera;
	
	String result,sesToken;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		
		
		
		setContentView(R.layout.activity_edit_profile);
		
		
		fname=(EditText) findViewById(R.id.txt_editFirstName);
		lname=(EditText) findViewById(R.id.txt_editLastName);
		email=(EditText) findViewById(R.id.txt_editEmail);
		camera=(EditText) findViewById(R.id.txt_editCamera);
		
		btnSaveProfileDetails=(Button) findViewById(R.id.btn_saveProfileDetails);
		
		btnSaveProfileDetails.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(saveProfileDetails())
				{
					EditProfile.this.finish();
				}
				
			}
		});
		
		
		try
		{
			
			editFname=getIntent().getStringExtra("first_name");
			editLname=getIntent().getStringExtra("last_name");
			editEmail=getIntent().getStringExtra("email");
			editCamera=getIntent().getStringExtra("camera");
			sesToken=getIntent().getStringExtra("sestoken");
			
			fname.setText(editFname);
			lname.setText(editLname);
			email.setText(editEmail);
			camera.setText(editCamera);
			
		}
		catch (Exception e) {
			Toast.makeText(this, getIntent().getStringExtra("first_name")+getIntent().getStringExtra("last_name")+getIntent().getStringExtra("email")+getIntent().getStringExtra("camera"), Toast.LENGTH_LONG).show();
		}
		
		
		
	}
	
	@Override
	public void onBackPressed()
	{
		if(fname.getText().toString().equals(editFname) && lname.getText().toString().equals(editLname) && email.getText().toString().equals(editEmail) && camera.getText().toString().equals(editCamera))
		{
			EditProfile.this.finish();
		}
		else
		{
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		    
			alertDialogBuilder.setTitle("Alert");
	 
			alertDialogBuilder
					.setMessage("Save changes?")
					.setCancelable(false)
					.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							
							//call function for saving details
							if(saveProfileDetails())
							{
								EditProfile.this.finish();
							}
							
						}
						
					
					  })
					  .setNegativeButton("No", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								
								EditProfile.this.finish();
									
								}
								
							
							  });   
			
			AlertDialog alertDialog = alertDialogBuilder.create();
			 
			alertDialog.show();  
		}
	}
	
	
	public Boolean saveProfileDetails()
	{
		try
		{
			result=new PPP(this).execute("7",fname.getText().toString(),lname.getText().toString(),email.getText().toString(),camera.getText().toString(),sesToken).get();
			Response res=new Response(result);
			if(res.getStatus().equals("ok"))
			{
				return true;
			}
			return false;
		}
		catch (Exception e) {
			try
			{
				ErrorObj err=new ErrorObj(result);
				Toast.makeText(this, err.getComment(), Toast.LENGTH_LONG).show();
				return false;
			}
			catch (Exception ex) {
				Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
				return false;
			}
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_profile, menu);
		return true;
	}

}
