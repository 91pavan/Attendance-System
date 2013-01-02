package com.ayaan;

import java.util.ArrayList;



import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class AttendanceActivity extends ListActivity {
	public String Sccode = AyaanActivity.fccode;
	public String[] usn;
	public int[] sid;
	public int[] missed;
	public int j=0;
	DBAdapter db;
	Cursor c;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_att);
   
        
        db=new DBAdapter(this); 
        try
        {
        	
        	db.open();
        	c=db.getRecordBycode(Sccode);
        	 
           
        	 usn=new String[c.getCount()];
        	 sid=new int[c.getCount()];
        	 missed=new int[c.getCount()];
        	 
        	 for(int i=0;i<c.getCount();++i)
        	 {
        		 usn[i]=c.getString(2);
        			sid[i]=c.getInt(0);
        		c.moveToNext(); 
        	 }
        	 db.close();
        	 
        }
        catch(Exception e)
        {
        	Toast.makeText(getApplicationContext(), "Could not retrieve Student Details", Toast.LENGTH_LONG).show();
        }
       try{ 
          setListAdapter(new ArrayAdapter<String>(this,
  				android.R.layout.simple_list_item_checked, new ArrayList()));     
        		
          new AddStringTask().execute();
       }
       catch(Exception e)
       {
    	   Toast.makeText(getApplicationContext(), "Null", Toast.LENGTH_LONG).show();
       }
       
	}
	

	
	class AddStringTask extends AsyncTask<Void, String, Void> {
		@Override
		protected Void doInBackground(Void... unused) {
			for(int i=0;i<usn.length;i++){
				publishProgress(usn[i]);
				
			}

			return (null);
		}

		@Override
		protected void onProgressUpdate(String... item) {
			((ArrayAdapter) getListAdapter()).add(item[0]);
		}

		@Override
		protected void onPostExecute(Void unused) {
			setSelection(3);
			
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
	    CheckedTextView check = (CheckedTextView)v;
	    check.setChecked(!check.isChecked());
	    
	    if(check.isChecked())
	    {
	    	missed[j]= sid[position];
	    	
	       j++;
	    }
	    else
	    {
	    	int k=j;
	    	j--;
	    	int i;
	    	for(i=0;i<k;++i)
	    	{
	    		if(missed[i]==sid[position])
	    			break;
	    	}
	    	for(;i<k;i++)
	    		missed[i]=missed[i+1];
	    }
	    	
	    	
	}

	public void submit(View v)
	{
		int flag=0;
		Cursor c1;
		int attended,missed;
		String phone;
		
		try{
			db.open();
			Cursor c2 = db.getCount(Sccode);
			int id2 = c2.getInt(0);
			int count = c2.getInt(2);
			
			db.updatecount(id2, count+1);
			for(int i=0;i<sid.length;++i)
			{
				c1=db.getRecordById(sid[i]);
				flag=compare(sid[i]);
				if(flag==0)
				{
					missed=c1.getInt(5);
					phone=c1.getString(6);
					try{
						sendSMS("5556",phone);
						}
					catch(Exception e)
					{
						Toast.makeText(getApplicationContext(), ""+e+"", Toast.LENGTH_LONG).show();
						return;
					}
					db.updatemissed(sid[i], missed+1);
				}
				else
				{
					attended=c1.getInt(4);
					db.updateattend(sid[i], attended+1);
				}
			}
			Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_LONG).show();
			db.close();
			}catch (Exception e) {
				Toast.makeText(getApplicationContext(), "exception", Toast.LENGTH_LONG).show();
			}
			
			Intent i=new Intent(AttendanceActivity.this,TestActivity.class);
			finish();
			startActivity(i);
	}
	public int compare(int sid)
	{
		for(int k=0;k<j;k++)
		{
			if(sid==missed[k])
			{
				
				return 0;
			}
			
		}
		
		return 1;
	}
	public void sendSMS(String phoneNumber, String message)
	 {        
	        String SENT = "SMS_SENT";
	        String DELIVERED = "SMS_DELIVERED";
	 
	        PendingIntent sentPI = PendingIntent.getBroadcast(AttendanceActivity.this, 0,new Intent(SENT), 0);
	 
	        PendingIntent deliveredPI = PendingIntent.getBroadcast(AttendanceActivity.this, 0,new Intent(DELIVERED), 0);
	 
	        SmsManager sms = SmsManager.getDefault();
	        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);   
	        Toast.makeText(getApplicationContext(), "Message Sent!", Toast.LENGTH_LONG).show();
	    }
}
