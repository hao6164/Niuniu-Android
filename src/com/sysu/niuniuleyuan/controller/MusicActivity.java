package com.sysu.niuniuleyuan.controller;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.sysu.niuniuleyuan.R;
import com.sysu.niuniuleyuan.R.color;
import com.sysu.niuniuleyuan.function.PlayerService;
import com.sysu.niuniuleyuan.function.PlayerService.SrvBinder;
import com.sysu.niuniuleyuan.function.MusicNetWork;
import com.sysu.niuniuleyuan.function.MusicNetWork.IMusicNetWorkCallBack;
import com.sysu.niuniuleyuan.function.URLAddress;

import android.R.integer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;



public class MusicActivity extends Activity implements IMusicNetWorkCallBack,OnClickListener,OnSeekBarChangeListener{
	
	private MusicNetWork mMusicNetWork = null;
	private final String MUSIC_LIST_URL = URLAddress.MainUrl + "music.php";
	
	private ListView mListView = null;
	private ArrayList<HashMap<String, String>> mMusicTitleArrayList = new ArrayList<HashMap<String,String>>();
	private int mCurSongId = 0;
	
	private Handler mHandler = null;
	
	private enum UPDEATE_TYPE{E_ListView,E_DownloadFinish,E_DownLoadProgress,E_ErrorInfo};
	
	private ProgressDialog mProgressDialog = null;
	
	
	private static boolean mIsPlaying = false;
	private ImageButton mPlayBtn = null;
	
	private SeekBar mSeekBar = null;
	private PlayerService mPlayerService = null;
	private Handler mSeekBarHandler = null;
	private Runnable mSeekBarRunnable = null;
	
