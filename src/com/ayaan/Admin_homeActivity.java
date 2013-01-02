package com.ayaan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Admin_homeActivity extends Activity {
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.admin_page); 
	 }
	 
	 public void manage_stud(View v)
	 {
		 Intent i=new Intent(this,StudentActivity.class);
		 finish();
         startActivity(i);
	 }
	 public void manage_fac(View v)
	 {
		 Intent i=new Intent(this,FactultyActivity.class);
		 finish();
         startActivity(i);
	 }

	 public void log_out(View v)
	 {
		 Intent i=new Intent(this,AyaanActivity.class);
		 finish();
         startActivity(i);
	 }

}
