package com.example.w3c_school;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.example.w3c_school.adapter.DiscussAdapter;
import com.example.w3c_school.entity.Discuss;
import com.example.w3c_school.entity.Topic;
import com.example.w3c_school.entity.User;
import com.example.w3c_school.utils.ApplicationDIV;
import com.example.w3c_school.utils.Constant;
import com.example.w3c_school.utils.FaceConversionUtil;
import com.example.w3c_school.utils.ImageLoaderUitil;
import com.example.w3c_school.utils.MethodUtils;
import com.example.w3c_school.utils.StringPostRequest;
import com.example.w3c_school.utils.UtilsURL;
import com.example.w3c_school.view.AutoListView;
import com.example.w3c_school.view.AutoListView.OnRefreshListener;
import com.example.w3c_school.view.FaceRelativeLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableString;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DiscussActivity extends BaseActivity implements OnClickListener,
		OnRefreshListener {

	private Button mBtnSend;

	private EditText mEditTextContent;

	private AutoListView mListView;

	private DiscussAdapter mAdapter;

	private List<Discuss> mDataArrays;
	private User user;

	private int parentId;

	private int isGood;

	private Handler handler = new Handler();

	private CheckBox good;
	private TextView discussNumView, goodNumView, topicContent, topicDate,
			author;

	private ImageView userImg;
	private Topic t;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_topic_reply_list);
		user = ApplicationDIV.getInstance().getUser();

		isGood = getIntent().getIntExtra("isGood", 0);
		t = (Topic) getIntent().getSerializableExtra("topic");
		good = (CheckBox) findViewById(R.id.goodT);
		discussNumView = (TextView) findViewById(R.id.discussNumT);
		discussNumView.setText(t.getReplyment() + "");
		goodNumView = (TextView) findViewById(R.id.goodNumT);
		goodNumView.setText(t.getGoodNum() + "");
		topicContent = (TextView) findViewById(R.id.contentT);
		SpannableString spannableString = FaceConversionUtil.getInstace()
				.getExpressionString(this, t.getContent());
		topicContent.setText(spannableString);
		topicDate = (TextView) findViewById(R.id.dateT);
		topicDate.setText(t.getDate());
		author = (TextView) findViewById(R.id.nameT);
		author.setText(t.getUserName());
		if (isGood == 1) {
			good.setChecked(true);
		} else {
			good.setChecked(false);
		}
		userImg = (ImageView) findViewById(R.id.user_imgT);
		ImageLoaderUitil.display(t.getImg(), userImg, this);
		good.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					goodNumView.setText(Integer.parseInt(goodNumView.getText()
							.toString()) + 1 + "");
				} else {
					goodNumView.setText(Integer.parseInt(goodNumView.getText()
							.toString()) - 1 + "");
				}

			}
		});
		mListView = (AutoListView) findViewById(R.id.replyList);
		mBtnSend = (Button) findViewById(R.id.btn_send);
		mBtnSend.setOnClickListener(this);
		mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
		mDataArrays = new ArrayList<Discuss>();

		parentId = getIntent().getIntExtra("id", 0);

		mAdapter = new DiscussAdapter(mDataArrays, this);
		mListView.setAdapter(mAdapter);
		// 获取编辑框焦点
		mEditTextContent.setFocusable(true);
		// 打开软键盘
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		mListView.setLoadEnable(false);
		mListView.setOnRefreshListener(this);
		mListView.selfRefreshing();
		// initData();
		initView();

	}

	private void initView() {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Discuss reply = (Discuss) mAdapter.getItem(arg2 - 1);
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
				editor.putString("author", reply.getUserName());
				editor.commit();
				if ("empty".equals(reply.getNo())) {
					mEditTextContent.setText("回复" + arg2 + "楼：");
				} else {
					mEditTextContent.setText("回复@" + reply.getUserName() + "：");
				}
				// 将光标置于文字后
				Editable etext = mEditTextContent.getText();
				Selection.setSelection(etext, etext.length());

			}
		});

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
				discuss.setNo("empty");
			} else {
				discuss.setNo(user.getUserNo());
				discuss.setUserName(user.getUserName());
			}

			if (contString.indexOf("回复") == -1) {
				isQuote = 0;
			} else {
				isQuote = 1;
			}
			discuss.setIsQuote(isQuote);
			if (isQuote == 1) {
				discuss.setQuoteFloor(floor);
			} else {
				discuss.setQuoteFloor(0);
			}

			discuss.setFloor(mDataArrays.size() + 1);
			discuss.setContent(contString);
			discuss.setDate(MethodUtils.getDate());
			discuss.setParentId(parentId);
			// addConData(discuss);
			mDataArrays.add(discuss);
			discussNumView.setText(Integer.parseInt(discussNumView.getText()
					.toString()) + 1 + "");
			mAdapter.notifyDataSetChanged();
			mEditTextContent.setText("");
			SharedPreferences.Editor editor = sp.edit();
			editor.putInt("isQuote", 0);
			editor.commit();
		}
	}

	public void back(View view) {
		finish();
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
		request.putParams("content", discuss.getContent());
		request.putParams("date", discuss.getDate());
		request.putParams("floor", discuss.getFloor() + "");
		request.putParams("isQuote", discuss.getIsQuote() + "");
		request.putParams("quoteFloor", discuss.getQuoteFloor() + "");
		request.putParams("no", discuss.getNo());

		ApplicationDIV.getInstance().getRequestQueue().add(request);
	}

	private void getConData() {
		//mListView.selfRefreshing();
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
		ApplicationDIV.getInstance().getRequestQueue().add(request);
	}

	@Override
	public void onRefresh() {
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

				getConData();

			}
		}, 3000);

	}

}
