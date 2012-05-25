package com.apphelionstudios.splinky;

import java.util.LinkedList;
import java.util.Queue;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class EnemySprite extends Sprite {
	
	public int x;
	public int y;
	public int width;
	public int height;
	public Bitmap jumboEnemy;//add three sizes of enemy -- shrink ray now kills
	public Bitmap badGuy;
	public Bitmap badGuyFrozen;
	public Bitmap badGuyShrunk;
	private Bitmap badGuyBig;
	public Queue<Integer> xSpeeds = new LinkedList<Integer>();
	public Queue<Integer> ySpeeds = new LinkedList<Integer>();
	public int freezeCounter =0;
	public int shrinkCounter =0;
	public int turns;
	private int state;
	
	public EnemySprite(Resources resource){
		badGuy = BitmapFactory.decodeResource(resource,R.drawable.badguytwo);
		badGuyShrunk = BitmapFactory.decodeResource(resource,R.drawable.badguytwoshrunk);
		badGuyFrozen= BitmapFactory.decodeResource(resource, R.drawable.badguytwofrozen);
		badGuyBig= BitmapFactory.decodeResource(resource, R.drawable.badguytwobig);
		width = badGuy.getWidth();
		height = badGuy.getHeight();
		turns =0;
		state = 2;
	}
	@Override
	public void draw(Canvas c) {
		if(freezeCounter>0){
			c.drawBitmap(badGuyFrozen, x, y, null);
			freezeCounter--;
		}
		else if(state==3){
			c.drawBitmap(badGuyBig, x, y, null);
		}
		else if(state==2){
			c.drawBitmap(badGuy, x, y, null);
		}
		else{
			c.drawBitmap(badGuyShrunk, x, y, null);
		}
	}
	@Override
	public int getWidth() {
		return width;
	}
	@Override
	public int getHeight() {
		return height;
	}
	@Override
	public int getX() {
		return x;
	}
	@Override
	public int getY() {
		return y;
	}
	@Override
	public int getTurns() {
		// TODO Auto-generated method stub
		return turns;
	}
	public boolean isFrozen(){
		return freezeCounter>0;
	}
	public boolean reduceState(){
		state--;
		if(state==0){
			return true;
		}
		reassignValues();
		return false;
	}
	public void reassignValues(){
		switch(state){
		case 3:
			width = badGuyBig.getWidth();
			height = badGuyBig.getHeight();
			break;
		case 2:
			width = badGuy.getWidth();
			height = badGuy.getHeight();
			break;
		case 1:
			width = badGuyShrunk.getWidth();
			height = badGuyShrunk.getHeight();
			break;
		}
	}
	public Bitmap getCurBitmap() {
		switch(state){
		case 3:
			return badGuyBig;
			
		case 2:
			return badGuy;
			
		case 1:
			return badGuyShrunk;
		}
		return null;
	}

}
