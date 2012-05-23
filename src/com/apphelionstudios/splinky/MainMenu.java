package com.apphelionstudios.splinky;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends Activity implements OnClickListener {
	
	Button playGame;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		playGame = (Button)findViewById(R.id.playGameButton);
		playGame.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.playGameButton:
			Intent i = new Intent(this, BounceGameActivity.class);
			startActivity(i);
			break;
		}
			
		
	}

}
