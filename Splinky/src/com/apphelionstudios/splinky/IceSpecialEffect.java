package com.apphelionstudios.splinky;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class IceSpecialEffect extends SpecialEffect {

	private BitmapDrawable freezeGraphic;
	private int x;
	private int y;
	private int width;
	private int height;
	private int alpha;
	private int alphaChange;
	private Rect effectRegion;

	public IceSpecialEffect (Resources resource, PlayerSprite player){
		Bitmap fg = BitmapFactory.decodeResource(resource, R.drawable.iceeffect2);
		freezeGraphic = new BitmapDrawable(fg);
		x=player.getX();
		y=player.getY();
		Rect area = new Rect((x+player.getWidth()/2)-(fg.getWidth()/2), (y+player.getHeight()/2)-(fg.getHeight()/2), (x+player.getWidth()/2)+(fg.getWidth()/2), (y+player.getHeight()/2)+(fg.getHeight()/2));
		effectRegion = area;
		freezeGraphic.setBounds(area);
		alpha = 0;
		alphaChange = 50;
	}


	public boolean draw(Canvas c) {
		freezeGraphic.setAlpha(alpha);
		//Log.e("drawing", "freeze effect");
		freezeGraphic.draw(c);
		return calcAlpha();
	}
	private boolean calcAlpha() {
		alpha+=alphaChange;
		if(alpha>255){
			alphaChange = -5;
			alpha = 255;
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




