package com.sysu.niuniuleyuan.controller;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.sysu.niuniuleyuan.R;
import com.sysu.niuniuleyuan.function.CacheFunction;
import com.sysu.niuniuleyuan.function.DataFunction;
import com.sysu.niuniuleyuan.function.JsonFunction;
import com.sysu.niuniuleyuan.function.StoryClass;
import com.sysu.niuniuleyuan.function.URLAddress;

public class StoryList extends Activity{

	private ListView storyList;
	private ImageButton backBtn;
	private ProgressBar progressBar;
	private StoryAdapter storyAdapter;
	private ArrayList<StoryClass> storys = new ArrayList<StoryClass>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.story_list);
		bindView();
		initList();
	}
	
	private void bindView(){
		storyList = (ListView)findViewById(R.id.storyList);
		backBtn = (ImageButton)findViewById(R.id.storyListToMain);
		progressBar = (ProgressBar)findViewById(R.id.storyListBar);
		//返回键关闭当前界面
		backBtn.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				StoryList.this.finish();	
			}
		});
	}
	
	private void initList(){
		//初始化列表
		HttpTask httpTask = new HttpTask();
		httpTask.execute(URLAddress.StoryUrl);
		//设置列表点击监听器
		storyList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent it = new Intent(StoryList.this,StoryItem.class);
				it.putExtra("name", storys.get(position).name);
				it.putExtra("content", storys.get(position).content);
				startActivity(it);
			}
		});
	}
	
	private class HttpTask extends AsyncTask<String, Object, Object>{

		@Override
		protected Object doInBackground(String... params) {
			try{
				String jsonString = JsonFunction.getJsonString(params[0], "story");
				//Log.v("test", jsonString);
				JSONArray jsonArray = new JSONArray(jsonString);
				for(int i =0;i<jsonArray.length();i++){
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					StoryClass story = new StoryClass();
					story.setStory(jsonObject);
					storys.add(story);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			storyAdapter = new StoryAdapter(StoryList.this,storys);
			storyList.setAdapter(storyAdapter);
			progressBar.setVisibility(8);
		}
		
	}
}
