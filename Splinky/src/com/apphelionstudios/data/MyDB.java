package com.apphelionstudios.data;

import java.util.ArrayList;
import java.util.HashMap;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class MyDB {
	private SQLiteDatabase db;
	private final Context context;
	private final MyDBhelper dbhelper;
	public MyDB(Context c){
		context = c;
		dbhelper = new MyDBhelper(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
	}
	public void close(){
		db.close();
	}
	public void open()throws SQLiteException{
		try{
			db = dbhelper.getWritableDatabase();
		}catch(SQLiteException ex){
			Log.v("Open databse exception caught", ex.getMessage());
			db = dbhelper.getReadableDatabase();
		}
	}
	public Cursor getMasterList()
	{
		Cursor c = db.query(Constants.TUTORIAL_TABLE, null, null, null, null, null, null);
		return c;
	}
	
	//get Tutorial Table

}
