package com.example.w3c_school;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.example.w3c_school.entity.User;
import com.example.w3c_school.utils.ApplicationDIV;
import com.example.w3c_school.utils.StringPostRequest;
import com.example.w3c_school.utils.UtilsURL;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class RegisteActivity extends BaseActivity implements OnClickListener {

	private EditText userName;
	private EditText userNo;
	private EditText userPwd;
	private EditText userPwdAgain;
	private EditText province;
	private EditText city;
	private CheckBox agree;
	private Button submit;
	private TextView agreeText;

	private String userNameStr;
	private String userNoStr;
	private String userPwdStr;
	private String userPwdAgainStr;
	private String provinceStr;
	private String cityStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registe);
		userName = (EditText) findViewById(R.id.userName);
		userNo = (EditText) findViewById(R.id.userNo);
		userPwd = (EditText) findViewById(R.id.userPwd);
		userPwdAgain = (EditText) findViewById(R.id.userPwdAgain);
		province = (EditText) findViewById(R.id.province);
		city = (EditText) findViewById(R.id.city);
		agree = (CheckBox) findViewById(R.id.agree);
		submit = (Button) findViewById(R.id.registe);
		submit.setOnClickListener(this);
		agreeText = (TextView) findViewById(R.id.agreeText);
		agreeText.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		agreeText.setTextColor(Color.BLUE);
		agreeText.setOnClickListener(this);
		

	}

	private void getData() {
		userNameStr = userName.getText().toString();
		userPwdStr = userPwd.getText().toString();
		userPwdAgainStr = userPwdAgain.getText().toString();
		userNoStr = userNo.getText().toString();
		provinceStr = province.getText().toString();
		cityStr = city.getText().toString();
		if (TextUtils.isEmpty(userNameStr)) {
			Toast.makeText(getApplicationContext(), "用户名不能为空",
					Toast.LENGTH_SHORT).show();
			userName.setFocusable(true);
			return;
		}
		if (TextUtils.isEmpty(userNoStr)) {
			Toast.makeText(getApplicationContext(), "申请账号不能为空",
					Toast.LENGTH_SHORT).show();
			userNo.setFocusable(true);
			return;
		}
		if (TextUtils.isEmpty(userPwdStr)) {
			Toast.makeText(getApplicationContext(), "密码不能为空",
					Toast.LENGTH_SHORT).show();
			userPwd.setFocusable(true);
			return;
		}
		if (TextUtils.isEmpty(provinceStr)) {
			Toast.makeText(getApplicationContext(), "所在省份不能为空",
					Toast.LENGTH_SHORT).show();
			province.setFocusable(true);
			return;
		}
		if (TextUtils.isEmpty(cityStr)) {
			Toast.makeText(getApplicationContext(), "所在城市不能为空",
					Toast.LENGTH_SHORT).show();
			city.setFocusable(true);
			return;
		}
		if (!userPwdStr.equals(userPwdAgainStr)) {
			Toast.makeText(getApplicationContext(), "两次密码输入不同",
					Toast.LENGTH_SHORT).show();
			return;
		}

		if (!agree.isChecked()) {
			Toast.makeText(getApplicationContext(), "请先同意协议",
					Toast.LENGTH_SHORT).show();
			return;
		}
		StringPostRequest request = new StringPostRequest(UtilsURL.DATA_URL,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						
						Toast.makeText(getApplicationContext(), "注册成功！",
								Toast.LENGTH_SHORT).show();
						userName.setText("");
						userPwd.setText("");
						userPwdAgain.setText("");
						userNo.setText("");
						province.setText("");
						city.setText("");
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(getApplicationContext(), "连接服务器失败，请重试！",
								Toast.LENGTH_SHORT).show();

					}
				});
		request.putParams("find", "addUser");
		request.putParams("userNo", userNoStr);
		request.putParams("userName", userNameStr);
		request.putParams("userPwd", userPwdStr);
		request.putParams("province", provinceStr);
		request.putParams("city", cityStr);
		ApplicationDIV.getInstance().getRequestQueue().add(request);

	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@SuppressLint("HandlerLeak")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				userNoStr = userNo.getText().toString();
				@SuppressWarnings("unchecked")
				List<User> ulist = (List<User>) msg.obj;
				if (ulist.size() > 0 && null != ulist) {
					for (User u : ulist) {
						if (userNoStr.equals(u.getUserNo())) {
							Toast.makeText(getApplicationContext(), "该用户名已存在！",
									Toast.LENGTH_SHORT).show();
							return;
						}
					}
				}

				getData();

				break;

			default:
				break;
			}
		}

	};

	private void getAllUser() {
		StringPostRequest request = new StringPostRequest(UtilsURL.DATA_URL,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						Gson gson = new Gson();
						List<User> uList = gson.fromJson(arg0,
								new TypeToken<ArrayList<User>>() {
								}.getType());
						if (uList.size() > 0 && null != uList) {
							Message msg = handler.obtainMessage();
							msg.what = 1;
							msg.obj = uList;
							handler.sendMessage(msg);
						}

					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(getApplicationContext(), "连接服务器失败，请重试！",
								Toast.LENGTH_SHORT).show();

					}
				});
		request.putParams("find", "allUser");
		ApplicationDIV.getInstance().getRequestQueue().add(request);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.agreeText:
			startActivity(new Intent(RegisteActivity.this,
					RegularActivity.class));
			break;
		case R.id.registe:
			getAllUser();
			break;
		default:
			break;
		}

	}

	public void back(View view) {
		finish();
	}
}
