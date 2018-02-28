package com.example.w3c_school;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.example.w3c_school.dao.SaveDao;
import com.example.w3c_school.entity.ContentItem;
import com.example.w3c_school.entity.User;
import com.example.w3c_school.utils.ApplicationDIV;
import com.example.w3c_school.utils.Constant;
import com.example.w3c_school.utils.StringPostRequest;
import com.example.w3c_school.utils.UtilsURL;

public class ContentItemActivity extends BaseActivity implements
		OnClickListener {

	private TextView title;
	private WebView contentText;
	private WebView codeShow;
	private TextView testCode;
	private ProgressDialog progressDialog;

	private boolean flagText = false;
	private boolean flagShow = false;
	private CheckBox save;

	private ContentItem item;

	private User user;
	private String type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_item);
		title = (TextView) findViewById(R.id.title);
		contentText = (WebView) findViewById(R.id.contentText);
		codeShow = (WebView) findViewById(R.id.codeShow);
		testCode = (TextView) findViewById(R.id.testCode);
		save = (CheckBox) findViewById(R.id.save);
		item = (ContentItem) getIntent().getSerializableExtra("item");
		type = getIntent().getStringExtra("type");
		user = ApplicationDIV.getInstance().getUser();
		findViewById(R.id.discuss).setOnClickListener(this);
		setResult(RESULT_OK);
		if (item.isFavorTag() == Constant.SAVE) {
			save.setChecked(true);
		}
		initView();
		initData();

		if (null != user) {
			getConData();
		}
		saveContent();
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:

				@SuppressWarnings("unchecked")
				List<ContentItem> saves = (List<ContentItem>) msg.obj;
				if (saves.size() > 0 && null != saves) {
					for (ContentItem cc : saves) {
						if (cc.getTitle().equals(item.getTitle())) {
							save.setChecked(true);
						}
					}
				}

			//	saveContent();

				break;

			default:
				break;
			}
		}

	};

	private void initView() {
		progressDialog = new ProgressDialog(ContentItemActivity.this);// 提示可为空
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage("正在加载...");
		progressDialog.show();

		testCode.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		testCode.setTextColor(Color.RED);
		testCode.setOnClickListener(this);
		if (item.isTestTag() == 0) {
			testCode.setVisibility(View.GONE);
		} else {
			testCode.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 判断是否收藏
	 */
	public void saveContent() {

		save.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (null == user) {
					save.setChecked(false);
					Toast.makeText(getApplicationContext(), "登陆后才能收藏",
							Toast.LENGTH_SHORT).show();
				} else {
					if (isChecked) {
						initConData();

					} else {
						conCancelSave();

					}
				}

			}
		});
	}

	/**
	 * 添加收藏到服务器
	 */
	private void initConData() {
		Log.i("收藏", "收藏");
		StringPostRequest request = new StringPostRequest(UtilsURL.DATA_URL,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {

//						SaveDao saveDao = new SaveDao(getApplicationContext());
//						saveDao.updateSave(item, user.getUserNo());
						Toast.makeText(getApplicationContext(), "已收藏",
								Toast.LENGTH_SHORT).show();

					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						save.setChecked(false);
						Toast.makeText(ContentItemActivity.this, "连接服务器失败，请重试",
								Toast.LENGTH_SHORT).show();

					}
				});
		if (item.getTitle() == null) {
			request.putParams("title", "empty");
		} else {
			request.putParams("title", item.getTitle());
		}

		if (item.getContentText() == null) {
			request.putParams("content", "empty");
		} else {
			request.putParams("content", item.getContentText());
		}
		if (item.getCodeShow() == null) {
			request.putParams("code", "empty");
		} else {
			request.putParams("code", item.getCodeShow());
		}

		request.putParams("userNo", user.getUserNo());
		if (item.getTestTag() == 0) {
			request.putParams("tryCode", "empty");
		} else {
			request.putParams("tryCode", item.getTryCode());
		}
		request.putParams("testTag", item.getTestTag() + "");
		request.putParams("find", "userCollectInsert");
		ApplicationDIV.getInstance().getRequestQueue().add(request);
	}

	/**
	 * 从服务器上取消收藏
	 */
	private void conCancelSave() {
		StringPostRequest request = new StringPostRequest(UtilsURL.DATA_URL,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						SaveDao saveDao = new SaveDao(getApplicationContext());
						saveDao.cancelSave(item);
						Toast.makeText(getApplicationContext(), "取消收藏",
								Toast.LENGTH_SHORT).show();

					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(ContentItemActivity.this, "连接服务器失败，请重试",
								Toast.LENGTH_SHORT).show();

					}
				});
		request.putParams("id", item.getId() + "");
		request.putParams("find", "userCollectDelete");
		ApplicationDIV.getInstance().getRequestQueue().add(request);
	}

	/**
	 * 页面数据
	 */
	private void initData() {
		title.setText(item.getTitle());
		contentText.loadDataWithBaseURL(null, item.getContentText(),
				"text/html", "utf-8", null);

		contentText.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				flagText = true;
				if (flagText && flagShow) {
					if (progressDialog != null && progressDialog.isShowing()) {// 数据加载完毕时
						progressDialog.dismiss();
					}
				}
			}

		});
		codeShow.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				flagShow = true;
				if (flagText && flagShow) {
					if (progressDialog != null && progressDialog.isShowing()) {// 数据加载完毕时
						progressDialog.dismiss();
					}
				}

			}

		});

		codeShow.loadDataWithBaseURL(null, item.getCodeShow(), "text/html",
				"utf-8", null);

	}

	@SuppressLint("InflateParams")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.testCode:
			Intent intent = new Intent(ContentItemActivity.this,
					TestCodeActivity.class);
			intent.putExtra("item", item);
			startActivity(intent);
			break;
		case R.id.discuss:
			// 跳转到评论列表
			if(user==null){
				Toast.makeText(getApplicationContext(), "注册登陆后才可以评论", Toast.LENGTH_SHORT).show();
			}else{
				intent = new Intent(this, DiscussActivity.class);
				intent.putExtra("id", item.getId());
				intent.putExtra("type", type);
				startActivity(intent);
			}
			
			break;
		default:
			break;
		}

	}

	public void back(View v) {
		finish();
	}

	/**
	 * 获得数据库上的收藏数据
	 */
	private void getConData() {
		final List<ContentItem> list = new ArrayList<ContentItem>();
		StringPostRequest request = new StringPostRequest(UtilsURL.DATA_URL,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {

						try {
							JSONArray array = new JSONArray(arg0);
							if (null != array && array.length() > 0) {
								for (int i = 0; i < array.length(); i++) {
									JSONObject jsonObject = (JSONObject) array
											.get(i);
									int id = jsonObject.getInt("id");
									int testTag = jsonObject.getInt("testTag");
									String title = jsonObject
											.getString("title");
									String userNo = jsonObject
											.getString("userNo");
									String codeShow = jsonObject
											.getString("codeShow");
									String tryCode = jsonObject
											.getString("tryCode");
									String contentText = jsonObject
											.getString("contentText");

									ContentItem contentItem = new ContentItem(
											id, title, contentText, codeShow,
											testTag, userNo, tryCode);
									list.add(contentItem);
								}
								Message msg = handler.obtainMessage();
								msg.obj = list;
								msg.what = 1;
								handler.sendMessage(msg);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						initData();
						Toast.makeText(ContentItemActivity.this, "连接数据失败，请重试",
								Toast.LENGTH_SHORT).show();

					}
				});
		request.putParams("find", "userCollect");
		request.putParams("userNo", user.getUserNo());
		ApplicationDIV.getInstance().getRequestQueue().add(request);
	}
}
