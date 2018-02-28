package com.example.w3c_school.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.w3c_school.DiscussActivity;
import com.example.w3c_school.R;
import com.example.w3c_school.TopicActivity;
import com.example.w3c_school.adapter.TopicAdapter;
import com.example.w3c_school.adapter.TopicAdapter.ChooseListener;
import com.example.w3c_school.entity.Topic;
import com.example.w3c_school.fragment.fragment.BannerFragment;
import com.example.w3c_school.view.AutoListView;
import com.example.w3c_school.view.AutoListView.OnRefreshListener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class HomeFragment extends Fragment implements OnRefreshListener,
		ChooseListener {

	private AutoListView commentList;
	private TopicAdapter topicAdapter;
	private List<Topic> topicListData;
	private Handler handler = new Handler();

	private BannerFragment bannerFragment;
	private TextView allDiscuss;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bannerFragment = new BannerFragment();
		topicListData = new ArrayList<Topic>();
		topicAdapter = new TopicAdapter(topicListData, getActivity());
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_home, null);
		commentList = (AutoListView) view.findViewById(R.id.commentList);
		allDiscuss = (TextView) view.findViewById(R.id.allDiscuss);
		if (view.findViewById(R.id.banner_fragment) != null) {
			getActivity().getSupportFragmentManager().beginTransaction()
					.add(R.id.banner_fragment, bannerFragment).commit();
		}
		commentList.setAdapter(topicAdapter);
		commentList.setLoadEnable(false);
		commentList.setOnRefreshListener(this);
		commentList.selfRefreshing();
		initTopicListData();
		topicAdapter.setListener(this);
		commentList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Topic topic = (Topic) topicAdapter.getItem(position - 1);
				Intent intent = new Intent(getActivity(), DiscussActivity.class);
				intent.putExtra("isGood", topic.getIsGood());
				intent.putExtra("topic", topic);
				startActivity(intent);

			}
		});
		allDiscuss.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), TopicActivity.class);
				intent.putExtra("all", "all");
				startActivity(intent);

			}
		});
		return view;
	}

	private void initTopicListData() {
		topicListData.clear();
		topicListData.add(new Topic("1", "mary", null, "今天天气好冷！！", 22, 0,
				"2015-12-14"));
		topicListData.add(new Topic("2", "lily", null, "123242432！！", 72, 0,
				"2015-12-14"));
		topicListData.add(new Topic("3", "tom", null, "今天f天气好冷！！", 22, 0,
				"2015-12-14"));
		topicListData.add(new Topic("4", "jerry", null, "今vf气好冷！！", 22, 0,
				"2015-12-14"));
		topicListData.add(new Topic("5", "mary", null, "今天天v气好冷！！", 22, 0,
				"2015-12-14"));
		topicAdapter.notifyDataSetChanged();
	}

	@Override
	public void chooseGood(Topic t) {
		t.setGoodNum(t.getGoodNum() + 1);
		topicAdapter.notifyDataSetChanged();
	}

	@Override
	public void unchooseGood(Topic t) {
		t.setGoodNum(t.getGoodNum() - 1);
		topicAdapter.notifyDataSetChanged();

	}

	@Override
	public void clickDiscuss(Topic t) {
		Intent intent = new Intent(getActivity(), DiscussActivity.class);
		intent.putExtra("isGood", t.getIsGood());
		intent.putExtra("topic", t);
		startActivity(intent);

	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

				initTopicListData();
				commentList.onRefreshComplete();

			}
		}, 3000);
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		getActivity().getSupportFragmentManager().beginTransaction()
		.remove(bannerFragment).commit();
	}

}
