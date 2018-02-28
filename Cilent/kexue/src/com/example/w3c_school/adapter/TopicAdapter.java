package com.example.w3c_school.adapter;

import java.util.List;














import com.example.w3c_school.DiscussActivity;
import com.example.w3c_school.R;
import com.example.w3c_school.entity.Topic;
import com.example.w3c_school.entity.Video;
import com.example.w3c_school.utils.FaceConversionUtil;
import com.example.w3c_school.utils.ImageLoaderUitil;
import com.example.w3c_school.utils.VideoThumbnail;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
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

public class TopicAdapter extends BaseAdapter{

	private List<Topic> mData;
	private Context mContext;
	ChooseListener listener;
	

	public TopicAdapter(List<Topic> mData, Context mContext) {
		super();
		this.mData = mData;
		this.mContext = mContext;
	}
	
	

	public void setListener(ChooseListener listener) {
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
		ViewHolder holder = null;
		if(convertView==null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.topic_list_item, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.date = (TextView) convertView.findViewById(R.id.date);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			holder.discussNum= (TextView) convertView.findViewById(R.id.discussNum);
			holder.goodNum= (TextView) convertView.findViewById(R.id.goodNum);
			holder.good = (CheckBox) convertView.findViewById(R.id.good);
			holder.discuss = (CheckBox) convertView.findViewById(R.id.discuss);
			holder.img = (ImageView) convertView.findViewById(R.id.user_img);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		final Topic topic = this.mData.get(position);
		holder.name.setText(topic.getUserName());
		holder.date.setText(topic.getDate());
		SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(mContext,topic.getContent());
		holder.content.setText(spannableString);
		holder.discussNum.setText(topic.getReplyment()+"");
		holder.goodNum.setText(topic.getGoodNum()+"");
		ImageLoaderUitil.display(topic.getImg(), holder.img,mContext);
		holder.good.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (null!=listener) {
					if (isChecked) {
						listener.chooseGood(topic);
						topic.setIsGood(1);
					}else {
						listener.unchooseGood(topic);
						topic.setIsGood(0);
					}
				}
				
			}
		});
		holder.discuss.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (null!=listener) {
					listener.clickDiscuss(topic);
				}
				
			}
		});
		return convertView;
	}
	
	public static class ViewHolder{
		public TextView name;
		public ImageView img;
		public TextView date;
		public TextView content;
		public CheckBox good;
		public TextView goodNum;
		public CheckBox discuss;
		public TextView discussNum;
		
	}
	
	
	public interface ChooseListener{
		public void chooseGood(Topic t);
		public void unchooseGood(Topic t);
		public void clickDiscuss(Topic t);
	}

	
}
