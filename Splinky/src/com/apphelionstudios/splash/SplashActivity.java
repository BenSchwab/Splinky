package com.apphelionstudios.splash;


import com.apphelionstudios.splinky.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

public class SplashActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		startAnimating();
	}

	private void startAnimating(){

	}



	private class LoadGameResources extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}
	}

}
