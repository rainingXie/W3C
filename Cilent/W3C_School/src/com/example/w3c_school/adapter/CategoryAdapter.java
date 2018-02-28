package com.example.w3c_school.adapter;

import java.util.List;

import com.example.w3c_school.R;
import com.example.w3c_school.entity.Category;
import com.example.w3c_school.entity.CategoryContent;
import com.example.w3c_school.entity.ContentItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CategoryAdapter extends BaseAdapter{

	private List<ContentItem> mData;
	private Context mContext;
	
	public CategoryAdapter(List<ContentItem> mData, Context mContext) {
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
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.course_tag_list_item, null);
			holder.courseName = (TextView)convertView.findViewById(R.id.courseItem);
			convertView.setTag(holder);
		}else{
			holder =(ViewHolder) convertView.getTag();
		}
		ContentItem item = this.mData.get(position);
		holder.courseName.setText(item.getTitle());
		return convertView;
	}
	
	
	public static class ViewHolder{
		public TextView courseName;
	}
	

}
