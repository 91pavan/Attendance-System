package com.ayaan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter 
{
    public static final String KEY_ROWID = "_id";
    public static final String KEY_FACNAME = "Fname";
    public static final String KEY_PASSWORD = "Password";
	public static final String KEY_CODE = "Code";
	public static final String KEY_PHONE = "Phone";
	public static final String KEY_USN = "Usn";
	public static final String KEY_SNAME = "Sname";
	public static final String KEY_ATTEND = "Attended";
	public static final String KEY_MISSED = "Missed";
	public static final String KEY_CHECKBOX = "Check";
	public static final String KEY_TOTAL = "Total";
	public static final String KEY_ADMIN = "Admin";
	public static final String KEY_PASS1 = "Pass1";
	public static final String KEY_PASS2 = "Pass2";
    private static final String TAG = "STUDB";
    private static final String DATABASE_NAME1 = "Student1";
    private static final String DATABASE_TABLE1 = "fac_info";
    private static final String DATABASE_TABLE2 = "stu_info";
    private static final String DATABASE_TABLE3 = "class_total";
    private static final String DATABASE_TABLE4 = "admin_values";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
        "create table fac_info (_id integer primary key autoincrement, Fname text not null, Password text not null, Code text not null);";
        
    private static final String DATABASE_CREATE1 =
        "create table stu_info (_id integer primary key autoincrement, Sname text not null, Usn text not null, Code text not null, Attended integer, Missed integer, Phone text not null);";
        
    private static final String DATABASE_CREATE2 =
        "create table class_total (_id integer primary key autoincrement, Code text not null,Total integer);";
       
    private static final String DATABASE_CREATE3 =
        "create table admin_values (_id integer primary key autoincrement, Admin text not null, Pass1 text not null, Pass2 text not null);";
       
    
    
    private final Context context; 
    
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) 
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
    private static class DatabaseHelper extends SQLiteOpenHelper 
    {
        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME1, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
        	long x;
            db.execSQL(DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE1);
            db.execSQL(DATABASE_CREATE2);
            
            
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, 
        int newVersion) 
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion 
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS PESITMSESTUDENT");
            onCreate(db);
        }
    }    
    //---opens the database---
    public DBAdapter open() throws SQLException 
    {
    	Log.i(TAG, "opening the database");
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---    
    public void close() 
    {
    	Log.i(TAG, "closing the database");
        DBHelper.close();
    }
    
    //---insert a title into the database---
    public long insertRecord(String fname, String pass, String code) 
    {
    	long x;
    	
    	Log.i(TAG, "INSERTING A FACULTY RECORD");
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_FACNAME, fname);
        initialValues.put(KEY_PASSWORD, pass);
        initialValues.put(KEY_CODE, code);
        x=  db.insert(DATABASE_TABLE1, null, initialValues);
        
        return x;
    }
    
    public long insertStudentRecord(String sname, String usn, String code,  int att, int miss, String phone) 
    {
    	long x;
    	
    	Log.i(TAG, "INSERTING A STUDENT RECORD");
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_SNAME, sname);
        initialValues.put(KEY_USN, usn);
        initialValues.put(KEY_CODE, code);
        initialValues.put(KEY_ATTEND, att);
        initialValues.put(KEY_MISSED, miss);
        initialValues.put(KEY_PHONE, phone);
        
        x=  db.insert(DATABASE_TABLE2, null, initialValues);
        
        return x;
    }
  
    public long insertClass(String Code, int Count) 
    {
    	long x;
    	
    	Log.i(TAG, "INSERTING A COUNT RECORD");
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CODE, Code);
        initialValues.put(KEY_TOTAL, Count);
        x=  db.insert(DATABASE_TABLE3, null, initialValues);
        return x;
    }
    
  //---retrieves all the student records---
    public Cursor getAllRecords() 
    {
        return db.query(DATABASE_TABLE1, new String[] {
        		KEY_ROWID, 
        		KEY_FACNAME,
        		KEY_PASSWORD,
                KEY_CODE}, 
                null, 
                null, 
                null, 
                null, 
                null);
    }
    
    public Cursor getAllRecordsBy() 
    {
        return db.query(DATABASE_TABLE2, new String[] {
        		KEY_ROWID,
        		KEY_SNAME, 
        		KEY_USN,
        		KEY_CODE,
        		KEY_ATTEND,
        		KEY_MISSED,
        		KEY_PHONE},
                null, 
                null, 
                null, 
                null, 
                null);
    }
    
  //---retrieves a particular student record---
    public Cursor getRecordByName(String code) throws SQLException 
    {
    	Log.i("Ayaan", "GETTING STUDENT RECORD");
    	Cursor mCursor =
                db.query(true, DATABASE_TABLE1, new String[] {
                		KEY_ROWID,
                		KEY_FACNAME, 
                		KEY_PASSWORD,
                		KEY_CODE
                		}, 
                		KEY_CODE + "=" +"\""+ code+"\"", 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
   
    public Cursor getCount(String code) throws SQLException 
    {
    	Log.i("Ayaan", "GETTING STUDENT RECORD");
    	Cursor mCursor =
                db.query(true, DATABASE_TABLE3, new String[] {
                		KEY_ROWID,
                		KEY_CODE, 
                		KEY_TOTAL
                		}, 
                		KEY_CODE + "=" +"\""+ code+"\"", 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    
    
    public Cursor getRecordByUsn(String Code, String usn) throws SQLException 
    {
    	Log.i("Ayaan", "getRecordByUsn()");
    	Cursor mCursor;
    	mCursor =
                db.query(DATABASE_TABLE2, new String[] {
                		KEY_ROWID,
                		KEY_SNAME, 
                		KEY_USN,
                		KEY_CODE,
                		KEY_ATTEND,
                		KEY_MISSED,
                		KEY_PHONE
                		}, 
                		 KEY_CODE +"=?" +" AND " + KEY_USN +"=?",
                         new String[] {Code, usn}, 
                		null, 
                		null, 
                		null);
      
        if (mCursor != null) {
        	Log.i("Ayaan", "cursor not null");
            mCursor.moveToFirst();
        } 
        return mCursor;
    }
    public Cursor getRecordBycode(String code) throws SQLException 
    {
    	Log.i("Ayaan", "GETTING STUDENT RECORD");
    	Cursor mCursor =
                db.query(true, DATABASE_TABLE2, new String[] {
                		KEY_ROWID,
                		KEY_SNAME, 
                		KEY_USN,
                		KEY_CODE,
                		KEY_ATTEND,
                		KEY_MISSED,
                		KEY_PHONE
                		}, 
                		KEY_CODE + "=" +"\""+ code+"\"", 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    
    public Cursor getRecordTotal(String code) throws SQLException 
    {
    	Log.i("Ayaan", "GETTING STUDENT RECORD");
    	Cursor mCursor =
                db.query(true, DATABASE_TABLE3, new String[] {
                		KEY_ROWID,
                		KEY_CODE,
                		KEY_TOTAL
                		}, 
                		KEY_CODE + "=" +"\""+ code+"\"", 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    
    
    public Cursor getRecordById(int id) throws SQLException 
    {
    	Log.i("Ayaan", "GETTING STUDENT RECORD by id");
    	Cursor mCursor =
                db.query(true, DATABASE_TABLE2, new String[] {
                		KEY_ROWID,
                		KEY_SNAME, 
                		KEY_USN,
                		KEY_CODE,
                		KEY_ATTEND,
                		KEY_MISSED,
                		KEY_PHONE
                		}, 
                		KEY_ROWID +"="+id, 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    
    public void updateattend(int id,int new_att) throws SQLException
    {

    	ContentValues values = new ContentValues();
		
		values.put(KEY_ATTEND,new_att);
    db.update(DATABASE_TABLE2, values, KEY_ROWID+"="+id, null);
    }
    
    public void updatemissed(int id,int new_miss) throws SQLException
    {

    	ContentValues values = new ContentValues();
		
		values.put(KEY_MISSED,new_miss);
    db.update(DATABASE_TABLE2, values, KEY_ROWID+"="+id, null);
    }
    
    
    public void updatecount(int id,int count) throws SQLException
    {
    	ContentValues values = new ContentValues();
		values.put(KEY_TOTAL,count);
    db.update(DATABASE_TABLE3, values, KEY_ROWID+"="+id, null);
    }    
    
    public void deleteStudentRecord(int id){
    	Log.i("ROOPA", "Trying To Delete Record");
    	
    	db.delete(DATABASE_TABLE2, KEY_ROWID+"="+id, null);
    }
}
