package com.example.w3c_school.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;

import com.example.w3c_school.MyVideoActivity;
import com.example.w3c_school.R;
import com.example.w3c_school.db.DBHelper;
import com.example.w3c_school.entity.Video;
import com.example.w3c_school.utils.ExecutorManager;
import com.example.w3c_school.utils.FileUitlity;
import com.example.w3c_school.utils.MethodUtils;
import com.j256.ormlite.dao.Dao;

import android.R.integer;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class DownLoadVideoService extends Service {

	private NotificationManager nm;
	private Thread thread;
	private ExecutorService executorService;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		executorService = ExecutorManager.getInstance().getExecutors();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		final Video video = (Video) intent.getSerializableExtra("video");

		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				download(video);
				stopSelf();

			}
		});
		executorService.execute(thread);
		// thread.start();
		return super.onStartCommand(intent, flags, startId);
	}

	public void notifyNotification(int id, int progress) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);
		builder.setContentTitle("下载视频");
		builder.setProgress(100, progress, false);
		builder.setSmallIcon(R.drawable.logo);

		Notification notification = builder.build();
		nm.notify(id, notification);
	}

	public void notifyFinishNotification(int id) {
		// 创建Notification
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);
		builder.setContentTitle("下载完成");
		builder.setContentText("下载完成，点击查看");
		builder.setSmallIcon(R.drawable.logo);

		Intent in = new Intent(this, MyVideoActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, in,
				PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pendingIntent);
		builder.setAutoCancel(true);
		Notification notification = builder.build();
		nm.notify(id, notification);

	}

	private void addLocalData(Video video, String localUrl) {
		Dao<Video, String> videoDao = DBHelper.getInstance(this).getVideoDao();
		video.setUrl(localUrl);
		try {
			Video local = videoDao.queryForId(video.getName());
			if (null != local) {
				videoDao.deleteById(video.getName());
			}
			videoDao.create(video);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载
	 */
	public void download(Video video) {
		HttpURLConnection conn = null;
		URL url = null;
		InputStream is = null;
		OutputStream os = null;
		File parent = null;
		File urlFile;
		int contentLength = 0;
		int currentLength = 0;
		try {
			url = new URL(video.getUrl());
			parent = FileUitlity.getInstance(this).makeDir("videoCache");
			urlFile = new File(parent, MethodUtils.getVideoName(video.getUrl()));
			os = new FileOutputStream(urlFile);
			conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			if (conn.getResponseCode() == 200) {
				// 文件总长度
				contentLength = conn.getContentLength();
				is = conn.getInputStream();
				byte[] buffer = new byte[512];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					//
					currentLength += len;
					os.write(buffer, 0, len);

					// 计算进度
					int progress = (int) ((currentLength * 1.0 / contentLength) * 100);
					notifyNotification(video.getId(), progress);

				}
				os.flush();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != os)
					os.close();

				if (null != is)
					is.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
			if (null != conn) {
				conn.disconnect();
			}

		}

		notifyFinishNotification(video.getId());
		addLocalData(video, parent + MethodUtils.getVideoName(video.getUrl()));
	}

}
