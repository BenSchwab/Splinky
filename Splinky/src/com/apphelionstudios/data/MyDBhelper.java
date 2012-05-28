package com.apphelionstudios.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBhelper extends SQLiteOpenHelper{
	private static final String CREATE_TUTORIAL_TABLE= "create table " + Constants.TUTORIAL_TABLE+" ("+
	Constants.TUTORIAL_LEVEL+" integer primary key autoincrement);";
	
	/*private static final String CREATE_USER_LISTS= "create table " + Constants.USER_LISTS+" ("+
	Constants.KEY_ID+" integer primary key autoincrement, "+ Constants.LIST_NAME+" text not null, "+ Constants.WORD+" text not null);"; //may have to redo syntax to just take two fields
	private static final String CREATE_STAT_TABLE= "create table " + Constants.STAT_TABLE+" ("+
			Constants.KEY_ID+" integer primary key autoincrement, "+ Constants.UNLOCK_LEVEL+" Integer not null, "+ Constants.SAT_SCORE+" Integer not null);"; 
	private static final String CREATE_ROOT_LIST= "create table " + Constants.ROOT_LIST+" ("+
			Constants.KEY_ID+" integer primary key autoincrement, "+ Constants.ROOT+" text not null, "+Constants.ROOT_DEFINITION+" text not null, "+ Constants.ROOT_WORD+" text not null);";
	private static final String CREATE_DAILY_CHALLENGE= "create table " + Constants.DAILY_CHALLENGE+" ("+
			Constants.KEY_ID+" integer primary key autoincrement, "+ Constants.DATE+" Integer not null);";
	private static final String CREATE_WORD_LIST_VERSION= "create table " + Constants.WORD_LIST_VER+" ("+
			Constants.KEY_ID+" integer primary key autoincrement, "+ Constants.VER+" Integer not null);";
	private static final String CREATE_TUTORIAL= "create table " + Constants.TUTORIAL+" ("+ 
			Constants.KEY_ID+" integer primary key autoincrement, "+ Constants.SCREEN+" text not null);";
			*/
	
	public MyDBhelper(Context context, String name, CursorFactory factory, int version){
		super(context, name, factory, version); 
	} 
	@Override
	public void onCreate(SQLiteDatabase db){
		Log.v("MyDBhelper onCreate","Creating all the tables");
		try{
			db.execSQL(CREATE_TUTORIAL_TABLE);
		}catch(SQLiteException ex){
			Log.e("Create table exception", ex.getMessage());
		}
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		Log.w("TaskDBAdapter", "Upgarding from version " + oldVersion +" to "+newVersion +", which will destory all old data");
		//db.execSQL("drop table if exists " + Constants.TUTORIAL_TABLE); 
		onCreate(db);
	}

}
