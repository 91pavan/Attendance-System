package com.ayaan;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class AyaanActivity extends Activity {
	public EditText fcode;
	public EditText fpass;
	public static String fccode;
	public String fcpass;
	DBAdapter db;
	Cursor c;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); 
        fcode=(EditText)findViewById(R.id.Username);
        fpass=(EditText)findViewById(R.id.Password);
                
    }
     
    
      public void register(View v)
      {
    	  Intent i = new Intent(this,Register_adminActivity.class);
    	  startActivity(i);
      }
      public void home(View v)
      {
    	  DBAdapter db = new DBAdapter(this);
    	    fccode=fcode.getText().toString();
  			fcpass=fpass.getText().toString();
  			if(fccode.equals("")||fcpass.equals(""))
  			{
  				if(fccode.equals(""))
  				{
  				Toast.makeText(getApplicationContext(), "Pls Fill in the Username!", Toast.LENGTH_LONG).show();
  				}
  				else  if(fcpass.equals(""))
  				{	
  					Toast.makeText(getApplicationContext(), "Pls Fill in the Password!", Toast.LENGTH_LONG).show();
  				}
  			}
  			else
  			{
  				try{
  			
  					db.open();
  					c=db.getRecordByName(fccode);
  					if(c.getCount()==0)
  					{
  						Toast.makeText(getApplicationContext(), "No Teacher is taking this course", Toast.LENGTH_LONG).show();
  						return ;	
  					}
  					if(!c.getString(2).equals(fcpass))
  					{
  						Toast.makeText(getApplicationContext(), "Incorrect Password!", Toast.LENGTH_LONG).show();
  						return ;
  					}
  					db.close();
  				}
  				catch(Exception e)
  				{
  					Toast.makeText(getApplicationContext(), "Could Not Retrieve Data", Toast.LENGTH_LONG).show();
  					return ;
  				}
  				Intent i=new Intent(this,TestActivity.class);
  	            startActivity(i);
  	            //Toast.makeText(getApplicationContext(), "Welcome "+fcname+" !", Toast.LENGTH_LONG).show();
  			}
      }

  }
