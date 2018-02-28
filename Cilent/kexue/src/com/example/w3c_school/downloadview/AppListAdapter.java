package com.example.w3c_school.downloadview;

import java.util.List;

import com.example.w3c_school.R;
import com.example.w3c_school.adapter.VideoAdapter.ViewHolder;
import com.example.w3c_school.view.MyGridView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * 下载列表的数据适配器
 * 
 * 
 **/
public class AppListAdapter extends BaseAdapter {

	private List<AppFile> dataList = null;
	private LayoutInflater inflater = null;
	private Context mContext;
	private DownloadManager downloadManager;
	private MyGridView listView;

	public AppListAdapter(Context context, List<AppFile> dataList) {
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.dataList = dataList;
		this.mContext = context;
		this.downloadManager = DownloadManager.getInstance();
		this.downloadManager.setHandler(mHandler);
	}

	public void setListView(MyGridView view){
		this.listView = view;
	}
	
	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	// 改变下载按钮的样式
	private void changeBtnStyle(Button btn, boolean enable){
		if(enable){
			btn.setBackgroundResource(R.drawable.download_btn_bg);
		}
		else{
			btn.setBackgroundResource(R.drawable.download_btn_pressed_bg);
		}
		btn.setEnabled(enable);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.video_gridview_item, null);
			holder.icon = (ImageView) convertView.findViewById(R.id.videoImg);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.size = (TextView) convertView.findViewById(R.id.totalTime);
			holder.btn = (Button) convertView.findViewById(R.id.download_btn);
			convertView.setTag(holder);
//			convertView = inflater.inflate(R.layout.video_item, nulls);
//			holder.layout = (LinearLayout) convertView
//					.findViewById(R.id.gamelist_item_layout);
//			holder.icon = (ImageView) convertView
//					.findViewById(R.id.app_icon);
//			holder.name = (TextView) convertView
//					.findViewById(R.id.app_name);
//			holder.size = (TextView) convertView
//					.findViewById(R.id.app_size);
//			holder.btn = (Button) convertView
//					.findViewById(R.id.download_btn);
//			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 这里position和app.id的值是相等的
		final AppFile app = dataList.get(position);
		//Log.e("", "id="+app.id+", name="+app.name);

		holder.name.setText(app.name);
		holder.size.setText((app.downloadSize * 100.0f / app.size) + "%");

		Drawable drawable = mContext.getResources().getDrawable(R.drawable.video);
		holder.icon.setImageDrawable(drawable);

		switch(app.downloadState){
		case DownloadManager.DOWNLOAD_STATE_NORMAL:
			holder.btn.setText("下载");
			this.changeBtnStyle(holder.btn, true);
			break;
		case DownloadManager.DOWNLOAD_STATE_DOWNLOADING:
			holder.btn.setText("下载中");
			this.changeBtnStyle(holder.btn, false);
			break;
		case DownloadManager.DOWNLOAD_STATE_FINISH:
			holder.btn.setText("已下载");
			this.changeBtnStyle(holder.btn, false);
			break;
		case DownloadManager.DOWNLOAD_STATE_WAITING:
			holder.btn.setText("等待");
			this.changeBtnStyle(holder.btn, false);
			break;
		}
		holder.btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DownloadFile downloadFile = new DownloadFile();
				downloadFile.downloadID = app.id;
				downloadFile.downloadState = DownloadManager.DOWNLOAD_STATE_WAITING;
				app.downloadState = DownloadManager.DOWNLOAD_STATE_WAITING;
				downloadFile.downloadSize = app.downloadSize;
				downloadFile.totalSize = app.size;
				holder.btn.setText("等待");
			    changeBtnStyle(holder.btn, false);
				downloadManager.startDownload(downloadFile);
			}
		});
		return convertView;
	}

	static class ViewHolder {
		LinearLayout layout;
		ImageView icon;
		TextView name;
		TextView size;
		Button btn;
	}
	
	
	private Handler mHandler = new Handler() {
		
		public void handleMessage(Message msg){
			DownloadFile downloadFile = (DownloadFile)msg.obj;
			AppFile appFile = dataList.get(downloadFile.downloadID);
			appFile.downloadSize = downloadFile.downloadSize;
			appFile.downloadState = downloadFile.downloadState;
			
			// notifyDataSetChanged会执行getView函数，更新所有可视item的数据
			//notifyDataSetChanged();
			// 只更新指定item的数据，提高了性能
			updateView(appFile.id);
		}
	};
	
	// 更新指定item的数据
	private void updateView(int index){
	//	int visiblePos = listView.getFirstVisiblePosition();
	//	int offset = index - visiblePos;
		//Log.e("", "index="+index+"visiblePos="+visiblePos+"offset="+offset);
		// 只有在可见区域才更新
	//	if(offset < 0) return;
		
		View view = listView.getChildAt(index);
		final AppFile app = dataList.get(index);
		ViewHolder holder = (ViewHolder)view.getTag();
		//Log.e("", "id="+app.id+", name="+app.name);

		holder.name.setText(app.name);
		holder.size.setText((app.downloadSize * 100.0f / app.size) + "%");
		Drawable drawable = mContext.getResources().getDrawable(R.drawable.video);
		holder.icon.setImageDrawable(drawable);

		switch(app.downloadState){
		case DownloadManager.DOWNLOAD_STATE_DOWNLOADING:
			holder.btn.setText("下载中");
			this.changeBtnStyle(holder.btn, false);
			break;
		case DownloadManager.DOWNLOAD_STATE_FINISH:
			holder.btn.setText("已下载");
			this.changeBtnStyle(holder.btn, false);
			break;
		}

	}
}
