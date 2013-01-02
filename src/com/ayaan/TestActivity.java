package com.ayaan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TestActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage);
	}
	public void log_out(View v)
	{
		
		Intent i = new Intent(this,AyaanActivity.class);
		finish();
		startActivity(i);
	}
	public void manage_stud(View v)
	{
		Intent i = new Intent(this,ManageActivity.class);
		finish();
		startActivity(i);
	}
	public void mark_att(View v)
	{
		Intent i = new Intent(this,AttendanceActivity.class);
		startActivity(i);
	}
	public void view_att(View v)
	{
		Intent i = new Intent(this,ViewActivity.class);
		startActivity(i);
	}
		
}
