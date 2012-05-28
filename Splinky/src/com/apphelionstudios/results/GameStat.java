package com.apphelionstudios.results;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class GameStat   {

	/**
	 * 
	 */
	
	String statText;
	Bitmap icon;
	private int test;
	
	public GameStat(String text, Bitmap icon){
		this.statText = text;
		this.icon = icon;
	}
	
	public String getStatText(){
		return statText;
	}
	
	public Bitmap getStatImage(){
		return icon;
	}



	
	

}
