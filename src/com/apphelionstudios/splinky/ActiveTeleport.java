package com.apphelionstudios.splinky;

import com.apphelionstudios.splinky.GameView.Coordinate;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class ActiveTeleport extends Sprite {

	private Bitmap greenTeleport;
	private Bitmap blueTeleport;
	public int x;
	public int y;
	public int width;
	public int height;
	public int turns;
	public ActiveTeleport linkedTeleport;
	public boolean isLinked;
	public boolean isGreen;
	public int centerX;
	public int centerY;
	
	public ActiveTeleport(Resources resource, Coordinate c){
		greenTeleport = BitmapFactory.decodeResource(resource, R.drawable.greenactiveteleport);
		blueTeleport = BitmapFactory.decodeResource(resource, R.drawable.blueactiveteleport);
		x=c.x;
		y=c.y;
		width = blueTeleport.getWidth();
		height = blueTeleport.getHeight();
		centerX = (2*getX()+getWidth())/2;
		centerY = (2*getY()+getHeight())/2;
		turns =0;
		isLinked=false;
		isGreen = true;
		
	}
	
	@Override
	public void draw(Canvas c) {
		if(isGreen){
		c.drawBitmap(greenTeleport, x, y, null);
		}
		else{
			c.drawBitmap(blueTeleport, x, y, null);
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
		return turns;
	}
	public void setLinkedTeleport(ActiveTeleport lT){
		linkedTeleport = lT;
		isLinked = true;
		Log.e("linked to",""+lT.toString());
	}
	public boolean isLinked(){
		return isLinked;
	}
	public ActiveTeleport getLinkedTeleport(){
		return linkedTeleport;
	}
	public boolean intersectCenter(Sprite c){
		Rect r = new Rect(centerX, centerY, centerX, centerY);
		Rect r2 = new Rect(c.getX(), c.getY(), c.getX()+c.getWidth(), c.getY()+c.getHeight());
		return r.intersect(r2);
	}
}
