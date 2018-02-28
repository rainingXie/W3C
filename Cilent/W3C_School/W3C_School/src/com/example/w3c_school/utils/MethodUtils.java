package com.example.w3c_school.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class MethodUtils {
	// º”√‹
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
		return (hour + " ±" + minute + "∑÷ " + second + "√Î");
	}

}
