package com.sysu.niuniuleyuan.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sysu.niuniuleyuan.R;
import com.sysu.niuniuleyuan.function.TextFunction;
import com.sysu.niuniuleyuan.function.URLAddress;

public class StoryItem extends Activity{

	private ImageButton backBtn;
	private TextView    storyTitle;
	private TextView    storyText;
	private ProgressBar progressBar;
	private String      title;
	private String      content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.story_item);
		bindView();
		
		//初始化界面
		Intent it = getIntent();
		title = it.getStringExtra("name");
		content = it.getStringExtra("content");
		storyTitle.setText(title);
		HttpTask httpTask = new HttpTask();
		httpTask.execute(URLAddress.MainUrl+content);

		
	}
	
	private void bindView(){
		backBtn = (ImageButton)findViewById(R.id.storyItemToList);
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StoryItem.this.finish();
			}
		});
		storyTitle = (TextView)findViewById(R.id.storyTitle);
		storyText = (TextView)findViewById(R.id.storyItem);
		progressBar = (ProgressBar)findViewById(R.id.storyItemBar);
	}
	
	
	private class HttpTask extends AsyncTask<String, Object, String>{

		@Override
		protected String doInBackground(String... params) {
			
			String text = TextFunction.getText(params[0],title+"Content");
			return text;
			
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			storyText.setText(result);
			storyText.setMovementMethod(new ScrollingMovementMethod());
			progressBar.setVisibility(8);
		}
		
	}
}
