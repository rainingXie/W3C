package com.example.w3c_school;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.example.w3c_school.adapter.DownloadVideoAdapter;
import com.example.w3c_school.adapter.DownloadVideoAdapter.DownloadVideoListener;
import com.example.w3c_school.db.DBHelper;
import com.example.w3c_school.entity.Video;
import com.j256.ormlite.dao.Dao;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MyVideoActivity extends BaseActivity implements
		DownloadVideoListener {

	private ListView videoList;
	private List<Video> videos;
	private RelativeLayout bottom;
	private DownloadVideoAdapter videoAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info_video);
		videoList = (ListView) findViewById(R.id.videoList);
		bottom = (RelativeLayout) findViewById(R.id.bottom);
		videos = new ArrayList<Video>();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		videoAdapter = new DownloadVideoAdapter(videos, this, dm.widthPixels);
		videoList.setAdapter(videoAdapter);
		videoAdapter.setListener(this);
		initData();
		videoList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Video video = (Video) videoAdapter.getItem(position);
				Intent intent = new Intent(MyVideoActivity.this,
						VideoActivity.class);
				intent.putExtra("video", video);
				startActivity(intent);

			}
		});
	}

	private void initData() {
		videos.clear();
		videos.add(new Video(1,"视频1", "1.02M", "09:08", "/storage/school/videoCache/11.mp4"));
		videos.add(new Video(2,"视频2", "1.02M", "09:08", "/storage/school/videoCache/11.mp4"));
		videos.add(new Video(3,"视频3", "1.02M", "09:08", "/storage/school/videoCache/11.mp4"));

	}

	public void back(View view) {
		finish();
	}

	@SuppressLint({ "NewApi", "InflateParams" })
	@Override
	public void deleteVideo(final Video video) {

		View contextView = LayoutInflater.from(this).inflate(
				R.layout.pop_delete_file, null);
		final PopupWindow pop = new PopupWindow(contextView, 500,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		final CheckBox isDelete = (CheckBox) contextView
				.findViewById(R.id.isDelete);

		contextView.findViewById(R.id.sure).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (isDelete.isChecked()) {
							DeleteDialog(video, true);
						} else {
							DeleteDialog(video, false);
						}
						pop.dismiss();
						videos.remove(video);
						videoAdapter.notifyDataSetChanged();
					}
				});
		contextView.findViewById(R.id.cancel).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						pop.dismiss();
					}
				});

		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.5f;
		getWindow().setAttributes(lp);
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setBackgroundDrawable(new ColorDrawable());
		pop.showAsDropDown(bottom, 130, 400);
		pop.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1.0f;
				getWindow().setAttributes(lp);

			}
		});

		

	}

	private void DeleteDialog(final Video video, final boolean isDel) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("确定删除？")
		        .setTitle("提示").setCancelable(false)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						deleteLocal(video, isDel);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();

	}

	private void deleteLocal(Video video, boolean isDeleteFile) {
		Dao<Video, String> videoDao = DBHelper.getInstance(this).getVideoDao();
		try {
			
			if (isDeleteFile) {
				getSdcardFile(video);
			}
			videoDao.deleteById(video.getName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void getSdcardFile(Video video) {
		File file = new File(video.getUrl());
		if (file.exists()) {
			file.delete();
		} else {
			Toast.makeText(this, "本地文件不存在", Toast.LENGTH_SHORT).show();
		}
	}

}
