package com.apphelionstudios.splinky;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;

public abstract class Sprite {
	
	public abstract void draw (Canvas c);
	public boolean intersects(Sprite c) {
		Rect r = new Rect(getX(), getY(), getX()+getWidth(), getY()+getHeight());
		Rect r2 = new Rect(c.getX(), c.getY(), c.getX()+c.getWidth(), c.getY()+c.getHeight());
		return r.intersect(r2);
	}
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract int getX();
	public abstract int getY();
	public abstract int getTurns();
	public Coordinate getCoordinate(){
		return new Coordinate(getX(), getY());
	}

}
