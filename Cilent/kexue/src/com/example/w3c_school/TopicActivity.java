package com.example.w3c_school;

import java.util.ArrayList;
import java.util.List;

import com.example.w3c_school.adapter.TopicAdapter;
import com.example.w3c_school.adapter.TopicAdapter.ChooseListener;
import com.example.w3c_school.entity.Topic;
import com.example.w3c_school.entity.User;
import com.example.w3c_school.utils.ApplicationDIV;
import com.example.w3c_school.utils.MethodUtils;
import com.example.w3c_school.view.AutoListView;
import com.example.w3c_school.view.AutoListView.OnRefreshListener;
import com.example.w3c_school.view.FaceRelativeLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class TopicActivity extends BaseActivity implements OnRefreshListener,OnClickListener,ChooseListener{

	
	private static final int TOPIC_REQ = 1;
	private List<Topic> topicList;
	private TopicAdapter adapter;
	private AutoListView topicListView;
	private Button add;
	
	private LinearLayout rl_bottom;
	private Button mBtnSend;

	private EditText mEditTextContent;
	
	private User user;
	private int parentId;
    private String all;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_topic_list);
		user =  ApplicationDIV.getInstance().getUser();
		parentId = getIntent().getIntExtra("parentId", 0);
		all = getIntent().getStringExtra("all");
		topicListView = (AutoListView) findViewById(R.id.mylist);
		add = (Button) findViewById(R.id.add);
		rl_bottom = (LinearLayout) findViewById(R.id.rl_bottom);
		topicList= new ArrayList<Topic>();
		mBtnSend = (Button) findViewById(R.id.btn_send);
		mBtnSend.setOnClickListener(this);
		mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
		adapter = new TopicAdapter(topicList, this);
		
		
		topicListView.setLoadEnable(false);
		topicListView.setOnRefreshListener(this);
		topicListView.setAdapter(adapter);
		topicListView.selfRefreshing();
		adapter.setListener(this);
		
		add.setOnClickListener(this);
		
		topicListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Topic topic = (Topic) adapter.getItem(arg2 - 1);
				Intent intent = new Intent(TopicActivity.this,
						DiscussActivity.class);
				intent.putExtra("topic", topic);
				startActivityForResult(intent, TOPIC_REQ);
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add:
			rl_bottom.setVisibility(View.VISIBLE);
			break;
		case R.id.btn_send:
			send();
		default:
			break;
		}
		
	}
	private void send() {
		Topic t = new Topic();
		t.setContent(mEditTextContent.getText().toString());
		t.setDate(MethodUtils.getDate());
		t.setGoodNum(0);
		t.setGoodPerson(null);
		t.setId(topicList.size() + 1+"");
		t.setImg(null);
		t.setReplyment(0);
		if (null!=user) {
			t.setUserName(user.getUserName());
		}else {
			t.setUserName("сн©м");
		}
		
		t.setIsGood(0);
		topicList.add(t);
		adapter.notifyDataSetChanged();
		mEditTextContent.setText("");
	}
	@Override
	public void chooseGood(Topic t) {
		t.setGoodNum(t.getGoodNum()+1);
		adapter.notifyDataSetChanged();
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
	

	public void back(View view) {
		finish();
	}

	@Override
	public void unchooseGood(Topic t) {
		t.setGoodNum(t.getGoodNum()-1);
		adapter.notifyDataSetChanged();
		
	}

	@Override
	public void clickDiscuss(Topic t) {
		Intent intent = new Intent(this,DiscussActivity.class);
		intent.putExtra("isGood", t.getIsGood());
		intent.putExtra("topic", t);
		startActivity(intent);
		
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}

	
	
}
