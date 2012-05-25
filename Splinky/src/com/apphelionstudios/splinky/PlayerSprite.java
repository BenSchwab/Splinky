package com.apphelionstudios.splinky;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class PlayerSprite extends Sprite {
	
	private Bitmap goodGuy;
	private Bitmap goodGuy1;
	private Bitmap goodGuy2;
	private Bitmap goodGuy3;
	private Bitmap goodGuy4;
	private Bitmap goodGuy12;
	private Bitmap goodGuy23;
	private Bitmap goodGuy34;
	private Bitmap goodGuy41;
	
	//player variables
	public int x =300;
	public int y=300;
	public int ySpeed = 0;
	public int xSpeed = 0;
	public int speedMultiplier =2;
	public int width;
	public int height;
	public int speedY =0;
	public int bounceSpeedX =0;
	public int bounceSpeedY = 0;
	public int turns;
	
	public PlayerSprite(Resources resources) {
		
				goodGuy = BitmapFactory.decodeResource(resources, R.drawable.goodguy);
				goodGuy1= BitmapFactory.decodeResource(resources, R.drawable.goodguy1);
				goodGuy2 = BitmapFactory.decodeResource(resources, R.drawable.goodguy2);
				goodGuy3= BitmapFactory.decodeResource(resources, R.drawable.goodguy3);
				goodGuy4= BitmapFactory.decodeResource(resources, R.drawable.goodguy4);
				goodGuy12= BitmapFactory.decodeResource(resources, R.drawable.goodguy12);
				goodGuy23= BitmapFactory.decodeResource(resources, R.drawable.goodguy23);
				goodGuy34= BitmapFactory.decodeResource(resources, R.drawable.goodguy34);
				goodGuy41= BitmapFactory.decodeResource(resources, R.drawable.goodguy14);
				width = goodGuy.getWidth();
				height = goodGuy.getHeight();
				turns =0;
	}

	public void draw(Canvas canvas) {
		
		canvas.drawBitmap(goodGuy, x, y, null);
		/*
		if(xSpeed>0&&ySpeed==0 ){ //right
			canvas.drawBitmap(goodGuy4, x-10 , y-10, null);
		}
		else if(xSpeed==0&&ySpeed<0 ){//down
			canvas.drawBitmap(goodGuy3, x-10 , y-10, null);
		}
		else if(xSpeed==0&&ySpeed>0 ){//up
			canvas.drawBitmap(goodGuy1, x-10 , y-10, null);
		}
		else if(xSpeed<0&&ySpeed==0 ){ //left
			canvas.drawBitmap(goodGuy2, x-10 , y-10, null);
		}
		else if(xSpeed>0&&ySpeed>0 ){ //right up
			canvas.drawBitmap(goodGuy41, x-10 , y-10, null);
		}
		
		else if(xSpeed>0&&ySpeed<0 ){//right down
			canvas.drawBitmap(goodGuy34, x -10, y-10, null);
		}
		else if(xSpeed<0&&ySpeed<0 ){//left down
			canvas.drawBitmap(goodGuy23, x-10 , y-10, null);
		}
		else if(xSpeed<0&&ySpeed>0 ){//left up
			canvas.drawBitmap(goodGuy12, x-10 , y-10, null);
		}
		else{//stationary
			canvas.drawBitmap(goodGuy, x, y, null);
		}
		*/
	}
	
	public void updateSpeed(float x, float y){
		xSpeed = (int)y;
		ySpeed = (int)x;
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