    private ServiceConnection mSrvConn = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			SrvBinder sBinder = (SrvBinder)service;
			mPlayerService = sBinder.getService();
			if (mPlayerService.isMusicPlaying())
			{
				updateSeekBar();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			mPlayerService = null;
		}
    	
    };
	
	public MusicActivity() {
		// TODO Auto-generated constructor stub

		mMusicNetWork = new MusicNetWork(this);
		
	}
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.music);
		
 
		mSeekBarHandler = new Handler();
	    mSeekBar = (SeekBar)findViewById(R.id.seekBar1);
		mSeekBar.setOnSeekBarChangeListener(this);
		
        Intent intent = new Intent(MusicActivity.this,PlayerService.class);
        startService(intent);
        bindService(intent, mSrvConn, Service.BIND_AUTO_CREATE);
        
        initPlayBtn();
        prePlayBtn();
        nextPlayBtn();
	
		initProgressDialog();
		initBackButton();
		initListView();
		mMusicNetWork.updateMusicArrayList(MUSIC_LIST_URL);
		
		mHandler = new Handler(){
			
			public void handleMessage(Message msg)
			{
				super.handleMessage(msg);
				
				if (UPDEATE_TYPE.E_ListView.ordinal() == msg.what)
				{
					refreshListView();
				}
				else if (UPDEATE_TYPE.E_DownloadFinish.ordinal() == msg.what)
				{
					openPlay();
				}
				else if (UPDEATE_TYPE.E_DownLoadProgress.ordinal() == msg.what)
				{
					mProgressDialog.setProgress(msg.arg1);
				}
				else if (UPDEATE_TYPE.E_ErrorInfo.ordinal() == msg.what)
				{
					Toast.makeText(getApplicationContext(), (CharSequence) msg.obj,
								     Toast.LENGTH_SHORT).show();
				}

			}
		};
		

		
	}
	
	private void initBackButton() {
		Button button = (Button)findViewById(R.id.music_button1);
		button.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mSeekBarHandler.removeCallbacks(mSeekBarRunnable);
				
				mPlayerService.resetMusic();
	        	unbindService(mSrvConn);
	        	
	        	Intent intent = new Intent(MusicActivity.this,PlayerService.class);
	        	stopService(intent);
	        	
				mMusicNetWork.cancelDownload();
				mProgressDialog.dismiss();
				finish();
			}
		});
	}
	
	private void initProgressDialog() 
	{
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setMax(100);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	}
	
	

	@Override
	public void musicArrayListCallBack(ArrayList<HashMap<String, String>> arrayList) {
		// TODO Auto-generated method stub
		if (null == arrayList)
			return;
		
		mMusicTitleArrayList = arrayList;
		mHandler.sendEmptyMessage(UPDEATE_TYPE.E_ListView.ordinal());
	}

	@Override
	public void musicDownloadCallBack(String fileName) {
		// TODO Auto-generated method stub
		
		mProgressDialog.dismiss();
		
		Message message = new Message();
		message.what = UPDEATE_TYPE.E_DownloadFinish.ordinal();
		message.obj = fileName;
		
		mHandler.sendMessage(message);
	}

	@Override
	public void musicNetWorkErrorCallBack(String errString) {
		// TODO Auto-generated method stub
		
		Message message = new Message();
		message.what = UPDEATE_TYPE.E_ErrorInfo.ordinal();
		message.obj = errString;
		
		mHandler.sendMessage(message);
	}

	@Override
	public void musicDownloadProgress(long totalNum, long sumNum) {
		// TODO Auto-generated method stub
		
		Message message = new Message();
		message.what = UPDEATE_TYPE.E_DownLoadProgress.ordinal();
		message.arg1 = (int) (sumNum * 100 / totalNum);
		
		mHandler.sendMessage(message);
	}
	
	
    public final class ViewHolder
    {
    	public TextView titleView;
    }

    private class ItemAdapter extends BaseAdapter
    {
    	private LayoutInflater mInflater;
    	
    	public ItemAdapter(Context context)
    	{
    		this.mInflater = LayoutInflater.from(context);
    	}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mMusicTitleArrayList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mMusicTitleArrayList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
		public void updateData()
		{
			notifyDataSetChanged();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			ViewHolder holder = null;
			if (convertView == null) 
			{
				 holder = new ViewHolder(); 
				 convertView = mInflater.inflate(R.layout.music_item, null);
				 holder.titleView = (TextView)convertView.findViewById(R.id.musicItemTextView1);
				 convertView.setTag(holder);             
			}
			else 
			{
				 holder = (ViewHolder)convertView.getTag();
			}
			
			if (mCurSongId == getItemId(position))
			{
				holder.titleView.setTextColor(Color.YELLOW);
			}
			else 
			{
				holder.titleView.setTextColor(Color.WHITE);
			}
			
			HashMap<String, String> hashMap = mMusicTitleArrayList.get((int) getItemId(position));
			holder.titleView.setText(getMusicName(hashMap));
			
			return convertView;
		}
 
    	
    }

    private void initListView()
    {    	
       ItemAdapter itemAdapter = new ItemAdapter(this);
    	 
    
       	mListView = (ListView)findViewById(R.id.musicListView1); 	
       	mListView.setAdapter(itemAdapter);
       	mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				//if ((int)id != mCurSongId)
				{		
					
					if(mMusicNetWork.isNetWorkAvaiable())
					{
						mCurSongId = (int)id;
						refreshListView();
					}
						
					
					startDownloadMusic();
					
				}
			}
    		
		});    	
    }
    
    private void startDownloadMusic()
    {
		if(mMusicNetWork.isNetWorkAvaiable())
		{
		
			HashMap<String, String> map = mMusicTitleArrayList.get(mCurSongId); 
			
			
			mProgressDialog.setTitle(getMusicName(map) + ",正在下载中请稍候...");
			mProgressDialog.show();
			
			mMusicNetWork.downloadMusicFile(URLAddress.MainUrl + getMusicDownloadUrl(map), getMusicName(map));

		}
		else
		{
			if (mProgressDialog.isShowing())
			{
				mProgressDialog.hide();
			}
			else 
			{
				mProgressDialog.show();
			}
		}
    }
    
    private void refreshListView() 
    {
    	((ItemAdapter)mListView.getAdapter()).updateData();
	}
 
    private String getMusicName(HashMap<String, String> aMap)
    {
		Iterator iter = aMap.entrySet().iterator();
		Map.Entry entry = (Map.Entry) iter.next();
		return (String) entry.getKey();
    }
    
    private String getMusicDownloadUrl(HashMap<String, String> aMap)
    {
		Iterator iter = aMap.entrySet().iterator();
		Map.Entry entry = (Map.Entry) iter.next();
		return (String) entry.getValue();
    }
    

    private String getCurMusicName() 
    {
    	HashMap<String, String> map = (HashMap<String, String>)mMusicTitleArrayList.get(mCurSongId);
    	return getMusicName(map);
	}
    
    private void initPlayBtn()
    {
    	mPlayBtn = (ImageButton)findViewById(R.id.imageButton2);
    	mPlayBtn.setOnClickListener((OnClickListener) this);
    	
    	if (!mIsPlaying)
    	{
    		mPlayBtn.setImageResource(R.drawable.music_play);
    	}
    	else
    	{	
    		mPlayBtn.setImageResource(R.drawable.music_pause);
		}
    }
    
    private void openPlay()
    {
    	refreshListView();
    	
		mPlayerService.resetMusic();
		mIsPlaying = false;
		
		
		mSeekBar.setProgress(0);
		
		onClick(mPlayBtn);
	
    }
    
    private void setPreSong()
    {
    	int preId = mCurSongId - 1 < 0 ? mMusicTitleArrayList.size() - 1 : mCurSongId - 1;
    	mCurSongId = preId;
    }
    
    private void prePlayBtn()
    {
    	ImageButton imgBtn = (ImageButton)findViewById(R.id.imageButton1);
    	imgBtn.setOnClickListener(new ImageButton.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				setPreSong();
				
				if (isMusicFileExist(getCurMusicName()))
				{
					openPlay();
				}
				else
				{
					startDownloadMusic();
				}
			}
		});
    }
    
    boolean isMusicFileExist(String fileName)
    {
    	File file = new File(MusicNetWork.MUSIC_DOWNLOAD_PATH + fileName);
    	return file.exists();
    }
    
    private void setNextSong()
    {
    	int nextId = mCurSongId + 1 > mMusicTitleArrayList.size() - 1 ? 0 : mCurSongId + 1;
    	mCurSongId = nextId;
    }
    
    private void nextPlayBtn()
    {
    	ImageButton imgBtn = (ImageButton)findViewById(R.id.imageButton3);
    	imgBtn.setOnClickListener(new ImageButton.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				setNextSong();
				
				if (isMusicFileExist(getCurMusicName()))
				{
					openPlay();
				}
				else
				{
					startDownloadMusic();
				}
				
			}
		});
    }
	
    @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (mIsPlaying)
		{
			mPlayBtn.setImageResource(R.drawable.music_play);
			
			mPlayerService.pauseMusic();
			mIsPlaying = false;
		}
		else 
		{
			mPlayBtn.setImageResource(R.drawable.music_pause);
			
			mPlayerService.playMusic(getCurMusicName());
			mIsPlaying = true;
	
			
			updateSeekBar();
		}
	}

	private void updateSeekBar()
	{
		mSeekBar.setMax(mPlayerService.getMusicMax());
		
		mSeekBarRunnable = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				mSeekBar.setProgress(mPlayerService.getMusicCurrentPos());
				mHandler.postDelayed(this, 1000);
			}
		};

		mSeekBarHandler.post(mSeekBarRunnable);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		if (fromUser)
		{
			mPlayerService.updateMusicPosition(progress);
		}
		
	}


	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
	
	public static void deleteMusicFiles()
	{
		File file = new File(MusicNetWork.MUSIC_DOWNLOAD_PATH);
		
	    if (file.isFile()) {                                   
	        file.delete();                                     
	        return;                                            
	    }                                                      
	                                                           
	    if(file.isDirectory())
	    {                                
	        File[] childFiles = file.listFiles();              
	        if (childFiles == null || childFiles.length == 0) {
	            file.delete();                                 
	            return;                                        
	        }                                                  
	                                                           
	        for (int i = 0; i < childFiles.length; i++) {      
	            childFiles[i].delete();                         
	        }                                                  
	        file.delete();                                     

	    }
	}

}
