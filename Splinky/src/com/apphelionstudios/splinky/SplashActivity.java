package com.apphelionstudios.splinky;




import com.apphelionstudios.splinky.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class SplashActivity extends Activity {


	ImageView logo;
	Boolean animFinished;
	Boolean loadFinished;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		logo = (ImageView) findViewById(R.id.logo_pic);
		ProgressBar loadingProgress = (ProgressBar) findViewById(R.id.splash_progress);
		startAnimating();
		new LoadGameResources(this, loadingProgress).execute();
		animFinished = false;
		loadFinished = false;
	}

	private void startAnimating(){
		Animation fade = AnimationUtils.loadAnimation(this, R.anim.splash_fade_in);
		fade.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationEnd(Animation arg0) {
				animFinished = true;
				startGame();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

		});
		logo.startAnimation(fade);
	}

	private void startGame(){
		if(animFinished&&loadFinished){
			Intent i = new Intent(this, MainMenu.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			this.finish();
			startActivity(i);
		}
	}



	private class LoadGameResources extends AsyncTask<Void, Integer, Void> {

		public LoadGameResources(SplashActivity splashActivity,
				ProgressBar loadingProgress) {
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Void doInBackground(Void... params) {
			GameResources resources =GameResources.getInstance();
			resources.setUpSounds(getBaseContext());//not sure if base context is correct
			return null;
		}
		@Override
		protected void onPostExecute(final Void result) {
			loadFinished= true;
			startGame();
		}

	}
	@Override
	protected void onPause(){
		//finish(); //might stop early quit errors
		super.onPause();
	}



}
