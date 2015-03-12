package com.sysu.niuniuleyuan.function;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import android.util.Log;

public class DataFunction {
	public static String inputstreamToString(InputStream in){
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		int i = -1;
		try {
			while((i=in.read())!=-1){
				bout.write(i);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.v("test", e.toString());
		}
		return bout.toString();
	}
	
	public static String inputstreamToChinaString(InputStream in){
		StringBuffer out = new StringBuffer();
		try {    
		    byte[]  b  =  new  byte[2048]; 
		    for  (int  n;  (n  =  in.read(b))  !=  -1;)  { 
		       out.append(new String(b,0,n,Charset.forName("GBK"))); 
		    } 
		    return out.toString(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.v("test", e.toString());
		}
		return out.toString();
	}
	
	public static byte[] inputstreamToByteArray(InputStream in){
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		int i = -1;
		byte[] buffer = new byte[1024];
		try {
			while((i=in.read(buffer))!=-1){
				bout.write(buffer,0,i);
			}
			bout.close();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bout.toByteArray();
	}
}
