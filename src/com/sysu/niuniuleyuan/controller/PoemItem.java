package com.sysu.niuniuleyuan.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.sysu.niuniuleyuan.R;
import com.sysu.niuniuleyuan.function.ImageFunction;



public class PoemItem extends Activity{
	
	private ImageButton backBtn;
	private ImageView poemItem;
	private ProgressBar progressBar;
	private String name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.poem_item);
		bindView();
		//更新ImageView
		Intent it = getIntent();
		String poemUrl = it.getStringExtra("url");
		name = it.getStringExtra("name");
		HttpTask ht = new HttpTask();
		ht.execute(poemUrl);
	}
	
	private void bindView(){
		backBtn = (ImageButton)findViewById(R.id.poemItemToList);
		poemItem = (ImageView)findViewById(R.id.poemItem);
		progressBar = (ProgressBar)findViewById(R.id.poemItemBar);
		//返回键关闭当前界面
		backBtn.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				PoemItem.this.finish();	
			}
		});		
	}
	
	//内部类，访问http
	private class HttpTask extends AsyncTask<String, Object, Bitmap>{

		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bitmap = ImageFunction.getBitmap(params[0],name+"Content");
			return bitmap;
		}
		@Override
		protected void onPostExecute(Bitmap result) {
			if(result!=null){
				poemItem.setImageBitmap(result);
				progressBar.setVisibility(4);
			}
			super.onPostExecute(result);
		}
	}

}
