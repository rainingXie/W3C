package com.example.w3c_school.service;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.w3c_school.R;
import com.example.w3c_school.utils.FileUitlity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;


public class VersionUpdateService extends Service {

	private NotificationManager nm;
	private Thread thread;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	
	@Override
	public void onCreate() {
		super.onCreate();
		nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		final String url = intent.getStringExtra("url");
		thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				download(url);
				stopSelf();
				
			}
		});
		thread.start();
		return super.onStartCommand(intent, flags, startId);
	}
	
	public void notifyNotification(int progress){
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setContentTitle("下载");
		builder.setProgress(100, progress, false);
		builder.setSmallIcon(R.drawable.logo);

		Notification notification = builder.build();
		nm.notify(0, notification);
	}
	
	public void notifyFinishNotification() {
		// 创建Notification
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);
		builder.setContentTitle("下载完成");
		builder.setContentText("下载完成，点击查看");
		builder.setSmallIcon(R.drawable.logo);

		//安装Apk
		File parent = FileUitlity.getInstance(this).makeDir("apk");
		File apkPath = new File(parent, "w3cshool.apk");
		Intent in = new Intent();
		in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		in.setAction(Intent.ACTION_VIEW);
		in.setDataAndType(Uri.fromFile(apkPath), "application/vnd.android.package-archive");
		
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				in, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pendingIntent);
		builder.setAutoCancel(true);
		Notification notification = builder.build();
		nm.notify(0, notification);

	}

	/**
	 * 下载
	 */
	public void download(String urlStr) {
		HttpURLConnection conn = null;
		URL url = null;
		InputStream is = null;
		OutputStream os = null;
		int contentLength = 0;
		int currentLength = 0;
		try {
			url = new URL(urlStr);
			File parent = FileUitlity.getInstance(this).makeDir("apk");
			os = new FileOutputStream(new File(parent, "cc.apk"));
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

					//计算进度
					int progress = (int) ((currentLength*1.0/contentLength)*100);
					notifyNotification(progress);
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
			conn.disconnect();
		}
		
		notifyFinishNotification();
	}
	
	
	
}
