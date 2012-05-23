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
	public Queue<Integer> xSpeeds = new LinkedList<Integer>();
	public Queue<Integer> ySpeeds = new LinkedList<Integer>();
	public int freezeCounter =0;
	public int shrinkCounter =0;
	public int turns;
	
	public EnemySprite(Resources resource){
		badGuy = BitmapFactory.decodeResource(resource,R.drawable.badguytwo);
		badGuyShrunk = BitmapFactory.decodeResource(resource,R.drawable.badguytwoshrunk);
		badGuyFrozen= BitmapFactory.decodeResource(resource, R.drawable.badguytwofrozen);
		width = badGuy.getWidth()-10;
		height = badGuy.getHeight()-10;
		turns =0;
	}
	@Override
	public void draw(Canvas c) {
		if(freezeCounter>0){
			c.drawBitmap(badGuyFrozen, x, y, null);
			freezeCounter--;
		}
		else if(shrinkCounter>0){
			c.drawBitmap(badGuyShrunk, x, y, null);
			shrinkCounter--;
		}
		else{
			c.drawBitmap(badGuy, x, y, null);
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

}
