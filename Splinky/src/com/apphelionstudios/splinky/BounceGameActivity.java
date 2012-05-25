package com.apphelionstudios.splinky;


import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BounceGameActivity extends Activity implements OnClickListener, AnimationListener {


	/** This is the UI thread for the running game**/
	
	private Handler mHandler = new Handler();//used to post notifications from game thread -- questionable implementation
	 // Create runnable for posting
	GameView game;
	TextView scoreBox;
	TextView multBox;
	TextView onScreenScore;
	Button switchTarget;
	Button powerUpOne;
	Button powerUpTwo;
	Button powerUpThree;
	Animation shrink;
	Animation grow;
	Animation fadeOut;
	LinearLayout onScreenScoreLayout;
	public HashMap<Integer, String> powerUps = new HashMap<Integer, String>();
	private int powerUpsEnabled =0;
	//protected PowerManager.WakeLock mWakeLock; //not sure if this works
	//sensor stuff
	private SensorManager sensorManager;
	private Sensor sensor;
	private float xAccel, yAccel, zAccel;
	private SensorEventListener accelerationListener = new SensorEventListener() {
		@Override
		public void onAccuracyChanged(Sensor sensor, int acc) {

		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			xAccel = event.values[0];
			yAccel = event.values[1];
			zAccel = event.values[2];
			refreshDisplay();

		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");//change to flag keep screen on
		//this.mWakeLock.acquire();
		
		setContentView(R.layout.gamelayout);
		scoreBox = (TextView) findViewById(R.id.scoreBox);
		scoreBox.setText("SCORE");
		multBox = (TextView) findViewById(R.id.multBox);
		game =  (GameView) findViewById(R.id.game_view);
		game.setHandler(mHandler);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
		powerUpOne= (Button) findViewById(R.id.buttonOne);
		powerUpOne.setOnClickListener(this);
		powerUpOne.setClickable(false);
		powerUpOne.setVisibility(View.INVISIBLE);
		powerUpTwo= (Button) findViewById(R.id.buttonTwo);
		powerUpTwo.setOnClickListener(this);
		powerUpTwo.setClickable(false);
		powerUpTwo.setVisibility(View.INVISIBLE);
		powerUpThree= (Button) findViewById(R.id.buttonThree);
		powerUpThree.setOnClickListener(this);
		powerUpThree.setClickable(false);
		powerUpThree.setVisibility(View.INVISIBLE);
		shrink = AnimationUtils.loadAnimation(this, R.anim.shrink);
		grow = AnimationUtils.loadAnimation(this, R.anim.grow);
		fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
		switchTarget = (Button) findViewById(R.id.switch_target);
		switchTarget.setOnClickListener(this);
		onScreenScore = (TextView) findViewById(R.id.onScreenScore);
		onScreenScoreLayout = (LinearLayout) findViewById(R.id.onScreenScoreLinearLayout);
		grow.setAnimationListener(this);
	}
	public void setTextView(final String txt, final int mult){
		BounceGameActivity.this.runOnUiThread(new Runnable() {     
			public void run() {         
				scoreBox.setText(txt);  
				scoreBox.startAnimation(grow);
				if((mult+2)/2>0){
					multBox.setText("x"+(mult+2)/2);
				}
			} 
		});
	}
	private void refreshDisplay() {
		game.upDateSpeed(xAccel,yAccel);
		String output = String
				.format("x is: %f / y is: %f / z is: %f", xAccel, yAccel, zAccel);
		//Log.e("Sensors", output);
	}

	public void enablePowerUp(String p){
		if(powerUpsEnabled==3){
			return;
		}
		powerUpsEnabled++;
		final String powerUp = p;
		BounceGameActivity.this.runOnUiThread(new Runnable() {     
			public void run() {  
				if(!powerUpOne.isClickable()){
					enablePowerUp(powerUpOne);  
					if(powerUp.equals("shrinkpower")){ //move these to separate method
						powerUpOne.setBackgroundResource(R.drawable.shrinkraybutton);
						powerUps.put(1, "shrinkpower");
					}
					else{
						powerUpOne.setBackgroundResource(R.drawable.freezepowerbuttonicon);
						powerUps.put(1, "freezepower");
					}
				}
				else if(!powerUpTwo.isClickable()){
					enablePowerUp(powerUpTwo);  
					if(powerUp.equals("shrinkpower")){
						powerUpTwo.setBackgroundResource(R.drawable.shrinkraybutton);
						powerUps.put(2, "shrinkpower");
					}
					else{
						powerUpTwo.setBackgroundResource(R.drawable.freezepowerbuttonicon);
						powerUps.put(2, "freezepower");
					}
		            
				}
				else if(!powerUpThree.isClickable()){
					enablePowerUp(powerUpThree);  
					if(powerUp.equals("shrinkpower")){
						powerUpThree.setBackgroundResource(R.drawable.shrinkraybutton);
						powerUps.put(3, "shrinkpower");
					}
					else{
						powerUpThree.setBackgroundResource(R.drawable.freezepowerbuttonicon);
						powerUps.put(3, "freezepower");
					}
				}

			} 
		});	
	}
	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(accelerationListener, sensor,
				SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	protected void onStop() {
		sensorManager.unregisterListener(accelerationListener);
		super.onStop();
	}
	public void onDestroy() {
		//this.mWakeLock.release();
		super.onDestroy();
	}
	
	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.buttonOne:
			game.usePowerUp(powerUps.get(1));
			disablePowerUp(powerUpOne);
			powerUpsEnabled--;
			break;
		case R.id.buttonTwo:
			game.usePowerUp(powerUps.get(2));
			disablePowerUp(powerUpTwo);
			powerUpsEnabled--;
			break;
		case R.id.buttonThree:
			game.usePowerUp(powerUps.get(3));
			disablePowerUp(powerUpThree);
			powerUpsEnabled--;
			break;
		case R.id.switch_target:
			game.switchTarget();
		}

	}
	public void restartGame(){
		BounceGameActivity.this.runOnUiThread(new Runnable() {     
			public void run() {         
				disablePowerUp(powerUpOne);
				disablePowerUp(powerUpTwo);
				disablePowerUp(powerUpThree);
			} 
		});
	}
	public void gameOver(){
		BounceGameActivity.this.runOnUiThread(new Runnable() {     
			public void run() {         
				finish();
			} 
		});
	}

	public void disablePowerUp(View powerUp){
		powerUp.setVisibility(View.INVISIBLE);
		powerUp.setClickable(false);
	}
	public void enablePowerUp(View powerUp){
		powerUp.setVisibility(View.VISIBLE);
		powerUp.setClickable(true);
	}
	public void updateOnScreenScore(final Coordinate c, final String txt){
		BounceGameActivity.this.runOnUiThread(new Runnable() {   
			
			public void run() {      
				Log.e("updating on screen", "score");
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				layoutParams.setMargins(c.x, c.y, 0, 0);
				((ViewGroup)onScreenScore.getParent()).removeView(onScreenScore);
				onScreenScoreLayout.addView(onScreenScore, layoutParams);
				onScreenScore.setText(txt);  
				onScreenScore.setVisibility(View.VISIBLE);
				onScreenScore.startAnimation(grow);
				onScreenScore.startAnimation(fadeOut);
			} 
		});
	}
	@Override
	public void onAnimationEnd(Animation anim) {
		onScreenScore.setVisibility(View.INVISIBLE);
	}
	@Override
	public void onAnimationRepeat(Animation anim) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAnimationStart(Animation anim) {
		// TODO Auto-generated method stub
		
	}


}