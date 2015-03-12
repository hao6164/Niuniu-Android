package com.sysu.niuniuleyuan.controller;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sysu.niuniuleyuan.R;
import com.sysu.niuniuleyuan.function.PoemClass;

public class PoemAdapter extends BaseAdapter{

	private Activity context;
	private ArrayList<PoemClass> poems;
	private static LayoutInflater inflater = null;
	
	public PoemAdapter(Activity context,ArrayList<PoemClass> poems){
		this.context = context;
		this.poems = poems;
		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return poems.size();
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
		if(convertView==null){
			//Log.v("test","≥ı ºªØ");
			convertView = inflater.inflate(R.layout.poem_list_item, null);
		}
		
		ImageView iv = (ImageView)convertView.findViewById(R.id.poemListItem);
		iv.setImageBitmap(poems.get(position).bitmap);
		
		return convertView;
	}

}
