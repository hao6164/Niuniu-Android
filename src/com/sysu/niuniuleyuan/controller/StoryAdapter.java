package com.sysu.niuniuleyuan.controller;

import java.util.ArrayList;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sysu.niuniuleyuan.R;
import com.sysu.niuniuleyuan.function.ImageFunction;
import com.sysu.niuniuleyuan.function.StoryClass;
import com.sysu.niuniuleyuan.function.URLAddress;

public class StoryAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private ArrayList<StoryClass> storys;
	
	public StoryAdapter(Context context,ArrayList<StoryClass> storys){
		inflater = LayoutInflater.from(context);
		this.storys = storys;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return storys.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			convertView = inflater.inflate(R.layout.story_list_item, null);
		}
		ImageView icon = (ImageView)convertView.findViewById(R.id.storyListItemIcon);
		TextView  name = (TextView)convertView.findViewById(R.id.storyListItemName);
		TextView  jianjie = (TextView)convertView.findViewById(R.id.storyListItemJianjie);
		StoryClass story = storys.get(position);
		icon.setImageBitmap(story.bitmap);
		name.setText(story.name);
		jianjie.setText(story.jianjie);
		return convertView;
	}

}
