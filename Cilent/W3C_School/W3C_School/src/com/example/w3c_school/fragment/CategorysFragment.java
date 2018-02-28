package com.example.w3c_school.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.internal.ListenerClass.NONE;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.example.w3c_school.ContentItemActivity;
import com.example.w3c_school.R;
import com.example.w3c_school.adapter.CategoryAdapter;
import com.example.w3c_school.entity.CategoryContent;
import com.example.w3c_school.entity.ContentItem;
import com.example.w3c_school.utils.ApplicationDIV;
import com.example.w3c_school.utils.StringPostRequest;
import com.example.w3c_school.utils.UtilsURL;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;

public class CategorysFragment extends BaseFragment {
	private int categoryId;
	private LinearLayout containerView;
	private List<CategoryContent> mCourseTagList;
	private long startTime;
	private boolean isInit;
	private String tag;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCourseTagList = new ArrayList<CategoryContent>();
		Bundle bundle = getArguments();
		categoryId = bundle.getInt("categoryId");
		tag = bundle.getString("tag");
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.categorys_fragment, null);
		containerView = (LinearLayout) view.findViewById(R.id.container);
		// getCategorys();

		startTime = System.currentTimeMillis();
		isInit = true;
		if (isVisible) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					getCategorys();
				}
			}, 800);
		}
		return view;
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				@SuppressWarnings("unchecked")
				List<CategoryContent> cn = (ArrayList<CategoryContent>) msg.obj;
				if (null != cn && cn.size() > 0) {
					mCourseTagList.clear();
					mCourseTagList.addAll(cn);
					for (CategoryContent cc : cn) {
						getContents(cc);
					}

				}
				break;
			case 2:
				if (mCourseTagList.size() > 0 && null != mCourseTagList) {
					initView();
				}

				break;

			default:
				break;
			}
		}

	};

	public void getCategorys() {

		StringPostRequest postRequest = new StringPostRequest(
				UtilsURL.DATA_URL, new Listener<String>() {
					@Override
					public void onResponse(String arg0) {
						List<CategoryContent> curList = new ArrayList<CategoryContent>();
						curList.clear();
						JSONArray array;
						try {
							array = new JSONArray(arg0);
							if (null != array && array.length() > 0) {
								for (int j = 0; j < array.length(); j++) {
									JSONObject jsonObject = (JSONObject) array
											.get(j);
									int parentId = jsonObject
											.getInt("parentId");
									String cTitle = jsonObject
											.getString("title");
									int id = jsonObject.getInt("id");
									CategoryContent cct = new CategoryContent(
											parentId, cTitle, id, tag);
									//
									curList.add(cct);

								}
								Message msg = handler.obtainMessage();
								msg.obj = curList;
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
						Toast.makeText(getActivity(), "连接数据失败，请重试",
								Toast.LENGTH_SHORT).show();
					}
				});
		postRequest.putParams("find", "contentList");
		postRequest.putParams("tableName", tag + "_category");
		postRequest.putParams("parentId", categoryId + "");

		ApplicationDIV.getInstance().getRequestQueue().add(postRequest);
	}

	private void getContents(final CategoryContent ccy) {
		StringPostRequest request = new StringPostRequest(UtilsURL.DATA_URL,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						Gson gson = new Gson();
						List<ContentItem> items = gson.fromJson(arg0,
								new TypeToken<ArrayList<ContentItem>>() {
								}.getType());
						if (null != items && items.size() > 0) {
							ccy.getChildren().addAll(items);
							for (ContentItem ct : items) {
								if (ct.getLabel() == 1) {
									Message msg = handler.obtainMessage();
									msg.what = 2;
									handler.sendMessage(msg);
									break;
								}
							}

						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(getActivity(), "连接数据失败，请重试",
								Toast.LENGTH_SHORT).show();

					}
				});
		request.putParams("find", "contents");
		request.putParams("tableName", tag + "_content");
		request.putParams("parentId", ccy.getId() + "");
		ApplicationDIV.getInstance().getRequestQueue().add(request);

	}

	@SuppressLint("InflateParams")
	private void initView() {
		for (int i = 0; i < mCourseTagList.size(); i++) {
			CheckBox textTag = (CheckBox) LayoutInflater.from(getActivity())
					.inflate(R.layout.course_tag_check, null);
			textTag.setTag("textTag" + i);
			textTag.setId(i + 1);
			textTag.setText(mCourseTagList.get(i).getTitle());
			// textTag.setBackgroundResource(R.drawable.contentlist);
			containerView.addView(textTag);

			final ListView courseList = new ListView(getActivity());
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
					80 * mCourseTagList.get(i).getChildren().size());
			courseList.setLayoutParams(lp);
			courseList.setTag("courseList" + i);
			courseList.setId(i);
			courseList.setVerticalScrollBarEnabled(false);
			List<ContentItem> curCourse = new ArrayList<ContentItem>();
			curCourse.addAll(mCourseTagList.get(i).getChildren());
			containerView.addView(courseList);
			CategoryAdapter adapter = new CategoryAdapter(curCourse,
					getActivity());
			courseList.setAdapter(adapter);
			courseList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					ContentItem item = (ContentItem) courseList.getAdapter()
							.getItem(arg2);
					Intent intent = new Intent(getActivity(),
							ContentItemActivity.class);
					intent.putExtra("item", item);
					intent.putExtra("type", tag);
					startActivity(intent);

				}
			});
			textTag.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					if (isChecked) {
						courseList.setVisibility(View.VISIBLE);
					} else {
						courseList.setVisibility(View.GONE);
					}

				}
			});

		}
	}

	@Override
	public void lazyLoadRefresh() {
		long endTime = System.currentTimeMillis();
		if (isVisible && isInit) {
			if ((mCourseTagList.size() > 0 && (endTime - startTime) > 1000 * 60)
					|| mCourseTagList.size() <= 0) {
				getCategorys();
				startTime = System.currentTimeMillis();
			}

		}

	}

}
