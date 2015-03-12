package com.sysu.niuniuleyuan.function;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;




public class MusicNetWork {
	
	public interface IMusicNetWorkCallBack
	{
		public void musicArrayListCallBack(ArrayList<HashMap<String, String>> arrayList);
		public void musicDownloadCallBack(String fileName);
		public void musicNetWorkErrorCallBack(String errString);
		public void musicDownloadProgress(long totalNum,long sumNum);
	}

	private static final int REQUEST_TIMEOUT = 5000;
	private static final int SOCKET_TIMEOUT = 10000;
	
	private HttpClient mHttpClient = null;
	private boolean mIsHttpDoing = false;
	private IMusicNetWorkCallBack mCallBack = null;
	private boolean mIsHttpCancel = false;
	
	private Runnable mRunnable = null;
	private Handler mHandler = null;
	
	public static String MUSIC_DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CowParadiseMusic/";
	public static String MUSIC_DOWNLOAD_TEMP_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CowParadiseMusic/Temp/";
	
	
	public MusicNetWork(IMusicNetWorkCallBack iCallBack) {
		// TODO Auto-generated constructor stub
		
		mCallBack = iCallBack;
		mHttpClient = getHttpClient();
		
		HandlerThread thread = new HandlerThread("MusicNetWorkHandlerThread"); 
		thread.start();
		mHandler = new Handler(thread.getLooper());
	}
	
	private HttpClient getHttpClient() 
	{
		BasicHttpParams httpParams = new BasicHttpParams();  
		HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, SOCKET_TIMEOUT);
		HttpClient client = new DefaultHttpClient(httpParams);

		return client;
	}

	private HttpEntity doGet(final String url)
	{
		if (mIsHttpDoing)
			return null;
		
		
		String errString = "";
		mIsHttpDoing = true;
		mIsHttpCancel = false;
		
		HttpGet httpRequest = new HttpGet(url);  
		
		try {
			HttpResponse httpResponse = mHttpClient.execute(httpRequest);
			
			if (HttpStatus.SC_OK == httpResponse.getStatusLine().getStatusCode())
			{
				return httpResponse.getEntity();  
			}
			
			errString = httpResponse.getStatusLine().toString();

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			errString = e.getMessage().toString();  
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			errString = e.getMessage().toString();   
			e.printStackTrace();
		}
		
		mIsHttpDoing = false;
		mCallBack.musicNetWorkErrorCallBack(errString);
		
		return null;
	}
	
	public void updateMusicArrayList(final String url) 
	{		
		 mRunnable = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				ArrayList<HashMap<String, String>> arrayList = null;
				
				try {
					HttpEntity httpEntity = doGet(url);
					
					if(null != httpEntity)
					{
						arrayList = parseMusicArrayList(EntityUtils.toString(httpEntity,"utf-8"));
						mIsHttpDoing = false;
						mCallBack.musicArrayListCallBack(arrayList);
					}
					
				
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		};
		
		mHandler.post(mRunnable);
	}
	
	private ArrayList<HashMap<String, String>> parseMusicArrayList(String string)
	{
		ArrayList<HashMap<String, String>> rArrayList = null;
		
		try {
			JSONArray jsonArray = new JSONArray(string);
			
			rArrayList = new ArrayList<HashMap<String,String>>();
			for (int i = 0; i < jsonArray.length(); ++i)
			{
				JSONObject object = (JSONObject) jsonArray.get(i);
				
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(object.getString("name"), object.getString("content"));
				rArrayList.add(map);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rArrayList;
	}
	
	public void downloadMusicFile(final String url,final String fileName) 
	{
		if (!avaiableMedia())
		{
			mCallBack.musicNetWorkErrorCallBack("ÎÞSD¿¨");
			return;
		}
		
 
		final String tempFileName = MUSIC_DOWNLOAD_TEMP_PATH + fileName;
		final String fullFileName = MUSIC_DOWNLOAD_PATH + fileName;
		
		final File file = new File(fullFileName);  
		final File tempFile = new File(tempFileName);
		
		if (file.exists())
		{
			mCallBack.musicDownloadProgress(100, 100);
			mCallBack.musicDownloadCallBack(fullFileName);
			return;
		}
		
		
		mRunnable = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
					HttpEntity httpEntity = doGet(url);
					
					String errString = "";
					
					try
					{
						long length = httpEntity.getContentLength(); 
						InputStream is = httpEntity.getContent(); 
						
						FileOutputStream fileOutputStream = null; 
				        if (is != null) 
				        { 
				        	File destDir = new File(MUSIC_DOWNLOAD_TEMP_PATH);
				        	  if (!destDir.exists()) {
				        	   destDir.mkdirs();
				        	  }
				
				            fileOutputStream = new FileOutputStream(tempFile); 
				                              
				            byte[] buf = new byte[1024]; 
				            int ch = -1; 
				            int count = 0; 
				            while ((ch = is.read(buf)) != -1) { 
				                fileOutputStream.write(buf, 0, ch); 
				                count += ch; 
				                mCallBack.musicDownloadProgress(length, count);
				                
				                if (mIsHttpCancel)
				                {
				                	fileOutputStream.close();
				                	is.close(); 
				                	
				                	return;
				                }
				                	
				            } 
				            
					        fileOutputStream.flush(); 
					        fileOutputStream.close(); 
					        is.close(); 
					        
					        tempFile.renameTo(file);
				        } 
 
				        mIsHttpDoing = false;
				        mCallBack.musicDownloadCallBack(fileName);
				        return;
				        
				    } catch (ClientProtocolException e) { 
				        e.printStackTrace();
				        errString = e.getMessage().toString();  
				    } catch (IOException e) { 
				        e.printStackTrace(); 
				        errString = e.getMessage().toString();  
				    } 
				
				 mIsHttpDoing = false;
				 mCallBack.musicNetWorkErrorCallBack(errString);
			}
		};
		
		mHandler.post(mRunnable);
	} 
	
	
	
	private boolean avaiableMedia()
	{ 
	    String status = Environment.getExternalStorageState(); 
	    return status.equals(Environment.MEDIA_MOUNTED);
	}
	
	public boolean isNetWorkAvaiable() {
		
		return !mIsHttpDoing;
	}
	
	public void cancelDownload() 
	{	
		mIsHttpCancel = true;
		mHandler.removeCallbacks(mRunnable);
	}
}
