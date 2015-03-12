package com.sysu.niuniuleyuan.function;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;





public class ImageFunction{
	/*
	 * input:String urlString
	 * output:Bitmap bitmap
	 */
	public static Bitmap getBitmap(String urlString,String name){
		Bitmap bitmap;
		CacheFunction cacheFunction = new CacheFunction();
		InputStream in = cacheFunction.fetch(name, "Images");
		if(in!=null){
			byte[] data = DataFunction.inputstreamToByteArray(in);
			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			return bitmap;
		}
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			if(conn.getResponseCode()==200){
				InputStream in2 = conn.getInputStream();
				in2 = cacheFunction.store(name, "Images", in2);
				//Log.v("test", DataFunction.inputstreamToString(in));
				byte[] data2 = DataFunction.inputstreamToByteArray(in2);
				bitmap = BitmapFactory.decodeByteArray(data2, 0, data2.length);
				return bitmap;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

