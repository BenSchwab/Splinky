package com.apphelionstudios.splinky;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;

public class Nuke extends SpecialEffect {

	
	//get bitmaps
	//set size to the canvas size
	//etcs
	
	private BitmapDrawable nuke;
	private int x;
	private int y;
	private int width;
	private int height;
	private int alpha;
	private int alphaChange;
	private Rect effectRegion;
	
	public Nuke(Resources resource){
		Bitmap exp = BitmapFactory.decodeResource(resource, R.drawable.nuke);
		nuke = new BitmapDrawable(exp);
		alpha = 0;
		alphaChange = 50;
	}
	@Override
	public boolean draw(Canvas c) {
		Rect area =  new Rect(0, 0, c.getWidth(), c.getHeight());
		nuke.setBounds(area);
		nuke.setAlpha(alpha);
		nuke.draw(c);
		return calcAlpha();
		
	}
	private boolean calcAlpha() {
		alpha+=alphaChange;
		if(alpha>255){
			alphaChange = -3;
			alpha = 255;
		}
		return alpha>0;
	}

}
