package com.sysu.niuniuleyuan.function;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonFunction {

	
	
	public static String getJsonString(String urlString,String name){
		String string;
		CacheFunction cacheFunction = new CacheFunction();
//		InputStream in = cacheFunction.fetch(name, "Lists");
//		if(in!=null){
//			string = DataFunction.inputstreamToString(in);
//			return string;
//		}
		try{
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			InputStream in2 = conn.getInputStream();
			in2 = cacheFunction.store(name, "Lists", in2);
			string = DataFunction.inputstreamToString(in2);
			return string;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
