package com.apphelionstudios.splinky;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class GameResources {
	
	//sound effect Variables
	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundPoolMap;
	public static final int SPEED_UP_SOUND = 1;
	public static final int SLOW_DOWN_SOUND = 2;
	public static final int BOUNCE_SOUND =3;
	public static final int FREEZE_POWER_SOUND = 4;
	public static final int SHRINK_SOUND = 5;
	public static final int NUKE_SOUND =6;
	public static final int BACKGROUND_ONE = 1000;
	
	 private static GameResources instance = null;
	   public GameResources() {
	      // Exists only to defeat instantiation
	   }
	   public static GameResources getInstance() {
	      if(instance == null) {
	         instance = new GameResources();
	      }
	      return instance;
	   }
	   public void setUpSounds(Context context){
		   	soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
			soundPoolMap = new HashMap<Integer, Integer>();
			soundPoolMap.put(SPEED_UP_SOUND, soundPool.load(context, R.raw.coindrop, 1));
			soundPoolMap.put(SLOW_DOWN_SOUND, soundPool.load(context, R.raw.slowdown, 1));
			soundPoolMap.put(BOUNCE_SOUND,soundPool.load(context, R.raw.bounce, 1));
			soundPoolMap.put(FREEZE_POWER_SOUND,soundPool.load(context, R.raw.freezepowersound, 1));
			soundPoolMap.put(SHRINK_SOUND, soundPool.load(context, R.raw.shrink,1));
			soundPoolMap.put(NUKE_SOUND, soundPool.load(context, R.raw.nukesound,1));
			soundPoolMap.put(BACKGROUND_ONE, soundPool.load(context, R.raw.backgroundmusicone,1));
	   }
	   public HashMap<Integer, Integer> getSoundPoolMap(){
		   return soundPoolMap;
	   }
	   public SoundPool getSoundPool(){
		   return soundPool;
	   }
	   
	
	

}
