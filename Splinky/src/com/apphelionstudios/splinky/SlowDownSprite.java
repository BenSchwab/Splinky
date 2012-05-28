package com.apphelionstudios.splinky;



import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class SlowDownSprite extends Sprite {
	private Bitmap speedDrop;
	public int x;
	public int y;
	public int width;
	public int height;
	public int turns;

	public SlowDownSprite(Resources resource, Coordinate c){
		speedDrop = BitmapFactory.decodeResource(resource, R.drawable.space_coin);
		x=c.x;
		y=c.y;
		width = speedDrop.getWidth();
		width = speedDrop.getHeight();
		turns  =0 ;
	}
	
	@Override
	public void draw(Canvas c) {
		c.drawBitmap(speedDrop, x, y, null);	
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
