package com.example.w3c_school.adapter;

import java.util.List;

import com.example.w3c_school.R;
import com.example.w3c_school.entity.TestQuestions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TestQuestionsAdapter extends BaseAdapter{

	private List<TestQuestions> mData;
	private Context mContext;
	

	public TestQuestionsAdapter(List<TestQuestions> mData, Context mContext) {
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.test_list_item, null);
			holder = new ViewHolder();
			holder.subject = (TextView) convertView.findViewById(R.id.subject);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		TestQuestions test = this.mData.get(position);
		holder.subject.setText(test.getSubjectName());
		holder.name.setText(test.getPageName());
		
		return convertView;
	}
	
	public static class ViewHolder{
		public TextView subject;
		public TextView name;
	}

}
