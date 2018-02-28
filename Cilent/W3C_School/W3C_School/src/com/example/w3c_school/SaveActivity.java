package com.example.w3c_school;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.example.w3c_school.adapter.CourseAdapter;
import com.example.w3c_school.dao.SaveDao;
import com.example.w3c_school.entity.ContentItem;
import com.example.w3c_school.entity.User;
import com.example.w3c_school.utils.ApplicationDIV;
import com.example.w3c_school.utils.Constant;
import com.example.w3c_school.utils.StringPostRequest;
import com.example.w3c_school.utils.UtilsURL;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class SaveActivity extends BaseActivity implements OnRefreshListener2<ListView>{

	private static final int REQ_CODE = 1;
	private CourseAdapter adapter;
	private List<ContentItem> itemsSave;

	private PullToRefreshListView saveList;

	private User user;
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.save_list);
		saveList = (PullToRefreshListView) findViewById(R.id.mylist);
		user = ApplicationDIV.getInstance().getUser();
		itemsSave = new ArrayList<ContentItem>();
		adapter = new CourseAdapter(itemsSave, SaveActivity.this);
		saveList.setAdapter(adapter);
		saveList.setOnRefreshListener(this);
		saveList.setAdapter(adapter);
		//initData();
		if (null != user) {
			getConData();
		}

	}

	/**
	 * 获得服务器个人收藏
	 */
	private void getConData() {
		initData();
		StringPostRequest request = new StringPostRequest(UtilsURL.DATA_URL,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						saveList.onRefreshComplete();
						itemsSave.clear();
						try {
							JSONArray array = new JSONArray(arg0);
							if (null != array && array.length() > 0) {
								for(int i = 0 ; i < array.length();i++){
									JSONObject jsonObject = (JSONObject) array.get(i);
									int id = jsonObject.getInt("id");
									int testTag = jsonObject.getInt("testTag");
									String title = jsonObject.getString("title");
									String userNo = jsonObject.getString("userNo");
									String codeShow = jsonObject.getString("codeShow");
									String tryCode = jsonObject.getString("tryCode");
									String contentText = jsonObject.getString("contentText");
									
									ContentItem contentItem = new ContentItem(id, title, contentText, codeShow, testTag, userNo, tryCode);
									itemsSave.add(contentItem);
//									SaveDao saveDao = new SaveDao(getApplicationContext());
									
//									List<ContentItem> list = saveDao.querySave(contentItem,
//											user.getUserNo());
//									if (list.size() > 0) {
//										contentItem.setFavorTag(Constant.SAVE);
//									}else{
//										contentItem.setFavorTag(Constant.SAVE);
//										saveDao.addSave(contentItem);
//									}
								}
								adapter.notifyDataSetChanged();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
						
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						saveList.onRefreshComplete();
						initData();
						Toast.makeText(SaveActivity.this, "连接数据失败，请重试",
								Toast.LENGTH_SHORT).show();

					}
				});
		request.putParams("find", "userCollect");
		request.putParams("userNo", user.getUserNo());
		ApplicationDIV.getInstance().getRequestQueue().add(request);
	}

	/**
	 * 获得本地数据库个人收藏
	 */
	private void initData() {
		SaveDao saveDao = new SaveDao(SaveActivity.this);
		itemsSave.clear();
		if (null != user) {
			List<ContentItem> localSave = saveDao.querySave(user.getUserNo());
			if (null != localSave && localSave.size() > 0) {
				itemsSave.addAll(localSave);
				adapter.notifyDataSetChanged();
			}
		}

		saveList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ContentItem item = (ContentItem) adapter.getItem(arg2-1);
				Intent intent = new Intent(getApplicationContext(),
						ContentItemActivity.class);
//				SaveDao saveDao = new SaveDao(getApplicationContext());
//				List<ContentItem> list = saveDao.querySave(item,
//						user.getUserNo());
//				if (list.size() > 0) {
					item.setFavorTag(Constant.SAVE);
//				}

				intent.putExtra("item", item);
				startActivityForResult(intent, REQ_CODE);

			}
		});
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
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		handler .postDelayed(new Runnable() {

			@Override
			public void run() {
				
				getConData();
				saveList.onRefreshComplete();

			}
		}, 4000);
		
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		saveList.onRefreshComplete();
	}

}
