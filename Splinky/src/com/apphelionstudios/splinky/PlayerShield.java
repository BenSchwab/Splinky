package com.apphelionstudios.splinky;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;

public class PlayerShield {
	
	//TODO: creating poping animation
	private Bitmap shield;
	private PlayerSprite player;
	private BitmapDrawable shieldDrawable;
	private Rect drawArea;
	public PlayerShield(Resources resource, PlayerSprite player){
		 shield = BitmapFactory.decodeResource(resource, R.drawable.sheild);
		 this.player = player;
		 shieldDrawable = new BitmapDrawable(shield);
	}

	public void draw(Canvas c) {
		drawArea = new Rect(player.getX()-7, player.getY()-7, player.getX()+player.getWidth()+7, player.getY()+player.getHeight()+7);
		shieldDrawable.setBounds(drawArea);
		shieldDrawable.draw(c);
	}

}
