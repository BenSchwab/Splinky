package com.apphelionstudios.splinky;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

public class TargetReticle extends Sprite {
	
	private BitmapDrawable targetReticle;
	public int x;
	public int y;
	public int width;
	public int height;
	public int turns;
	private EnemySprite target;
	private int alpha;
	private int alphaChange;
	private int switchCount;
	private Bitmap tR;
	private Bitmap tRS;
	private BitmapDrawable holder;
	
	public TargetReticle(Resources resource){
		
		 tR = BitmapFactory.decodeResource(resource, R.drawable.targetreticle);
		 tRS = BitmapFactory.decodeResource(resource, R.drawable.targetreticleswitch);
		targetReticle = new BitmapDrawable(tR);
		turns = 0;
		alpha = 0;
		alphaChange = 15;
		
		
	}

	@Override
	public void draw(Canvas c) {
		switchCount--;
		if(switchCount==0){
			targetReticle = new BitmapDrawable(tR);//avoid new if inefficient
		}
		if(target!=null){
			calcAlpha();
			targetReticle.setAlpha(alpha);
			//Log.e("fail", "target");
			targetReticle.setBounds(target.getX(), target.getY(), target.getX()+target.getWidth(), target.getY()+target.getHeight());
			targetReticle.draw(c);
		}
	}
	private void calcAlpha() {
		alpha+=alphaChange;
		if(alpha>255){
			alphaChange=-alphaChange;
			alpha = 255;
		}
		if(alpha<0){
			alphaChange=-alphaChange;
			alpha = 0;
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
	
		return target.getX();
	}
	@Override
	public int getY() {
		
		return target.getY();
	}
	@Override
	public int getTurns() {
		// TODO Auto-generated method stub
		return turns;
	}
	public void setTarget(EnemySprite target){
		this.target = target;
		if(target!=null){
			targetReticle = new BitmapDrawable(tRS);
			switchCount = Math.max(255/alphaChange,-255/alphaChange);
			alphaChange=Math.min(alphaChange,-alphaChange);//ensures negative
			alpha = 255;
		}
		
	}
	public EnemySprite getTarget(){
		return target;
	}
}
