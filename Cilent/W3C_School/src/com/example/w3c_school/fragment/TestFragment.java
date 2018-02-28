package com.example.w3c_school.fragment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.example.w3c_school.ExerciseActivity;
import com.example.w3c_school.R;
import com.example.w3c_school.adapter.TestQuestionsAdapter;
import com.example.w3c_school.db.DBHelper;
import com.example.w3c_school.entity.Exercise;
import com.example.w3c_school.entity.TestQuestions;
import com.example.w3c_school.utils.ApplicationDIV;
import com.example.w3c_school.utils.StringPostRequest;
import com.example.w3c_school.utils.UtilsURL;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.j256.ormlite.dao.Dao;

public class TestFragment extends BaseFragment implements
		OnRefreshListener2<ListView> {

	private List<TestQuestions> qusetions;
	private TestQuestionsAdapter adapter;
	private PullToRefreshListView myShelf;
	private long startTime;
	private boolean isInit = false;
	private Handler handler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		qusetions = new ArrayList<TestQuestions>();
		adapter = new TestQuestionsAdapter(qusetions, getActivity());

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.shelf, null);
		myShelf = (PullToRefreshListView) view.findViewById(R.id.shelf_list);
		myShelf.setAdapter(adapter);
		myShelf.setMode(Mode.BOTH);
		myShelf.setOnRefreshListener(this);
		// initData();
		initView();

		startTime = System.currentTimeMillis();
		isInit = true;
		if (isVisible) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					getConData();
				}
			}, 800);
		}
		return view;
	}

	private void initView() {
		myShelf.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TestQuestions questions = (TestQuestions) adapter
						.getItem(arg2 - 1);
				Intent intent = new Intent(getActivity(),
						ExerciseActivity.class);
				intent.putExtra("questions", questions);
				startActivity(intent);

			}
		});
	}

	private void initData() {
		qusetions.clear();
		qusetions.add(new TestQuestions("HTML", "html试题",0));
		qusetions.add(new TestQuestions("HTML5", "html试题",0));
		qusetions.add(new TestQuestions("CSS", "html试题",0));
		qusetions.add(new TestQuestions("JavaScript", "html试题",0));
		adapter.notifyDataSetChanged();
	}

	private void getConData() {
		getLocalData();
		StringPostRequest request = new StringPostRequest(UtilsURL.DATA_URL,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						myShelf.onRefreshComplete();
						Gson gson = new Gson();
						qusetions.clear();
						List<Exercise> eList = gson.fromJson(arg0,
								new TypeToken<ArrayList<Exercise>>() {
								}.getType());
						Dao<TestQuestions, String> testQuestionsDao = DBHelper.getInstance(
								getActivity()).getTestQuestionsDao();
						if (eList.size() > 0 && null != eList) {
							for (Exercise e : eList) {
								TestQuestions q = new TestQuestions();
								q.setSubjectName(e.getType());
								q.setPageName(e.getName());
								q.setTag(e.getTag());
								qusetions.add(q);
								try {
									TestQuestions tq = testQuestionsDao.queryForId(q.getPageName());
									if(null!=tq){
										testQuestionsDao.delete(tq);
									}
									testQuestionsDao.create(q);
								} catch (SQLException e1) {
									e1.printStackTrace();
								}

							}
							adapter.notifyDataSetChanged();
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						myShelf.onRefreshComplete();
						getLocalData();
						Toast.makeText(getActivity(), "服务器连接失败，请重试！",
								Toast.LENGTH_SHORT).show();

					}

					
				});
		request.putParams("find", "allpagers");
		ApplicationDIV.getInstance().getRequestQueue().add(request);
	}

	@Override
	public void lazyLoadRefresh() {
		long endTime = System.currentTimeMillis();
		if (isVisible && isInit) {
			if ((qusetions.size() > 0 && (endTime - startTime) > 1000 * 60)
					|| qusetions.size() <= 0) {
				getConData();
				startTime = System.currentTimeMillis();
			}

		}

	}
	public void getLocalData() {
		Dao<TestQuestions, String> testQuestionsDao = DBHelper.getInstance(
				getActivity()).getTestQuestionsDao();
		try {
			List<TestQuestions> tList = testQuestionsDao.queryForAll();
			if(null!=tList && tList.size()>0){
				qusetions.clear();
				qusetions.addAll(tList);
				adapter.notifyDataSetChanged();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

				getConData();
			}
		}, 4000);

	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		myShelf.onRefreshComplete();

	}

}
