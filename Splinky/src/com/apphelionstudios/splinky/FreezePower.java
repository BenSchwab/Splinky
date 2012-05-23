package com.apphelionstudios.splinky;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


public class FreezePower extends Sprite {
	private Bitmap freezePower;
	public int x;
	public int y;
	public int width;
	public int height;
	public int turns;

	public FreezePower (Resources resource, com.apphelionstudios.splinky.GameView.Coordinate c){
		freezePower = BitmapFactory.decodeResource(resource, R.drawable.snowflake);
		x=c.x;
		y=c.y;
		width = freezePower.getWidth();
		height = freezePower.getHeight();
		turns =0;
	}
	
	@Override
	public void draw(Canvas c) {
		c.drawBitmap(freezePower, x, y, null);	
		turns++;
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
}
