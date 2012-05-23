package com.apphelionstudios.splinky;

import com.apphelionstudios.splinky.GameView.Coordinate;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class SpeedUpSprite extends Sprite {
	private Bitmap speedBoost;
	public int x;
	public int y;
	public int width;
	public int height;
	public int turns;
	
	public SpeedUpSprite(Resources resource, Coordinate c){
		speedBoost = BitmapFactory.decodeResource(resource, R.drawable.speed);
		x=c.x;
		y=c.y;
		width = speedBoost.getWidth();
		height = speedBoost.getHeight();
		turns = 0;
		
		
	}
	@Override
	public void draw(Canvas c) {
		c.drawBitmap(speedBoost, x, y, null);
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
