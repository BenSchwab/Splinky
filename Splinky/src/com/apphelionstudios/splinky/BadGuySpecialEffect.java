package com.apphelionstudios.splinky;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class BadGuySpecialEffect extends SpecialEffect {
	private BitmapDrawable deadBadGuy;
	private int x;
	private int y;
	private int alpha;
	private int alphaChange;
	private Rect effectRegion;
	private Animation shrink;

	public BadGuySpecialEffect(Resources resource,EnemySprite badGuy){
		Bitmap dBG = badGuy.getCurBitmap();
		deadBadGuy = new BitmapDrawable(dBG);
		alpha = 255;
		x= badGuy.getX();
		y= badGuy.getY();
		//Log.e("x",""+x);
		//Log.e("y",""+y);
		deadBadGuy.setBounds(x, y, x+dBG.getWidth(), y+dBG.getHeight());
		//deadBadGuy.setBounds(50, 50, 100, 100);
		alphaChange = 5;
		
	}


	public boolean draw(Canvas c) {
		
		deadBadGuy.setAlpha(alpha);
		//Log.e("drawing", "bad effect");
		deadBadGuy.draw(c);
		return calcAlpha();

	}
	private boolean calcAlpha() {
		alpha-=alphaChange;
		if(alpha<0){
			alpha = 0;
		}
		return alpha>0;

	}

	public int getX() {

		return x;
	}

	public int getY() {

		return y;
	}
	public boolean intersectsSprite(Sprite c){
		Rect r2 = new Rect(c.getX(), c.getY(), c.getX()+c.getWidth(), c.getY()+c.getHeight());
		return effectRegion.intersect(r2);
	}
}
