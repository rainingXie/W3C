package com.example.w3c_school;

import java.sql.SQLException;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.example.w3c_school.db.DBHelper;
import com.example.w3c_school.entity.ApkInfo;
import com.example.w3c_school.entity.User;
import com.example.w3c_school.utils.ApplicationDIV;
import com.example.w3c_school.utils.Constant;
import com.example.w3c_school.utils.StringPostRequest;
import com.example.w3c_school.utils.UtilsURL;
import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class SplashActivity extends BaseActivity {

	private Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		getApkInfo();
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				SharedPreferences sp = getSharedPreferences(
						Constant.SP_FILE_NAME, MODE_PRIVATE);
				// boolean flag = sp.getBoolean("isFirst", true);
				boolean autoLogin = sp.getBoolean("self_login", true);
				final String no = sp.getString("userNo", null);
				final String pwd = sp.getString("userPwd", null);

				SharedPreferences.Editor editor = sp.edit();
				long startAppTime = System.currentTimeMillis();
				editor.putLong("startAppTime", startAppTime);
				editor.commit();
				if (autoLogin && null != no && null != pwd) {
					StringPostRequest request = new StringPostRequest(
							UtilsURL.LOGIN_URL, new Listener<String>() {

								private User personData;

								@Override
								public void onResponse(String arg0) {

									if (null != arg0) {

										Gson gosn = new Gson();
										personData = gosn.fromJson(arg0,
												User.class);

										if (null != personData) {

											ApplicationDIV.getInstance()
													.setUser(personData);
											SharedPreferences sp = getSharedPreferences(
													Constant.SP_FILE_NAME,
													MODE_PRIVATE);
											SharedPreferences.Editor editor = sp
													.edit();
											editor.putString("userName", no);
											editor.putString("userPwd", pwd);
											editor.commit();
											

										}

									} else {
										Toast.makeText(SplashActivity.this,
												"连接失败，请重试", Toast.LENGTH_SHORT)
												.show();
									}

									Intent intent = new Intent(
											SplashActivity.this,
											MainActivity.class);
									startActivity(intent);
									finish();
								}

							}, new ErrorListener() {

								@Override
								public void onErrorResponse(VolleyError arg0) {
									Dao<User, String> userDao = DBHelper
											.getInstance(SplashActivity.this)
											.getUserDao();
									try {
										User u = userDao.queryForId(no);
										if (null != u) {
											ApplicationDIV.getInstance()
													.setUser(u);
										}
									} catch (SQLException e) {
										e.printStackTrace();
									}
									Intent intent = new Intent(
											SplashActivity.this,
											MainActivity.class);
									startActivity(intent);
									finish();

								}
							});

					request.putParams("userNo", no);
					request.putParams("userPwd", pwd);
					ApplicationDIV.getInstance().getRequestQueue().add(request);
				} else {
					Intent intent = new Intent(SplashActivity.this,
							MainActivity.class);
					startActivity(intent);
					finish();
				}

				// Intent intent = null;
				//
				// if (flag) {
				// intent = new Intent(SplashActivity.this, MainActivity.class);
				// } else {
				// intent = new Intent(SplashActivity.this, MainActivity.class);
				// }
				// startActivity(intent);
				// finish();

			}

		}, 2000);

	}

	public void updateUser(User user) {
		Dao<User, String> userDao = DBHelper.getInstance(SplashActivity.this)
				.getUserDao();

		try {
			User u = userDao.queryForId(user.getUserNo());
			if (null != u) {
				userDao.delete(u);
			}
			userDao.create(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getApkInfo() {
		StringPostRequest request = new StringPostRequest(UtilsURL.DATA_URL,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						Gson gson = new Gson();
						ApkInfo apk = gson.fromJson(arg0, ApkInfo.class);
						ApplicationDIV.getInstance().setApkInfo(apk);

					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(getApplicationContext(), "连接服务器失败，请重试",
								Toast.LENGTH_SHORT).show();
					}
				});
		request.putParams("find", "apkInfos");
		ApplicationDIV.getInstance().getRequestQueue().add(request);
	}
}
