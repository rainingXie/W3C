package com.example.w3c_school.adapter;

import java.util.List;














import com.example.w3c_school.DiscussActivity;
import com.example.w3c_school.R;
import com.example.w3c_school.entity.Category;
import com.example.w3c_school.entity.Topic;
import com.example.w3c_school.entity.Video;
import com.example.w3c_school.utils.ImageLoaderUitil;
import com.example.w3c_school.utils.VideoThumbnail;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class CourseAdapter extends BaseAdapter{

	private List<Category> mData;
	private Context mContext;
	BookChooseListener listener;
	

	public CourseAdapter(List<Category> mData, Context mContext) {
		super();
		this.mData = mData;
		this.mContext = mContext;
	}
	
	

	public void setListener(BookChooseListener listener) {
		this.listener = listener;
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
		Button book;
		if(convertView==null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.course_gridview_item, null);
			book =  (Button) convertView.findViewById(R.id.book);
			
			convertView.setTag(book);
		}else{
			book = (Button) convertView.getTag();
		}
		
		final Category category = this.mData.get(position);
		book.setText(category.getShceduleName());
		book.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (null!=listener) {
					listener.clickBook(category);
				}
				
			}
		});
		return convertView;
	}
	
	
	
	
	public interface BookChooseListener{
		public void clickBook(Category c);
	}

	
}
