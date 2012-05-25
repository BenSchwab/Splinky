package com.apphelionstudios.splinky;




import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class ShrinkRaySprite extends Sprite {
	private Bitmap shrinkRay;
	public int x;
	public int y;
	public int width;
	public int height;
	public int turns;
	private EnemySprite target;

	public ShrinkRaySprite(Resources resource, Coordinate c, EnemySprite target){
		shrinkRay = BitmapFactory.decodeResource(resource, R.drawable.shrinkray);
		x=c.x;
		y=c.y;
		width = shrinkRay.getWidth();
		height = shrinkRay.getHeight();
		turns =0;
		this.target = target;
	}
	
	@Override
	public void draw(Canvas c) {
		c.drawBitmap(shrinkRay, x, y, null);
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

	public EnemySprite getTarget() {
		// TODO Auto-generated method stub
		return target;
	}

}
