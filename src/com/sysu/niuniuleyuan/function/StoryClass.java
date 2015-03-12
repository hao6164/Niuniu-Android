package com.sysu.niuniuleyuan.function;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;

public class StoryClass {

	public String name;
	public String weizhi;
	public String jianjie;
	public String content;
	public Bitmap bitmap;
	
	public void setStory(JSONObject json){
		try {
			name = json.getString("name");
			weizhi = json.getString("weizhi");
			jianjie = json.getString("jianjie");
			content = json.getString("content");
			bitmap = ImageFunction.getBitmap(URLAddress.MainUrl+weizhi,name+"Icon");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
}
