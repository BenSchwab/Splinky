package com.apphelionstudios.splinky;



import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class TeleportSprite extends Sprite {
	
	private Bitmap teleport;
	private int x;
	private int y;
	private int width;
	private int height;
	private int turns;
	
	public TeleportSprite(Resources resource, Coordinate c){
		teleport = BitmapFactory.decodeResource(resource, R.drawable.teleportdrop);
		x=c.x;
		y=c.y;
		width = teleport.getWidth();
		height = teleport.getHeight();
		turns =0;
		
		
	}
	
	@Override
	public void draw(Canvas c) {
		c.drawBitmap(teleport, x, y, null);	
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
