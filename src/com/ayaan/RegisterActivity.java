package com.ayaan;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	
	public EditText fname;
	public EditText fpass;
	public EditText fcode;
	public String fcname;
	public String fcpass;
	public String fccode;
	DBAdapter db;
	Cursor c;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        fname=(EditText)findViewById(R.id.Tname);
        fpass=(EditText)findViewById(R.id.Password);
        fcode=(EditText)findViewById(R.id.Code);
        db = new DBAdapter(this);
	}
	public void home_start(View v)
    {
		fcname=fname.getText().toString();
		fcpass=fpass.getText().toString();
		fccode=fcode.getText().toString();
	
		try
		{
			db.open();
			
			c=db.getRecordByName(fccode);
			
				if(c.getCount()!=0)
				{
					Toast.makeText(getApplicationContext(), "This Subject is taken by other Teacher", Toast.LENGTH_LONG).show();
					db.close();
					return ;	
				}
				db.close();
		}catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), "Exception Occured", Toast.LENGTH_LONG).show();
			return;
		}
		if(fcname.equals("")||fcpass.equals("")||fccode.equals(""))
		{
			if(fcname.equals(""))
			{
			Toast.makeText(getApplicationContext(), "Pls Fill in the Faculty Name!", Toast.LENGTH_LONG).show();
			return;
			}
			else  if(fcpass.equals(""))
			{	
				Toast.makeText(getApplicationContext(), "Pls Fill in the Password!", Toast.LENGTH_LONG).show();
				return ;
			}
			else  if(fccode.equals(""))
			{	
				Toast.makeText(getApplicationContext(), "Pls Fill in the subject Code!", Toast.LENGTH_LONG).show();
				return;
			}
		}
		else
		{
			
			try
			{
			
	          	db.open();        
	           db.insertRecord(
	          		fcname,
	          		fcpass,
	          		fccode
	          		); 
	           db.insertClass(fccode, 0);
	          	db.close();
			}catch(Exception e)
			{
					Toast.makeText(getApplicationContext(), "Unable to Insert Pls Try again!!",Toast.LENGTH_LONG).show();
					
			}
     }
		Intent i=new Intent(this,AyaanActivity.class);
		finish();
        startActivity(i);
   }
    

}
