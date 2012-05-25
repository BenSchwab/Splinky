package com.apphelionstudios.splinky;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameView extends SurfaceView implements OnTouchListener  {

	//TODO: Change things up to support multiple enemies -- add random tracking for shrink ray
	//TODO: fix teleports -- make enemies running to them die -- shrink and spin out?
	//TODO: fix side bars -- add power ups on both sides
	//TODO: fix graphics

	private final int FPS = 20;
	int itemFrameCounter;
	private int score;
	Handler mHandler;
	private int enemyFrameCounter;


	//bitmaps
	PlayerSprite player;
	EnemySprite badGuyOne;
	private int speedMult;
	private int slowMult;
	private Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.spacebackground);
	private Bitmap border;
	private Bitmap borderHorizontal;
	private SurfaceHolder holder;
	private GameLoopThread gameLoopThread;
	
	private int curTar;
	private TargetReticle targetReticle;
	private int touchBuffer;
	//Multiple badGuys
	private ArrayList<EnemySprite> badGuyLocs;

	//Special Effects

	private ArrayList<SpecialEffect> specialEffects;

	//Items
	private ArrayList<ActiveTeleport> teleportLocs;
	private ArrayList<SpeedUpSprite> speedBoostLocs;
	private ArrayList<SlowDownSprite> speedDropLocs;
	private ArrayList<Sprite> mobilePowerUps;
	private ArrayList<Sprite> powerUpLocs;
	private int itemsVisable;
	private int teleportCount;
	private int itemTime;
	private boolean isNextGreen;

	//board limits
	private int yTop;
	private int yBottom;
	private int xLeft;
	private int xRight;

	//sound effects
	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundPoolMap;
	public static final int SPEED_UP_SOUND = 1;
	public static final int SLOW_DOWN_SOUND = 2;
	public static final int BOUNCE_SOUND =3;
	private static final int FREEZE_POWER_SOUND = 4;
	private static final int SHRINK_SOUND = 5;
	private static final int BACKGROUND_ONE = 1000;

	int teleportTimeOut = 0;


	public GameView(Context context, AttributeSet attrs) {
		super(context,attrs);
		this.setOnTouchListener(this);
		initSounds();
		startNewGame();
		holder = getHolder();
		holder.addCallback(new Callback(){
			@Override
			public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2,
					int arg3) {
			}
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				gameLoopThread.setRunning(true);
				/* Play the sound with the correct volume */
				//soundPool.setLoop(soundPool.play(soundPoolMap.get(BACKGROUND_ONE), volume, volume, 1, 0, 1f),-1); 
				playSound(BACKGROUND_ONE);
				gameLoopThread.start();
				yTop = 0;
				yBottom = getHeight();
				xLeft = 0;
				xRight = getWidth();
			}
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				Log.e("SD", "In surface destroyed");
				boolean retry = true;
				gameLoopThread.setRunning(false);
				while (retry) {
					try {
						gameLoopThread.join();
						retry = false;
					} catch (InterruptedException e) {
					}
				}
			}});


	}

	private void startNewGame() {
		teleportLocs = new ArrayList<ActiveTeleport>();
		speedBoostLocs = new ArrayList<SpeedUpSprite>();
		speedDropLocs = new ArrayList<SlowDownSprite>();
		mobilePowerUps = new ArrayList<Sprite>();
		powerUpLocs = new ArrayList<Sprite>();
		itemsVisable =0;
		teleportCount = 0;
		enemyFrameCounter = 0;
		itemFrameCounter = 0;
		score = 0;
		player = new PlayerSprite(getResources());
		badGuyOne = new EnemySprite(getResources());
		border= BitmapFactory.decodeResource(getResources(), R.drawable.asteroidborder);
		borderHorizontal = BitmapFactory.decodeResource(getResources(), R.drawable.asteroidborderhorizontal);
		badGuyLocs = new ArrayList<EnemySprite>();
		badGuyLocs.add(badGuyOne);
		gameLoopThread = new GameLoopThread(this);
		specialEffects = new ArrayList<SpecialEffect>();
		itemTime =90;
		isNextGreen = false;
		targetReticle = new TargetReticle(getResources());
		targetReticle.setTarget(badGuyOne);
		curTar = 0;
		touchBuffer =0;

	}

	public void upDateSpeed(float xVel, float yVel){
		player.updateSpeed(xVel, yVel);
	}

	@Override
	protected void onDraw(Canvas canvas) {  //this is important -- main hub of activity
		itemClock();
		enemyClock();
		drawBackground(canvas);
		drawTeleports(canvas);
		drawPowerUps(canvas);
		drawSpeed(canvas);
		drawGoodGuy(canvas);
		for(int i =badGuyLocs.size()-1; i>=0; i--){
			EnemySprite bGuy = badGuyLocs.get(i);
			drawBadGuy(bGuy, canvas);
		}
		drawSpecialEffects(canvas);
		//Log.e("abou to call", "test");
		drawTargetReticle(canvas);
		touchBuffer --;

	}

	private void drawTargetReticle(Canvas c) {
		if(!badGuyLocs.contains(targetReticle.getTarget())){
			//Log.e("no target", "no target");
			targetReticle.setTarget(null);
			switchTarget();
		}
		else{
			//Log.e("target", "target");
			targetReticle.draw(c);
		}
		
	}

	private void enemyClock() {
		enemyFrameCounter++;
		if(enemyFrameCounter>FPS*15){
			spawnEnemy();
			enemyFrameCounter =0;
		}

	}

	private void spawnEnemy() {
		badGuyLocs.add(new EnemySprite(getResources()));
		Log.e("enemyadded", ":(");

	}

	private void itemClock() {
		itemFrameCounter++;
		if(itemFrameCounter>FPS*3){
			spawnItem();
			itemFrameCounter =0;
		}

	}

	private void drawSpecialEffects(Canvas canvas) {
		ArrayList<SpecialEffect> toRemove = new ArrayList<SpecialEffect>();
		for(SpecialEffect s: specialEffects){
			if(!s.draw(canvas)){
				toRemove.add(s);
			}
		}
		for(SpecialEffect s: toRemove){
			specialEffects.remove(s);
			Log.e("removed", ""+s.toString());
		}

	}

	private void drawTeleports(Canvas canvas) {
		for(ActiveTeleport a: teleportLocs ){
			a.draw(canvas);
		}

	}

	private void drawPowerUps(Canvas canvas) {
		ArrayList<Sprite> toRemove = new ArrayList<Sprite>();
		for(Sprite c: mobilePowerUps){
			ShrinkRaySprite shrinkRay = (ShrinkRaySprite)c;
			EnemySprite badGuy = shrinkRay.getTarget(); //this is bad..
			double xDiff =((((shrinkRay.x+shrinkRay.width)/2-(badGuy.x+badGuy.width)/2))); //targetting method?
			double yDiff =((((shrinkRay.y+shrinkRay.height)/2-(badGuy.y+badGuy.height)/2)));//targeting method
			double magnitude = Math.pow(xDiff*xDiff+yDiff*yDiff, .5);
			double xUnitVector = -xDiff/magnitude*15;
			double yUnitVector = -yDiff/magnitude*15;
			shrinkRay.x = (int) (shrinkRay.x+xUnitVector); //maybe make an intersect method that tells you if two sprites intersect?
			shrinkRay.y = (int) (shrinkRay.y+yUnitVector);
			if(badGuy.intersects(shrinkRay)){
				playSound(SHRINK_SOUND);
				toRemove.add(c);
				if(badGuy.reduceState()){
					badGuyLocs.remove(badGuy);
				}
			}
			shrinkRay.draw(canvas);
		}
		for(Sprite c: toRemove){
			mobilePowerUps.remove(c);
		}

	}

	private void drawBackground(Canvas canvas) {
		canvas.drawBitmap(background, null, new Rect(0,0,getWidth(),getHeight()), null);
	}

	private void spawnItem(){
		Coordinate itemSpot = new Coordinate((int)(Math.random()*(xRight-border.getWidth()))+border.getWidth(),(int)(Math.random()*(yBottom-borderHorizontal.getHeight()))+borderHorizontal.getHeight());
		itemsVisable++;
		int whichOne = (int)(Math.random()*5);
		switch(whichOne){
		case 0:
			SpeedUpSprite newSpeed = new SpeedUpSprite(getResources(),itemSpot);
			speedBoostLocs.add(newSpeed);
			break;
		case 1:
			SlowDownSprite newSlow = new SlowDownSprite(getResources(),itemSpot);
			speedDropLocs.add(newSlow);
			break;
		case 2:
			FreezePower newFreeze = new FreezePower(getResources(),itemSpot);
			powerUpLocs.add(newFreeze);
			break;
		case 3:
			ShrinkRaySprite newShrink = new ShrinkRaySprite(getResources(),itemSpot,null);
			powerUpLocs.add(newShrink);
		case 4:
			TeleportSprite newTeleport = new TeleportSprite(getResources(),itemSpot);
			powerUpLocs.add(newTeleport);
			break;
		}


	}
	private void drawBadGuy(EnemySprite badGuy, Canvas canvas){
		if(badGuy.freezeCounter>0){
			badGuy.freezeCounter--;
			badGuy.draw(canvas);
			return;
		}
		if(badGuy.xSpeeds.isEmpty()){
			for(int i =0; i<5; i++){
				double xDiff =((((player.x+player.width)/2-(badGuy.x+badGuy.width)/2)));
				double yDiff =((((player.y+player.height)/2-(badGuy.y+badGuy.height)/2)));
				double magnitude = Math.pow(xDiff*xDiff+yDiff*yDiff, .5);
				double xUnitVector = xDiff/magnitude*5;
				double yUnitVector = yDiff/magnitude*5;
				badGuy.xSpeeds.add((int)xUnitVector);
				badGuy.ySpeeds.add((int)yUnitVector);

			}
		}
		badGuy.x = badGuy.xSpeeds.remove() + badGuy.x;
		badGuy.y = badGuy.ySpeeds.remove() + badGuy.y;
		if (badGuy.y >= yBottom - badGuy.height) {
			badGuy.y =  yBottom - badGuy.height;
		}
		if (badGuy.y <= yTop) {
			badGuy.y = yTop;
		}
		if (badGuy.x >= xRight - badGuy.width) {
			badGuy.x= xRight-badGuy.width;
		}
		if (badGuy.x <= xLeft) {
			badGuy.x=xLeft;
		}
		if(badGuyIntersectsTeleport(badGuy)){
			badGuyLocs.remove((badGuy)); //concurrent mod problem?
		}
		else{
			badGuy.draw(canvas);
			if (player.intersects(badGuy))
			{
				pauseGame();
				gameOver();
			} 
		}
	}

	private boolean badGuyIntersectsTeleport(EnemySprite badGuy) {
		for(ActiveTeleport aT: teleportLocs){
			if(aT.intersectCenter(badGuy)){
				specialEffects.add(new BadGuySpecialEffect(getResources(),badGuy));
				return true;
			}
		}
		return false;
	}

	private void gameOver() {
		mHandler.post(new Runnable(){
			@Override
			public void run() {
				AlertDialog.Builder builder = new AlertDialog.Builder((BounceGameActivity)getContext());
				builder.setMessage("Game Over. Would you like to play again?")
				.setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						restartGame();
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						endGame();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});

	}

	private void drawGoodGuy(Canvas canvas) {
		player.x = player.x+ player.xSpeed*player.speedMultiplier;
		player.y = player.y + player.ySpeed*player.speedMultiplier;
		player.x = player.x+ player.bounceSpeedX;
		player.y = player.y + player.bounceSpeedY;

		if (player.y >= yBottom - player.height) {
			player.y =  yBottom - player.height;
			doBounce(3);
		}
		if (player.y<= yTop) { //badCODE
			player.y = yTop;
			doBounce(1);
		}
		if (player.x >= xRight - player.width) { //badCODE
			player.x= xRight-player.width;
			doBounce(4);
		}
		if (player.x<= xLeft) {
			player.x=xLeft;
			doBounce(2);
		}
		player.bounceSpeedX =(int) (player.bounceSpeedX/1.3);
		player.bounceSpeedY= (int) (player.bounceSpeedY/1.3);
		playerIntersectTeleport();
		ArrayList<Sprite> toRemove = new ArrayList<Sprite>();
		for(SpeedUpSprite sBLoc: speedBoostLocs){
			if(sBLoc.intersects(player)){
				toRemove.add(sBLoc);
				speedUpSound();
				itemsVisable--;
				player.speedMultiplier++;
				score++;
				speedMult++;
				slowMult =0;
				String scoreString = ""+score;
				((BounceGameActivity) getContext()).setTextView(scoreString, Math.max(speedMult, slowMult));
				Log.e("trying to", "update score");
				((BounceGameActivity) getContext()).updateOnScreenScore(new Coordinate(player.getX(), player.getY()), "+10");
			}

		}

		for(Sprite remove: toRemove){
			speedBoostLocs.remove(remove);
		}
		toRemove.clear();
		for(SlowDownSprite sBLoc: speedDropLocs){
			if(sBLoc.intersects(player)){
				toRemove.add(sBLoc);
				player.speedMultiplier--;
				itemsVisable--;
				slowDownSound();
				score++;
				speedMult=0;
				slowMult ++;
				String scoreString = ""+score;
				((BounceGameActivity) getContext()).setTextView(scoreString, Math.max(speedMult, slowMult));
				((BounceGameActivity) getContext()).updateOnScreenScore(new Coordinate(player.getX(), player.getY()), "+10");
			}
		}
		for(Sprite remove: toRemove){
			speedDropLocs.remove(remove);
		}
		toRemove.clear();

		for(Sprite p: powerUpLocs){
			if(p instanceof FreezePower){
				FreezePower powerUp = (FreezePower)p;
				//if(powerUp.x>=player.x&&powerUp.x<=player.x+player.width&&powerUp.y>=player.y&&powerUp.y<=player.y+player.height){
				if(powerUp.intersects(player)){
					toRemove.add(powerUp);
					score++;
					String scoreString = ""+score;
					((BounceGameActivity) getContext()).setTextView(scoreString, Math.max(speedMult, slowMult));
					((BounceGameActivity) getContext()).enablePowerUp("freezepower");
				}
			}
			if(p instanceof ShrinkRaySprite){
				ShrinkRaySprite powerUp = (ShrinkRaySprite)p;
				//if(powerUp.x>=player.x&&powerUp.x<=player.x+player.width&&powerUp.y>=player.y&&powerUp.y<=player.y+player.height){
				if(powerUp.intersects(player)){
					toRemove.add(powerUp);
					score++;
					String scoreString = ""+score;
					((BounceGameActivity) getContext()).setTextView(scoreString, Math.max(speedMult, slowMult));
					((BounceGameActivity) getContext()).enablePowerUp("shrinkpower");
				}
			}
			if(p instanceof TeleportSprite){
				TeleportSprite powerUp = (TeleportSprite)p;
				//if(powerUp.x>=player.x&&powerUp.x<=player.x+player.width&&powerUp.y>=player.y&&powerUp.y<=player.y+player.height){
				if(powerUp.intersects(player)){
					toRemove.add(powerUp);
					score++;
					String scoreString = ""+score;
					((BounceGameActivity) getContext()).enablePowerUp("shrinkpower");
					teleportCount++;
				}
			}
		}
		for(Sprite remove: toRemove){
			powerUpLocs.remove(remove);
		}
		toRemove.clear();
		player.draw(canvas);
	}

	private void playerIntersectTeleport() {
		if(teleportTimeOut<1){
			for(ActiveTeleport a: teleportLocs){
				if(a.intersectCenter(player)){
					if(a.isLinked()&&teleportTimeOut<1){
						ActiveTeleport linkedTeleport = a.getLinkedTeleport();
						Log.e("Lt", ""+linkedTeleport.toString());
						player.x = linkedTeleport.getX();
						player.y = linkedTeleport.getY();
						teleportTimeOut = 50;
					}
				}
			}
		}
		else{
			teleportTimeOut--;
		}
	}

	private void drawSpeed(Canvas canvas) {
		ArrayList<Sprite> toRemove = new ArrayList<Sprite>();

		//draw speed up
		for(SpeedUpSprite s: speedBoostLocs){
			if(s.getTurns()>itemTime){
				toRemove.add(s);
			}
			else{
				s.draw(canvas);
			}
		}
		for(Sprite s: toRemove){
			speedBoostLocs.remove(s);
		}
		toRemove.clear();

		//draw slow down
		for(SlowDownSprite s: speedDropLocs){
			if(s.getTurns()>itemTime){
				toRemove.add(s);
			}
			else{
				s.draw(canvas);
			}
		}
		for(Sprite s: toRemove){
			speedDropLocs.remove(s);
		}
		toRemove.clear();

		//draw power ups
		for(Sprite s: powerUpLocs){
			if(s.getTurns()>itemTime){
				toRemove.add(s);
			}
			else{
				s.draw(canvas);
			}
		}
		for(Sprite s: toRemove){
			powerUpLocs.remove(s);
		}
		toRemove.clear();
	}
	//1 = top, 2= right, 3 = bottom, 4 = left
	private void doBounce(int wall){
		playBounceSound();
		switch(wall){
		case 1:
			player.bounceSpeedY = -player.ySpeed*player.speedMultiplier*3;
			break;
		case 2:
			player.bounceSpeedX = -player.xSpeed*player.speedMultiplier*3;
			break;
		case 3:
			player.bounceSpeedY = -player.ySpeed*player.speedMultiplier*3;
			break;
		case 4:
			player.bounceSpeedX = -player.xSpeed*player.speedMultiplier*3;
			break;
		}
		//player.bounceSpeedX = -player.xSpeed*player.speedMultiplier*3;
		//player.bounceSpeedY = -player.ySpeed*player.speedMultiplier*3;
	}
	private void initSounds() {
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
		soundPoolMap = new HashMap<Integer, Integer>();
		soundPoolMap.put(SPEED_UP_SOUND, soundPool.load(getContext(), R.raw.coindrop, 1));
		soundPoolMap.put(SLOW_DOWN_SOUND, soundPool.load(getContext(), R.raw.slowdown, 1));
		soundPoolMap.put(BOUNCE_SOUND,soundPool.load(getContext(), R.raw.bounce, 1));
		soundPoolMap.put(FREEZE_POWER_SOUND,soundPool.load(getContext(), R.raw.freezepowersound, 1));
		soundPoolMap.put(SHRINK_SOUND, soundPool.load(getContext(), R.raw.shrink,1));
		soundPoolMap.put(BACKGROUND_ONE, soundPool.load(getContext(), R.raw.backgroundmusicone,1));
	}
	public void playSound(int sound) {
		/* Updated: The next 4 lines calculate the current volume in a scale of 0.0 to 1.0 */
		AudioManager mgr = (AudioManager)getContext().getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);    
		float volume = streamVolumeCurrent / streamVolumeMax;
		/* Play the sound with the correct volume */
		soundPool.play(soundPoolMap.get(sound), volume, volume, 1, 0, 1f);     
	}
	public void speedUpSound() {
		//playSound(BACKGROUND_ONE);
		playSound(SPEED_UP_SOUND);

	} 
	public void slowDownSound(){
		playSound(SLOW_DOWN_SOUND);
	}
	public void playBounceSound(){
		playSound(BOUNCE_SOUND);
	}
	public void playFreezePowerSound(){
		playSound(FREEZE_POWER_SOUND);
	}
	public void usePowerUp(String powerUp){
		if(powerUp.equals("shrinkpower")){
			if(targetReticle.getTarget()!=null){
			mobilePowerUps.add(new ShrinkRaySprite(getResources(),new Coordinate(player.x,player.y), targetReticle.getTarget()));
			}
		}
		else{
			IceSpecialEffect ice = new IceSpecialEffect(getResources(),player);
			specialEffects.add(ice);
			playFreezePowerSound();
			for(EnemySprite badGuy: badGuyLocs){
				if(ice.intersectsSprite(badGuy)){
					badGuy.freezeCounter = 60; //change to badGuy's in radius
				}
			}
		}
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(touchBuffer>0){
			return false;
		}
		int x = (int) event.getX();
		int y = (int) event.getY();
		touchBuffer = 20;
		Log.e("touchEvent", ""+x);
		Log.e("touchEvent", ""+y);
		if(teleportCount>0){ //do I have any deployable teleports?
			if(teleportLocs.size()==0){
				ActiveTeleport newTele = new ActiveTeleport(getResources(), new Coordinate(x,y), isNextGreen);
				isNextGreen = !isNextGreen;
				teleportLocs.add(newTele);

			}
			else if(teleportLocs.size()==1){//link existing teleport
				ActiveTeleport newTele = new ActiveTeleport(getResources(), new Coordinate(x,y), isNextGreen);
				teleportLocs.add(newTele);
				isNextGreen = !isNextGreen;
				ActiveTeleport oldTele = teleportLocs.get(0);
				oldTele.setLinkedTeleport(newTele);
				newTele.setLinkedTeleport(oldTele);
			}
			else if(teleportLocs.size()==2){//link new teleport with proper color
				ActiveTeleport aT = teleportLocs.get(0);
				if(aT.isGreen()&&isNextGreen||!aT.isGreen()&&!isNextGreen){
					aT = teleportLocs.remove(0);//concurrent mod
				}
				else{
					aT = teleportLocs.remove(1);
				}
				ActiveTeleport oldTele = teleportLocs.get(0);
				ActiveTeleport newTele = new ActiveTeleport(getResources(), new Coordinate(x,y), aT.isGreen());
				teleportLocs.add(newTele);
				isNextGreen = !aT.isGreen();
				oldTele.setLinkedTeleport(newTele);
				newTele.setLinkedTeleport(oldTele);

			}
			teleportCount --;
		}
		return true;
	}

	public void setHandler(Handler m){
		mHandler = m;
	}
	public void endGame(){
		((BounceGameActivity) getContext()).gameOver();
	}
	public void pauseGame(){
		gameLoopThread.setRunning(false);
	}
	public void resumeGame(){
		gameLoopThread.setRunning(true);
	}
	public void restartGame(){
		startNewGame();
		gameLoopThread.start();
		Log.e("game", "restarted");
		gameLoopThread.setRunning(true);
	}

	public void switchTarget() {
		int size = badGuyLocs.size();
		curTar++;
		if(curTar>= size){
			curTar =0;
		}
		if(badGuyLocs.size()>0){
			targetReticle.setTarget(badGuyLocs.get(curTar));
		}
		
	}
}

