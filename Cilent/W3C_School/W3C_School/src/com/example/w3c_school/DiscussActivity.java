package com.example.w3c_school;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.example.w3c_school.adapter.DiscussAdapter;
import com.example.w3c_school.entity.Discuss;
import com.example.w3c_school.entity.User;
import com.example.w3c_school.utils.ApplicationDIV;
import com.example.w3c_school.utils.Constant;
import com.example.w3c_school.utils.StringPostRequest;
import com.example.w3c_school.utils.UtilsURL;
import com.example.w3c_school.view.FaceRelativeLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class DiscussActivity extends BaseActivity implements OnClickListener,
		OnRefreshListener2<ListView> {

	private Button mBtnSend;

	private EditText mEditTextContent;

	private PullToRefreshListView mListView;

	private DiscussAdapter mAdapter;

	private List<Discuss> mDataArrays;
	private User user;

	private String type;
	private int parentId;

	private Handler handler = new Handler();

	public void onCreate(Bundle savedInstanceState) {
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.discuss_list);
		mListView = (PullToRefreshListView) findViewById(R.id.discussList);
		mBtnSend = (Button) findViewById(R.id.btn_send);
		mBtnSend.setOnClickListener(this);
		mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
		mDataArrays = new ArrayList<Discuss>();
		user = ApplicationDIV.getInstance().getUser();
		parentId = getIntent().getIntExtra("id", 0);
		type = getIntent().getStringExtra("type");
		mAdapter = new DiscussAdapter(mDataArrays, this);
		mListView.setAdapter(mAdapter);
		// 获取编辑框焦点
		mEditTextContent.setFocusable(true);
		// 打开软键盘
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		mListView.setMode(Mode.PULL_FROM_START);
		mListView.setOnRefreshListener(this);
		initData();
		initView();
	}

	private void initView() {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Discuss discuss = (Discuss) mAdapter.getItem(arg2 - 1);
				Log.i("arg2", arg2 + "");
				SharedPreferences sp = getSharedPreferences(
						Constant.SP_FILE_NAME, MODE_PRIVATE);
				SharedPreferences.Editor editor = sp.edit();

				if (arg2 == 1) {
					editor.putInt("floor", 1);
				} else {
					editor.putInt("floor", arg2);
				}
				editor.putInt("isQuote", 1);
				editor.putString("author", discuss.getUserName());
				editor.commit();
				if ("empty".equals(discuss.getNo())) {
					mEditTextContent.setText("回复" + arg2 + "楼：");
				} else {
					mEditTextContent.setText("回复@" + discuss.getUserName()
							+ "：");
				}

			}
		});
	}

	private void addConData(Discuss discuss) {
		StringPostRequest request = new StringPostRequest(UtilsURL.DATA_URL,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						Log.i("insertInfo", "插入数据成功！");
						Toast.makeText(getApplicationContext(), "发表成功！",
								Toast.LENGTH_SHORT).show();
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(getApplicationContext(), "连接服务器失败，请重试！",
								Toast.LENGTH_SHORT).show();
					}
				});
		request.putParams("find", "addDiscuss");
		request.putParams("parentId", discuss.getParentId() + "");
		request.putParams("type", discuss.getType());
		request.putParams("content", discuss.getContent());
		request.putParams("date", discuss.getDate());
		request.putParams("floor", discuss.getFloor() + "");
		request.putParams("isQuote", discuss.getIsQuote() + "");
		request.putParams("quoteFloor", discuss.getQuoteFloor() + "");
		request.putParams("no", discuss.getNo());

		ApplicationDIV.getInstance().getRequestQueue().add(request);
	}

	private void getConData() {
		mListView.setRefreshing();
		StringPostRequest request = new StringPostRequest(UtilsURL.DATA_URL,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						mListView.onRefreshComplete();
						Gson gson = new Gson();
						List<Discuss> dList = gson.fromJson(arg0,
								new TypeToken<ArrayList<Discuss>>() {
								}.getType());
						if (null != dList && dList.size() > 0) {
							mDataArrays.clear();
							mDataArrays.addAll(dList);
							mAdapter.notifyDataSetChanged();
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						mListView.onRefreshComplete();
						Toast.makeText(getApplicationContext(), "连接服务器失败，请重试！",
								Toast.LENGTH_SHORT).show();
					}
				});
		request.putParams("find", "discussItem");
		request.putParams("parentId", parentId + "");
		request.putParams("type", type);
		ApplicationDIV.getInstance().getRequestQueue().add(request);
	}

	private void initData() {
		getConData();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_send:
			send();
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& ((FaceRelativeLayout) findViewById(R.id.FaceRelativeLayout))
						.hideFaceView()) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void send() {
		SharedPreferences sp = getSharedPreferences(Constant.SP_FILE_NAME,
				MODE_PRIVATE);
		int floor = sp.getInt("floor", 1);
		int isQuote = sp.getInt("isQuote", 0);
		String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0) {
			Discuss discuss = new Discuss();
			if (user == null) {
			//	discuss.setImg("empty");
			//	discuss.setUserName("匿名");
				discuss.setNo("empty");
			} else {
			//	discuss.setImg(user.getUserImg());
			//	discuss.setUserName(user.getUserName());
				discuss.setNo(user.getUserNo());
			}
			discuss.setIsQuote(isQuote);
			if (isQuote == 1) {
				discuss.setQuoteFloor(floor);
			} else {
				discuss.setQuoteFloor(0);
			}

			discuss.setFloor(mDataArrays.size()+1);
			discuss.setContent(contString);
			discuss.setDate(getDate());
			discuss.setParentId(parentId);
			discuss.setType(type);
			addConData(discuss);
			mDataArrays.add(discuss);
			
			mAdapter.notifyDataSetChanged();
			mListView.setRefreshing();
			mEditTextContent.setText("");
			SharedPreferences.Editor editor = sp.edit();
			editor.putInt("isQuote", 0);
			editor.commit();
		}
	}

	private String getDate() {
		Calendar c = Calendar.getInstance();

		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH)+1);
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String mins = String.valueOf(c.get(Calendar.MINUTE));

		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":"
				+ mins);

		return sbBuffer.toString();
	}

	public void back(View view) {
		finish();
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		handler .postDelayed(new Runnable() {

			@Override
			public void run() {
				
				getConData();

			}
		}, 6000);

	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		mListView.onRefreshComplete();

	}
}