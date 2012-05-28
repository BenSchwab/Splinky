package com.apphelionstudios.results;

import java.util.ArrayList;

import com.apphelionstudios.splinky.R;


import android.app.ListActivity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Results extends ListActivity {

	private ResultAdapter adapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ArrayList<String>stats = getIntent().getStringArrayListExtra("stats");
		ArrayList<GameStat> gameStats = createStats(stats);
		setContentView(R.layout.result_screen);
		adapter = new ResultAdapter(this, R.layout.result_row, gameStats);
		setListAdapter(adapter);
		Log.e("In results", "yep");

	}

	
	private ArrayList<GameStat> createStats(ArrayList<String> stats) {
		GameStat speedBoosts = new GameStat("Speed Boosts "+ stats.get(0), BitmapFactory.decodeResource(getResources(), R.drawable.speed));
		ArrayList<GameStat> gameStats = new ArrayList<GameStat>();
		gameStats.add(speedBoosts);
		gameStats.add(speedBoosts);
		gameStats.add(speedBoosts);
		gameStats.add(speedBoosts);
		gameStats.add(speedBoosts);
		gameStats.add(speedBoosts);
		gameStats.add(speedBoosts);
		return gameStats;
	}


	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		//this.adapter.getItem(position).click(this.getApplicationContext());
	}


	private class ResultAdapter extends ArrayAdapter<GameStat>{
		

	        private ArrayList<GameStat> stats;

	        public ResultAdapter(Context context, int textViewResourceId, ArrayList<GameStat> stats) {
	                super(context, textViewResourceId, stats);
	                this.stats = stats;
	        }

	        @Override
	        public View getView(int position, View convertView, ViewGroup parent) {
	                View v = convertView;
	                if (v == null) {
	                    LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	                    v = vi.inflate(R.layout.result_row, null);
	                }
	                GameStat stat = stats.get(position);
	                if (stat != null) {
	                        TextView text = (TextView) v.findViewById(R.id.stat_text);
	                        ImageView image = (ImageView) v.findViewById(R.id.stat_pic);
	                        if (text != null) {
	                              text.setText("Name: "+stat.getStatText());                            }
	                        if(image != null){
	                              image.setImageBitmap(stat.getStatImage());
	                        }
	                }
	                return v;
	        }

	}


}
