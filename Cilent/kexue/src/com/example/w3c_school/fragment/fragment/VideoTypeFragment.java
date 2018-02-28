package com.example.w3c_school.fragment.fragment;



import java.util.ArrayList;
import java.util.List;

import com.example.w3c_school.R;
import com.example.w3c_school.downloadview.AppFile;
import com.example.w3c_school.downloadview.AppListAdapter;
import com.example.w3c_school.downloadview.DownloadManager;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class VideoTypeFragment extends Fragment{

	private ListView listView;
	
	public NotificationManager mNotificationManager;

	private List<AppFile> appList = new ArrayList<AppFile>();
	private String tag;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tag = getArguments().getString("title");

		
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_refresh_list, null);
		listView = (ListView) view
				.findViewById(R.id.refreshList);
		initData();
		AppListAdapter adapter = new AppListAdapter(getActivity(), appList);
	//	adapter.setListView(listView);
		listView.setAdapter(adapter);
//		listView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				Intent intent = new Intent(getActivity(),VideoActivity.class);
//				Video video = (Video) adapter.getItem(position);
//				intent.putExtra("video", video);
//				startActivity(intent);
//				
//			}
//		});
		return view;

	}
	private void initData(){
		for(int i =0; i<20; i++){
			AppFile app = new AppFile();
			app.name = "视频--" + (i+1);
			app.size = 100;
			app.id = i;
			app.tag = tag+i;
			app.downloadState = DownloadManager.DOWNLOAD_STATE_NORMAL;
			app.downloadSize = 0;
			appList.add(app);
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	//	DownloadManager.getInstance().stopAllDownloadTask();
	}
	

//	@Override
//	public void downloadVideo(Video video) {
//		// 准备下载
//		Intent intent = new Intent(
//				getActivity(),
//				DownLoadVideoService.class);
//		intent.putExtra("video", video);
//		getActivity().startService(intent);
//		
//	}
	
}
