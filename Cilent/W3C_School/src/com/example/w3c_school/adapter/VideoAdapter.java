package com.example.w3c_school.adapter;

import java.util.List;


import com.example.w3c_school.R;
import com.example.w3c_school.entity.Video;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class VideoAdapter extends BaseAdapter{

	private List<Video> mData;
	private Context mContext;
	

	public VideoAdapter(List<Video> mData, Context mContext) {
		super();
		this.mData = mData;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return this.mData.size();
	}

	@Override
	public Object getItem(int position) {
		return this.mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView==null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.video_list_item, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.label = (TextView) convertView.findViewById(R.id.introduce);
			holder.totalTime = (TextView) convertView.findViewById(R.id.time);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		Video video = this.mData.get(position);
		holder.name.setText(video.getName());
		holder.label.setText(video.getLabel());
		holder.totalTime.setText(video.getTotalTime());
		
		return convertView;
	}
	
	public static class ViewHolder{
		public TextView name;
		public TextView label;
		public TextView totalTime;
	}

}
