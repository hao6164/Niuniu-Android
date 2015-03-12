package com.sysu.niuniuleyuan.function;

import java.io.File;
import java.io.IOException;

import com.sysu.niuniuleyuan.function.MusicNetWork;

import android.R.string;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;




public class PlayerService extends Service implements OnCompletionListener {
	
	private final IBinder binder = new SrvBinder();  
	
	
	public class SrvBinder extends Binder
	{
		public PlayerService getService()
		{
			return PlayerService.this;
		}
	}
	
	public interface PlayerServiceNotify
	{
		void MusicCompletionNotify();
	}

	public void setOnPlayerServiceNotify(PlayerServiceNotify notify)
	{
		mPlayerServiceNotify = notify;
	}
	
	
	
	
	private PlayerServiceNotify mPlayerServiceNotify = null;
	
	public  MediaPlayer mMediaPlayer = null; 

	
	public PlayerService() {
		// TODO Auto-generated constructor stub
	}

	 
	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		if (null != mPlayerServiceNotify)
		{
			mPlayerServiceNotify.MusicCompletionNotify();
		}
		
	} 


	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return binder;
	}
	
	
	public void onCreate() {  
		super.onCreate();  
		
		if (null == mMediaPlayer) 
		{  
			 mMediaPlayer = new MediaPlayer();  
			 /* 监听播放是否完成 */  
			 mMediaPlayer.setOnCompletionListener(this);  
			 mMediaPlayer.setLooping(true);
		}  

	}  
	
	public void onDestroy() {
		super.onDestroy();  
		
		if (mMediaPlayer != null) 
		{
			mMediaPlayer.stop();  
			mMediaPlayer.release(); 
			mMediaPlayer = null;
		}
	
	}
	
	
	 public void playMusic(String musicName) 
	 { 
		 try {
			 mMediaPlayer.setDataSource(MusicNetWork.MUSIC_DOWNLOAD_PATH + musicName);
			 mMediaPlayer.prepare();
			 
			 
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 mMediaPlayer.start();
	 }
	 
	 public void pauseMusic()
	 {
		 if (null != mMediaPlayer)
		 {
			 mMediaPlayer.pause();
		 }
		
	 }
	 
	 public void resetMusic() {
		 if (null != mMediaPlayer)
		 {
			 mMediaPlayer.reset(); 
		 }
		
	}
	 
	 public int getMusicMax() {
		 if (null != mMediaPlayer)
		 {
			 return mMediaPlayer.getDuration();
		 }
		return 0;
	}
	 
	 public int getMusicCurrentPos() {
		 if (null != mMediaPlayer)
		 {
			 return mMediaPlayer.getCurrentPosition();
		 }
		
		return -1;
	}

	 public void updateMusicPosition(int pos)
	 {
		 if (null != mMediaPlayer)
		 {
			 mMediaPlayer.seekTo(pos);
		 }
		 
	 }
	 
	 public boolean isMusicPlaying()
	 {
		 if (null != mMediaPlayer)
		 {
			 return mMediaPlayer.isPlaying();
		 }
		 return false;
	 }
}
