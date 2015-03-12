package com.sysu.niuniuleyuan.function;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.util.Log;

public class PoemClass {

	public String name;
	public String weizhi_1;
	public String weizhi_2;
	public Bitmap bitmap;
	
	public void setPoem(JSONObject json){
		try {
			name = json.getString("name");
			weizhi_1 = json.getString("weizhi_1");
			weizhi_2 = json.getString("weizhi_2");
			bitmap = ImageFunction.getBitmap(URLAddress.MainUrl+weizhi_1,name+"Icon");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
}
