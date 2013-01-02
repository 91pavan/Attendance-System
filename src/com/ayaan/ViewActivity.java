package com.ayaan;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ViewActivity extends ListActivity {
	public String Sccode = AyaanActivity.fccode;
	DBAdapter db;
	Cursor c,c1;
	public ListAdapter adapter;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.att_display);
         db=new DBAdapter(this); 
         try
         {
         	
         	db.open();
         	c1=db.getRecordByName(Sccode);
         	DisplayRecord(c1);
         	Cursor c2 = db.getRecordTotal(Sccode);
         	DisplayRecord1(c2);
         	c=db.getRecordBycode(Sccode);
         	adapter = new SimpleCursorAdapter(
        			this, 
        			R.layout.display, 
        			c, 
        			new String[] {"Usn", "Attended", "Missed"}, 
        			new int[] {R.id.Usn,R.id.Attended,R.id.Missed});
        	setListAdapter(adapter);
        			db.close();
         
         }catch (Exception e) {
			// TODO: handle exception
		}
	}
	 public void DisplayRecord(Cursor c)
     {
         
		  
		  TextView name=(TextView)findViewById(R.id.fac);
		  TextView clg=(TextView)findViewById(R.id.scode);
		  name.setText(c.getString(1));
		  clg.setText(c.getString(3));
		
		 
		  
     }
	 public void DisplayRecord1(Cursor c)
     {
         
		  
		  TextView name=(TextView)findViewById(R.id.total);
		  name.setText(c.getString(2));
		 
		
		 
		  
     }

}
