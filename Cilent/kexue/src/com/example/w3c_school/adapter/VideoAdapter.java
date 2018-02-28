package com.example.w3c_school.adapter;

import java.util.List;







import com.example.w3c_school.R;
import com.example.w3c_school.entity.Video;
import com.example.w3c_school.utils.ImageLoaderUitil;
import com.example.w3c_school.utils.VideoThumbnail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class VideoAdapter extends BaseAdapter{

	private List<Video> mData;
	private Context mContext;
	DownloadListener listener;
	

	public VideoAdapter(List<Video> mData, Context mContext) {
		super();
		this.mData = mData;
		this.mContext = mContext;
	}

	public void setListener(DownloadListener listener) {
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.video_gridview_item, null);
			holder = new ViewHolder();
			holder.videoImg = (ImageView) convertView.findViewById(R.id.videoImg);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			//holder.size = (TextView) convertView.findViewById(R.id.time_length);
			holder.totalTime = (TextView) convertView.findViewById(R.id.totalTime);
			holder.download = (Button) convertView.findViewById(R.id.download_btn);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		final Video video = this.mData.get(position);
		holder.name.setText(video.getName());
		//holder.size.setText(video.getSize());
		holder.totalTime.setText(video.getTotalTime());
		if (null!=video.getUrl()) {
			holder.videoImg.setImageBitmap(VideoThumbnail.getVideoThumbnail(video.getUrl()));
		}else {
			holder.videoImg.setImageResource(R.drawable.w3welcome);
		}
		
		holder.download.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (null!=listener) {
					//listener.downloadVideo(video);
				}
			}
		});
		return convertView;
	}
	
	public static class ViewHolder{
		public TextView name;
		public TextView size;
		public TextView totalTime;
		public Button download;
		public ImageView videoImg;
	}
	
	
	public interface DownloadListener{
		public void downloadVideo(Video video);
	}

}
