package com.apphelionstudios.splinky;



import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class ActiveTeleport extends Sprite {

	private Bitmap greenTeleport;
	private Bitmap blueTeleport;
	private int x;
	private int y;
	private int width;
	private int height;
	private int turns;
	private ActiveTeleport linkedTeleport;
	private boolean isLinked;
	private boolean isGreen;
	private int centerX;
	private int centerY;
	
	public ActiveTeleport(Resources resource, Coordinate c, boolean isG){
		greenTeleport = BitmapFactory.decodeResource(resource, R.drawable.greenactiveteleport);
		blueTeleport = BitmapFactory.decodeResource(resource, R.drawable.blueactiveteleport);
		x=c.x;
		y=c.y;
		width = blueTeleport.getWidth();
		height = blueTeleport.getHeight();
		centerX = (2*getX()+getWidth())/2;//adjust algorithm
		centerY = (2*getY()+getHeight())/2;//adjust algorithm
		turns =0;
		isLinked=false;
		isGreen = isG;
		
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
	public boolean intersectCenter(Sprite c){ //TODO might need adjustment
		Rect r = new Rect(centerX, centerY, centerX, centerY);
		Rect r2 = new Rect(c.getX(), c.getY(), c.getX()+c.getWidth(), c.getY()+c.getHeight());
		return r.intersect(r2);
	}
	public boolean isGreen(){
		return isGreen;
	}
	public String toString(){
		if(isGreen){
			return "Green";
		}
		else{
			return "Blue";
		}
	}
	
}
