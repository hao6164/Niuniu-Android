package com.sysu.niuniuleyuan.function;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TextFunction {

	public static String getText(String urlString,String name){
		String text;
		CacheFunction cacheFunction = new CacheFunction();
		InputStream in = cacheFunction.fetch(name, "Texts");
		if(in!=null){
			text = DataFunction.inputstreamToChinaString(in);
			return text;
		}
		try{
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			if(conn.getResponseCode()==200){
				InputStream in2 = conn.getInputStream();
				in2 = cacheFunction.store(name, "Texts", in2);
				text = DataFunction.inputstreamToChinaString(in2);
				return text;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
