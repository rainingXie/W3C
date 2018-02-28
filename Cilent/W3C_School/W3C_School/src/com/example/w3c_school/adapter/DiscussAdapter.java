package com.example.w3c_school.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.example.w3c_school.R;
import com.example.w3c_school.adapter.CategoryAdapter.ViewHolder;
import com.example.w3c_school.entity.Discuss;
import com.example.w3c_school.utils.FaceConversionUtil;
import com.example.w3c_school.utils.ImageLoaderUitil;

public class DiscussAdapter extends BaseAdapter {

	private List<Discuss> mDate;
	private Context mContext;

	

	public DiscussAdapter(List<Discuss> mDate, Context mContext) {
		super();
		this.mDate = mDate;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return this.mDate.size();
	}

	@Override
	public Object getItem(int arg0) {
		return this.mDate.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView==null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.discuss_item, null);
			holder = new ViewHolder();
			holder.img = (ImageView) convertView.findViewById(R.id.photo);
			holder.userName = (TextView) convertView.findViewById(R.id.name);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			holder.date = (TextView) convertView.findViewById(R.id.time);
			holder.backDiscuss = (TextView) convertView.findViewById(R.id.backdiscuss);
			holder.floor = (TextView) convertView.findViewById(R.id.floor);
			convertView.setTag(holder);
		}else{
			holder =(ViewHolder) convertView.getTag();
		}
		Discuss discuss = this.mDate.get(position);
		String ImgUrl =discuss.getImg();
		ImageLoaderUitil.display(ImgUrl , holder.img);
		if("empty".equals(discuss.getNo())){
			holder.userName.setText("匿名");
		}else{
			holder.userName.setText(discuss.getUserName());
		}
		
		SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(mContext, discuss.getContent());
		holder.content.setText(spannableString);
		holder.date.setText(discuss.getDate());
		if(discuss.getIsQuote()==0){
			holder.backDiscuss.setVisibility(View.GONE);
		}else{
			holder.backDiscuss.setText("回复"+discuss.getQuoteFloor()+"楼");
		}
		
		holder.floor.setText(discuss.getFloor()+"楼");
		return convertView;
	}
	
	public class ViewHolder {
		public ImageView img;
		public TextView userName;
		public TextView content;
		public TextView date;
		public TextView backDiscuss;
		public TextView floor;
		
	}


	
}
