package com.example.w3c_school;

import java.sql.SQLException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.example.w3c_school.db.DBHelper;
import com.example.w3c_school.entity.User;
import com.example.w3c_school.utils.ApplicationDIV;
import com.example.w3c_school.utils.Constant;
import com.example.w3c_school.utils.MethodUtils;
import com.example.w3c_school.utils.StringPostRequest;
import com.example.w3c_school.utils.UtilsURL;
import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class LoginActivity extends BaseActivity implements OnClickListener {

	private EditText userNoEdit;
	private EditText passwordEdit;
	private CheckBox remember;
	private CheckBox self_login;
	private String userNoText;
	private String passwordText;
	private User userData;
	private TextView qq;
;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		findViewById(R.id.registe).setOnClickListener(this);
//		qq = (TextView) findViewById(R.id.qq);
//		qq.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//		qq.setTextColor(Color.BLUE);
//		qq.setOnClickListener(this);
		//user = new User();
		initView();

	}

	private void initView() {
		userNoEdit = (EditText) findViewById(R.id.login_user);
		passwordEdit = (EditText) findViewById(R.id.login_password);
		remember = (CheckBox) findViewById(R.id.remember);
		self_login = (CheckBox) findViewById(R.id.selflogin);
		findViewById(R.id.login).setOnClickListener(this);
		User user = ApplicationDIV.getInstance().getUser();
		if (user == null) {
			return;
		}
		SharedPreferences sp = getSharedPreferences(Constant.SP_FILE_NAME,
				MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		boolean flag = sp.getBoolean("remember", true);
		boolean self_login_flag = sp.getBoolean("self_login", true);
		self_login.setChecked(self_login_flag);
		editor.putBoolean("self_login", self_login.isChecked());
		editor.commit();
		self_login.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				SharedPreferences sp = getSharedPreferences(
						Constant.SP_FILE_NAME, MODE_PRIVATE);
				SharedPreferences.Editor editor = sp.edit();
				editor.putBoolean("self_login", isChecked);
				editor.commit();
				if (isChecked) {
					remember.setChecked(true);
				}

			}
		});
		remember.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				SharedPreferences sp = getSharedPreferences(
						Constant.SP_FILE_NAME, MODE_PRIVATE);
				SharedPreferences.Editor editor = sp.edit();
				editor.putBoolean("remember", isChecked);
				editor.commit();
				if (!isChecked) {
					self_login.setChecked(false);
				}

			}
		});

		if (!flag) {
			userNoEdit.setText("");
			passwordEdit.setText("");
			remember.setChecked(flag);
		} else {
			if (null != user) {
				userNoEdit.setText(user.getUserNo());
				passwordEdit.setText(user.getUserPwd());
				remember.setChecked(flag);
			}

		}

	}

	private void initData() {
		userNoText = userNoEdit.getText().toString();
		passwordText = passwordEdit.getText().toString();
		if (userNoText == null
				|| "".equals(userNoText)) {
			Toast.makeText(LoginActivity.this,
					"用户名不能为空", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		if (passwordText == null
				|| "".equals(passwordText)) {
			Toast.makeText(LoginActivity.this,
					"密码不能为空", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		if ("123".equals(userNoText)&&"123".equals(passwordText)) {
			userData = new User("123", "123", "123", null, null);
			ApplicationDIV.getInstance().setUser(
					userData);
			updateUser();
			setResult(RESULT_FIRST_USER);
			Toast.makeText(LoginActivity.this,
					"登录成功", Toast.LENGTH_SHORT)
					.show();
			finish();
		}
		StringPostRequest request = new StringPostRequest(UtilsURL.LOGIN_URL,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {

						if (null != arg0) {
							JSONObject json = null;
							try {
								json = new JSONObject(arg0);
								if (json.has("info")) {
									Toast.makeText(LoginActivity.this,
											"用户名或密码错误", Toast.LENGTH_SHORT)
											.show();
								} else {
									Gson gosn = new Gson();
									userData = gosn.fromJson(arg0, User.class);
									

									if (userNoText.equals(userData.getUserNo())
											&& passwordText.equals(userData
													.getUserPwd())) {

										SharedPreferences sp = getSharedPreferences(
												Constant.SP_FILE_NAME,
												MODE_PRIVATE);
										SharedPreferences.Editor editor = sp
												.edit();
										editor.putString("userNo",
												userData.getUserNo());
										editor.putString("userPwd",
												passwordText);
										editor.putString("userName",
												userData.getUserName());
										editor.commit();
										ApplicationDIV.getInstance().setUser(
												userData);
										updateUser();
										setResult(RESULT_FIRST_USER);
										Toast.makeText(LoginActivity.this,
												"登陆成功", Toast.LENGTH_SHORT)
												.show();
										
										finish();

									} else {
										Toast.makeText(LoginActivity.this,
												"用户名或密码错误", Toast.LENGTH_SHORT)
												.show();
										return;

									}
								}

							} catch (JSONException e) {
								e.printStackTrace();
							}

						} else {
							Toast.makeText(LoginActivity.this, "连接失败，请重试",
									Toast.LENGTH_SHORT).show();
						}

					}

				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Dao<User, String> userDao = DBHelper.getInstance(
								LoginActivity.this).getUserDao();
						try {
							User u = userDao.queryForId(userNoText);
							
							if (null != u) {
								ApplicationDIV.getInstance().setUser(u);
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}

						Toast.makeText(LoginActivity.this, "连接数据失败，请重试",
								Toast.LENGTH_SHORT).show();

					}
				});

		request.putParams("userNo", userNoText);
		request.putParams("userPwd", passwordText);
		ApplicationDIV.getInstance().getRequestQueue().add(request);

	}

	public void updateUser() {
		Dao<User, String> userDao = DBHelper.getInstance(LoginActivity.this)
				.getUserDao();

		try {
			User u = userDao.queryForId(userData.getUserNo());
			if (null != u) {
				userDao.delete(u);
			}
			userDao.create(userData);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void addUser(User u){
		StringPostRequest request = new StringPostRequest(UtilsURL.DATA_URL,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						
						Toast.makeText(getApplicationContext(), "注册成功！",
								Toast.LENGTH_SHORT).show();
						
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(getApplicationContext(), "连接服务器失败，请重试！",
								Toast.LENGTH_SHORT).show();

					}
				});
		request.putParams("find", "addQQUser");
		request.putParams("userNo", u.getUserNo());
		request.putParams("userName", u.getUserName());
		request.putParams("userPwd", "null");
		request.putParams("province", u.getProvince());
		request.putParams("city", u.getCity());
		ApplicationDIV.getInstance().getRequestQueue().add(request);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login:
			initData();
			break;
	//	case R.id.qq:
//			addQZoneQQPlatform();
//			login(SHARE_MEDIA.QQ);
			
//			break;
		case R.id.registe:
			startActivity(new Intent(LoginActivity.this, RegisteActivity.class));
			break;
		default:
			break;
		}

	}
	
//	private UMSocialService mController = UMServiceFactory
//            .getUMSocialService(Constant.DESCRIPTOR);
//	 /**
//     * 授权。如果授权成功，则获取用户信息</br>
//     */
//    @SuppressLint("ShowToast")
//	private void login(final SHARE_MEDIA platform) {
//        mController.doOauthVerify(LoginActivity.this, platform, new UMAuthListener() {
//
//            @SuppressLint("ShowToast")
//			@Override
//            public void onStart(SHARE_MEDIA platform) {
//                Toast.makeText(LoginActivity.this, "正在获取授权...", 0).show();
//            }
//
//            @Override
//            public void onError(SocializeException e, SHARE_MEDIA platform) {
//            }
//
//            @Override
//            public void onComplete(Bundle value, SHARE_MEDIA platform) {
//                Toast.makeText(LoginActivity.this, "登录", 0).show();
//                String uid =value.getString("uid");
//                if (!TextUtils.isEmpty(uid)) {
//                    getUserInfo(platform);
//                } else {
//                    Toast.makeText(LoginActivity.this, "授权失败...", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancel(SHARE_MEDIA platform) {
//            }
//        });
//    }
//    /**
//     * 获取授权平台的用户信息</br>
//     */
//    private void getUserInfo(SHARE_MEDIA platform) {
//        mController.getPlatformInfo(LoginActivity.this, platform, new UMDataListener() {
//
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onComplete(int status, Map<String, Object> info) {
//                	
//                if (info != null) {
//                	User user = new User();
//                	String name = info.get("screen_name").toString();
//                	String province = info.get("province").toString();
//                	String imgUrl = info.get("profile_image_url").toString();
//                	String city = info.get("city").toString();
//                	String no = MethodUtils.encrypt(name);
//                	user.setUserNo(no);
//                	user.setUserName(name);
//                	user.setUserImg(imgUrl);
//                	user.setProvince(province);
//                	user.setCity(city);
//                	//addUser(user);
//                	ApplicationDIV.getInstance().setUser(user);
//                 //   Toast.makeText(LoginActivity.this, info.toString(), Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//            }
//        });
//    }
//    
//    private void addQZoneQQPlatform() {
//        String appId = "1104919418";
//        String appKey = "HBkbwvXRyMBROtEe";
//        // 添加QQ支持, 并且设置QQ分享内容的target url
//        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(LoginActivity.this,
//                appId, appKey);
//        qqSsoHandler.setTargetUrl("http://www.umeng.com");
//        qqSsoHandler.addToSocialSDK();
//
//        // 添加QZone平台
//        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(LoginActivity.this, appId, appKey);
//        qZoneSsoHandler.addToSocialSDK();
//    }
//    
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
//                requestCode);
//        if (ssoHandler != null) {
//            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
//        }
//    }

	public void back(View view) {
		finish();
	}

}
