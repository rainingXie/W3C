package com.example.w3c_school;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;

import com.example.w3c_school.adapter.ContentItemAdapter;
import com.example.w3c_school.dao.SaveDao;
import com.example.w3c_school.entity.ContentItem;
import com.example.w3c_school.entity.User;
import com.example.w3c_school.utils.ApplicationDIV;
import com.example.w3c_school.view.AutoListView;
import com.example.w3c_school.view.AutoListView.OnRefreshListener;

public class SaveActivity extends BaseActivity implements
		OnRefreshListener {

	private static final int REQ_CODE = 1;
	private ContentItemAdapter adapter;
	private List<ContentItem> itemsSave;

	private AutoListView saveList;

	private User user;
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save);
		saveList = (AutoListView) findViewById(R.id.mylist);
		user = ApplicationDIV.getInstance().getUser();
		itemsSave = new ArrayList<ContentItem>();
		adapter = new ContentItemAdapter(itemsSave, this);
		saveList.setAdapter(adapter);
		saveList.setOnRefreshListener(this);
		saveList.setAdapter(adapter);
		// initData();
		if (null != user) {
			getConData();
		}

	}

	/**
	 * 获得服务器个人收藏
	 */
	private void getConData() {

	}

	/**
	 * 获得本地数据库个人收藏
	 */
	private void initData() {
		SaveDao saveDao = new SaveDao(getApplicationContext());
		List<ContentItem> saves = saveDao.querySave(user);
		if (saves.size() > 0 && null != saves) {
			itemsSave.clear();
			itemsSave.addAll(saves);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE && resultCode == RESULT_OK) {
			getConData();
		}
	}

	/**
	 * 退出该界面
	 */
	public void back(View v) {
		finish();
	}

	

	@Override
	public void onRefresh() {
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

				// getConData();
				saveList.onRefreshComplete();

			}
		}, 4000);
		
	}

}
