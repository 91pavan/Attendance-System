package com.ayaan;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageActivity extends ListActivity {
	public EditText sname;
	public EditText susn;
	public EditText phone;
	public String Sccode = AyaanActivity.fccode;
	public String ssname;
	public ListAdapter adapter;
	public String ssusn;
	public String sphone;
	DBAdapter db;
	Cursor c;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        sname=(EditText)findViewById(R.id.Sname);
        susn=(EditText)findViewById(R.id.USN);
        phone=(EditText)findViewById(R.id.Phone);
        db=new DBAdapter(this); 
        try
        {
        	
        	db.open();
        	
        	c=db.getRecordBycode(Sccode);
        	
        	adapter = new SimpleCursorAdapter(
        			this, 
        			R.layout.display1, 
        			c, 
        			new String[] {"Sname", "Usn"}, 
        			new int[] {R.id.Sname,R.id.USN});
        	        setListAdapter(adapter);
        			db.close();
        }
        catch(Exception e)
        {
        	Toast.makeText(getApplicationContext(), "Could not retrieve Details", Toast.LENGTH_LONG).show();
        }
	}
	
	 public void onListItemClick(ListView parent, View view, int position, long id) {
	    	
		 AlertDialog.Builder alertDialog = new AlertDialog.Builder(ManageActivity.this);

		 		
		 		alertDialog.setTitle("Delete Student Details");

		 		
		 		alertDialog.setMessage("Are you sure you want delete this Detail?");

		 		
		 		
		 		final Cursor cursor = (Cursor) adapter.getItem(position);
		 		alertDialog.setPositiveButton("YES",
		 				new DialogInterface.OnClickListener() {
		 					public void onClick(DialogInterface dialog,int which) {
		 						
		 				try{
		 					db.open();
		 					int id=cursor.getInt(cursor.getColumnIndex("_id"));
		 					db.deleteStudentRecord(id);		   	
		 					db.close();
		 				}
		 				catch(Exception e)
		 				{
		 					Toast.makeText(getApplicationContext(), "Could not delete  the Record", Toast.LENGTH_LONG).show();
		 					return;
		 				}
		 				Toast.makeText(getApplicationContext(), "Record Deleted", Toast.LENGTH_LONG).show(); 
		 				
		 			
		 				
		 				
		 					   Intent i=new Intent(ManageActivity.this,ManageActivity.class);
		 					  finish();
		 				   startActivity(i);
		 					}
		  			});
		 	
		 		alertDialog.setNegativeButton("NO",
		 				new DialogInterface.OnClickListener() {
		 					public void onClick(DialogInterface dialog,	int which) {
		 						dialog.cancel();
		 						
		 					}
		 				});

		 	
		 		alertDialog.show();
		     	
		     	
		     	
		     }
	
public void home_start(View v)
{
	//Toast.makeText(getApplicationContext(), "Welcome!!", Toast.LENGTH_LONG).show();
	DBAdapter db = new DBAdapter(this);
    ssname=sname.getText().toString();
	ssusn=susn.getText().toString();
	sphone=phone.getText().toString();
	Pattern pattern = Pattern.compile("\\d{10}");
	Matcher matcher = pattern.matcher(sphone);
      if (!matcher.matches()) 
      {
    	  Toast.makeText(getApplicationContext(), "Invalid Phone Id", Toast.LENGTH_LONG).show();
    	  return;
      }
     
	try
	{
		
		db.open();
		c=db.getRecordByUsn(Sccode, ssusn);
			if(c.getCount()!=0)
			{
				Toast.makeText(getApplicationContext(), "The Student has already \n registered for this subject", Toast.LENGTH_LONG).show();
				db.close();
				return;
			}
			db.close();
	}catch(Exception e)
	{
		Toast.makeText(getApplicationContext(), "Exception Occured", Toast.LENGTH_LONG).show();
		return;
	}
	if(ssname.equals("")||ssusn.equals("")||sphone.equals(""))
	{
		if(ssname.equals(""))
		{
		Toast.makeText(getApplicationContext(), "Pls Fill in the Student Name!", Toast.LENGTH_LONG).show();
		}
		else  if(ssusn.equals(""))
		{	
			Toast.makeText(getApplicationContext(), "Pls Fill in the Student Usn!", Toast.LENGTH_LONG).show();
		}
		else
		{
			Toast.makeText(getApplicationContext(), "Pls Enter the Phone No!", Toast.LENGTH_LONG).show();
		}
		
	}
	else
	{
		try{
	
			db.open();
			db.insertStudentRecord(ssname, ssusn, Sccode, 0, 0, sphone);
			db.close();
			
		}catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), "Unable to Insert Pls Try again!!",Toast.LENGTH_LONG).show();
		}
	}
Intent i=new Intent(this,ManageActivity.class);
finish();
startActivity(i);
}

	public void Back(View v)
	{
		Intent i= new Intent(this,TestActivity.class);
		finish();
		startActivity(i);
	}

}
