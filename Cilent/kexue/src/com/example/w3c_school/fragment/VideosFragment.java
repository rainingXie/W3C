package com.example.w3c_school.fragment;

import java.util.ArrayList;
import java.util.List;

import com.astuetz.PagerSlidingTabStrip;
import com.example.w3c_school.R;
import com.example.w3c_school.VideoActivity;
import com.example.w3c_school.adapter.VCFragmentAdapter;
import com.example.w3c_school.adapter.VideoAdapter;
import com.example.w3c_school.adapter.VideoAdapter.DownloadListener;
import com.example.w3c_school.downloadview.AppFile;
import com.example.w3c_school.downloadview.AppListAdapter;
import com.example.w3c_school.downloadview.DownloadManager;
import com.example.w3c_school.entity.Video;
import com.example.w3c_school.fragment.fragment.ModelFragment;
import com.example.w3c_school.fragment.fragment.VideoTypeFragment;
import com.example.w3c_school.service.DownLoadVideoService;
import com.example.w3c_school.view.MyGridView;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VideosFragment extends Fragment implements DownloadListener {

	private List<String> categorys;
	private List<List<Video>> allData;
	private LinearLayout typeContainer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		categorys = new ArrayList<String>();
		allData = new ArrayList<List<Video>>();
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_video_gridview, null);
		typeContainer = (LinearLayout) view
				.findViewById(R.id.video_gridview_list);

		initData();
		initView();
		return view;
	}

	private void initView() {
		for (int i = 0; i < categorys.size(); i++) {
			View item_grid = LayoutInflater.from(getActivity()).inflate(
					R.layout.video_type_layout, null);
			TextView tagView = (TextView) item_grid.findViewById(R.id.type_tag);
			tagView.setText(categorys.get(i));
			final MyGridView gridView = (MyGridView) item_grid
					.findViewById(R.id.typeGrid);
			final List<Video> mDataList = new ArrayList<Video>();
			mDataList.addAll(allData.get(i));
			typeContainer.addView(item_grid);
			VideoAdapter adapter = new VideoAdapter(mDataList, getActivity());
			gridView.setAdapter(adapter);
			adapter.setListener(this);
			gridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
//					Video item = (Video) gridView.getAdapter().getItem(arg2);
//					Intent intent = new Intent(getActivity(),
//							VideoActivity.class);
//					intent.putExtra("item", item);
//					startActivity(intent);

				}
			});
		}
	}

	@SuppressLint("ResourceAsColor")
	private void initData() {
		categorys.clear();
		categorys.add("标题1");
		categorys.add("标题2");
		categorys.add("标题3");
		categorys.add("标题4");

		allData.clear();

		List<Video> list1 = new ArrayList<Video>();
		list1.add(new Video(1,"标题1视频1", "1.2M", "9:09", "http://cdn2.image.apk.gfan.com/asdf/PImages/2014/6/9/148567f296c959-7c23-445c-80e7-7d024d60204c_icon.png"));
		list1.add(new Video(2,"标题1视频2", "1.2M", "9:09", "http://cdn2.image.apk.gfan.com/asdf/PImages/2014/6/9/148567f296c959-7c23-445c-80e7-7d024d60204c_icon.png"));
		list1.add(new Video(3,"标题1视频3", "1.2M", "9:09", null));
		list1.add(new Video(4,"标题1视频4", "1.2M", "9:09", null));
		allData.add(list1);

		list1 = new ArrayList<Video>();
		list1.add(new Video(5,"标题2视频1", "1.2M", "9:09", null));
		list1.add(new Video(6,"标题2视频2", "1.2M", "9:09", null));
		list1.add(new Video(7,"标题2视频3", "1.2M", "9:09", null));
		list1.add(new Video(8,"标题2视频4", "1.2M", "9:09", null));
		allData.add(list1);

		list1 = new ArrayList<Video>();
		list1.add(new Video(9,"标题3视频1", "1.2M", "9:09", null));
		list1.add(new Video(10,"标题3视频2", "1.2M", "9:09", null));
		list1.add(new Video(11,"标题3视频3", "1.2M", "9:09", null));
		list1.add(new Video(12,"标题3视频4", "1.2M", "9:09", null));
		allData.add(list1);

		list1 = new ArrayList<Video>();
		list1.add(new Video(13,"标题4视频1", "1.2M", "9:09", null));
		list1.add(new Video(14,"标题4视频2", "1.2M", "9:09", null));
		list1.add(new Video(15,"标题4视频3", "1.2M", "9:09", null));
		list1.add(new Video(16,"标题4视频4", "1.2M", "9:09", null));
		allData.add(list1);

	}

	@Override
	public void downloadVideo(Video video) {
		Intent intent = new Intent(getActivity(), DownLoadVideoService.class);
		intent.putExtra("video", video);
		getActivity().startService(intent);
	}

}
