package com.example.w3c_school.utils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.R.integer;

public class MethodUtils {
	// 加密
	public static String encrypt(String str) {
		byte[] b = null;
		StringBuffer sb = new StringBuffer();
		try {
			b = str.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		for (byte bb : b) {
			sb.append(((int) bb + 256));
		}
		return sb.toString().substring(0, 16);
	}

	public static String getTimeFormat(long time) {
		long hour = time / (60 * 60 * 1000);
		long minute = (time - hour * 60 * 60 * 1000) / (60 * 1000);
		long second = (time - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000;
		return (hour + "时" + minute + "分 " + second + "秒");
	}

	public static String getDate() {
		Calendar c = Calendar.getInstance();

		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH) + 1);
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String mins = String.valueOf(c.get(Calendar.MINUTE));

		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":"
				+ mins);

		return sbBuffer.toString();
	}

	/**
	 * 获得当前时间yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getCurDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

	public static String getVideoName(String str) {
		int start = str.lastIndexOf("/");
		String name = str.substring(start + 1);
		return name;

	}
	
	public static String getCurrentTime(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		String currentTime = sdf.format(date);
		return currentTime;
	}

	public static String getCurrentTime() {
		return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
	}
}
