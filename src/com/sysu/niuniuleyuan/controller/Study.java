package com.sysu.niuniuleyuan.controller;


import com.sysu.niuniuleyuan.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Study extends Activity{
	private ImageButton backBtn;
	private ImageButton btn0, btn1, btn2, btn3, btn4, btn5,
	                    btn6, btn7, btn8, btn9, btnDelete, btnEqual;
	private ImageView math_question, dynamicShow;
	private int question = 0, answer = 0, nowQuestion;
	LinearLayout studyLinearLayoutShow;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.study);
		bindView();
		selectQuestion(1);
		bindListener();
	}

	private void selectQuestion(int id) {
		// TODO Auto-generated method stub
		switch(id){
		case 1:
			math_question.setImageResource(R.drawable.math_question1);
			studyLinearLayoutShow.removeAllViews();
			dynamicShow = new ImageView(Study.this);
			dynamicShow.setImageResource(R.drawable.math_icon_0);
			studyLinearLayoutShow.addView(dynamicShow);
			question = 121;
			answer = 0;
			nowQuestion = 1;
			break;
		case 2:
			math_question.setImageResource(R.drawable.math_question2);
			studyLinearLayoutShow.removeAllViews();
			dynamicShow = new ImageView(Study.this);
			dynamicShow.setImageResource(R.drawable.math_icon_0);
			studyLinearLayoutShow.addView(dynamicShow);
			question = 88;
			answer = 0;
			nowQuestion = 2;
			break;
		case 3:
			math_question.setImageResource(R.drawable.math_question3);
			studyLinearLayoutShow.removeAllViews();
			dynamicShow = new ImageView(Study.this);
			dynamicShow.setImageResource(R.drawable.math_icon_0);
			studyLinearLayoutShow.addView(dynamicShow);
			question = 27;
			answer = 0;
			nowQuestion = 3;
			break;
		case 4:
			math_question.setImageResource(R.drawable.math_question4);
			studyLinearLayoutShow.removeAllViews();
			dynamicShow = new ImageView(Study.this);
			dynamicShow.setImageResource(R.drawable.math_icon_0);
			studyLinearLayoutShow.addView(dynamicShow);
			question = 8;
			answer = 0;
			nowQuestion = 4;
			break;
		case 5:
			math_question.setImageResource(R.drawable.math_question5);
			studyLinearLayoutShow.removeAllViews();
			dynamicShow = new ImageView(Study.this);
			dynamicShow.setImageResource(R.drawable.math_icon_0);
			studyLinearLayoutShow.addView(dynamicShow);
			question = 35;
			answer = 0;
			nowQuestion = 5;
			break;
		}
	}

	private void bindView() {
		// TODO Auto-generated method stub
		math_question = (ImageView) findViewById(R.id.math_question);
		studyLinearLayoutShow = (LinearLayout) findViewById(R.id.studyLinearLayoutShow);
		backBtn = (ImageButton) findViewById(R.id.math_back_button);
		btn0 = (ImageButton) findViewById(R.id.math_icon_0);
		btn1 = (ImageButton) findViewById(R.id.math_icon_1);
		btn2 = (ImageButton) findViewById(R.id.math_icon_2);
		btn3 = (ImageButton) findViewById(R.id.math_icon_3);
		btn4 = (ImageButton) findViewById(R.id.math_icon_4);
		btn5 = (ImageButton) findViewById(R.id.math_icon_5);
		btn6 = (ImageButton) findViewById(R.id.math_icon_6);
		btn7 = (ImageButton) findViewById(R.id.math_icon_7);
		btn8 = (ImageButton) findViewById(R.id.math_icon_8);
		btn9 = (ImageButton) findViewById(R.id.math_icon_9);
		btnDelete = (ImageButton) findViewById(R.id.math_icon_delete);
		btnEqual = (ImageButton) findViewById(R.id.math_icon_equal);
		
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Study.this.finish();
			}
		});
	}
	
	private void bindListener() {
		// TODO Auto-generated method stub
		OnClickListener listener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch(v.getId()){
				case R.id.math_icon_0:
					answer = answer * 10;
					if(answer != 0){
						dynamicShow = new ImageView(Study.this);
						dynamicShow.setImageResource(R.drawable.math_icon_0);
						studyLinearLayoutShow.addView(dynamicShow);
					}
					break;
				case R.id.math_icon_1:
					if(answer == 0){
						studyLinearLayoutShow.removeAllViews();
					}
					answer = answer * 10 + 1;
					dynamicShow = new ImageView(Study.this);
					dynamicShow.setImageResource(R.drawable.math_icon_1);
					studyLinearLayoutShow.addView(dynamicShow);
					break;
				case R.id.math_icon_2:
					if(answer == 0){
						studyLinearLayoutShow.removeAllViews();
					}
					answer = answer * 10 + 2;
					dynamicShow = new ImageView(Study.this);
					dynamicShow.setImageResource(R.drawable.math_icon_2);
					studyLinearLayoutShow.addView(dynamicShow);
					break;
				case R.id.math_icon_3:
					if(answer == 0){
						studyLinearLayoutShow.removeAllViews();
					}
					answer = answer * 10 + 3;
					dynamicShow = new ImageView(Study.this);
					dynamicShow.setImageResource(R.drawable.math_icon_3);
					studyLinearLayoutShow.addView(dynamicShow);
					break;
				case R.id.math_icon_4:
					if(answer == 0){
						studyLinearLayoutShow.removeAllViews();
					}
					answer = answer * 10 + 4;
					dynamicShow = new ImageView(Study.this);
					dynamicShow.setImageResource(R.drawable.math_icon_4);
					studyLinearLayoutShow.addView(dynamicShow);
					break;
				case R.id.math_icon_5:
					if(answer == 0){
						studyLinearLayoutShow.removeAllViews();
					}
					answer = answer * 10 + 5;					
					dynamicShow = new ImageView(Study.this);
					dynamicShow.setImageResource(R.drawable.math_icon_5);
					studyLinearLayoutShow.addView(dynamicShow);
					break;
				case R.id.math_icon_6:
					if(answer == 0){
						studyLinearLayoutShow.removeAllViews();
					}
					answer = answer * 10 + 6;
					dynamicShow = new ImageView(Study.this);
					dynamicShow.setImageResource(R.drawable.math_icon_6);
					studyLinearLayoutShow.addView(dynamicShow);
					break;
				case R.id.math_icon_7:
					if(answer == 0){
						studyLinearLayoutShow.removeAllViews();
					}
					answer = answer * 10 + 7;					
					dynamicShow = new ImageView(Study.this);
					dynamicShow.setImageResource(R.drawable.math_icon_7);
					studyLinearLayoutShow.addView(dynamicShow);
					break;
				case R.id.math_icon_8:
					if(answer == 0){
						studyLinearLayoutShow.removeAllViews();
					}
					answer = answer * 10 + 8;
					dynamicShow = new ImageView(Study.this);
					dynamicShow.setImageResource(R.drawable.math_icon_8);
					studyLinearLayoutShow.addView(dynamicShow);
					break;
				case R.id.math_icon_9:
					if(answer == 0){
						studyLinearLayoutShow.removeAllViews();
					}
					answer = answer * 10 + 9;
					dynamicShow = new ImageView(Study.this);
					dynamicShow.setImageResource(R.drawable.math_icon_9);
					studyLinearLayoutShow.addView(dynamicShow);
					break;
				case R.id.math_icon_delete:
					if(answer != 0){
						studyLinearLayoutShow.removeViewAt(studyLinearLayoutShow.getChildCount()-1);
					}
					answer = answer / 10;
					if(answer == 0){
						studyLinearLayoutShow.removeAllViews();
						dynamicShow = new ImageView(Study.this);
						dynamicShow.setImageResource(R.drawable.math_icon_0);
						studyLinearLayoutShow.addView(dynamicShow);
					}
					break;
				case R.id.math_icon_equal:
					if(answer == 0)
						Log.v("jin","未输入值");
					else if(question != answer){
						Log.v("jin","错误");
						ImageView img = new ImageView(Study.this);
						img.setImageResource(R.drawable.math_wrong);
						new AlertDialog.Builder(Study.this)
						               .setPositiveButton("确定", null)
						               .setView(img)
						               .show();
						
						math_question.setImageResource(R.drawable.math_wrong);
						selectQuestion(nowQuestion);
					}
					else {
						Log.v("jin","正确");
						ImageView img = new ImageView(Study.this);
						img.setImageResource(R.drawable.math_right);
						new AlertDialog.Builder(Study.this)
						               .setPositiveButton("确定", null)
						               .setView(img)
						               .show();
						
						nowQuestion = nowQuestion % 5 + 1;
						selectQuestion(nowQuestion);
					}
					
					break;
				
				}
			}
		};
		
		btn0.setOnClickListener(listener);
		btn1.setOnClickListener(listener);
		btn2.setOnClickListener(listener);
		btn3.setOnClickListener(listener);
		btn4.setOnClickListener(listener);
		btn5.setOnClickListener(listener);
		btn6.setOnClickListener(listener);
		btn7.setOnClickListener(listener);
		btn8.setOnClickListener(listener);
		btn9.setOnClickListener(listener);
		btnDelete.setOnClickListener(listener);
		btnEqual.setOnClickListener(listener);
	
	}

}
