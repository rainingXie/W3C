package com.example.w3c_school.fragment;

import java.util.ArrayList;
import java.util.List;

import com.astuetz.PagerSlidingTabStrip;
import com.example.w3c_school.R;
import com.example.w3c_school.adapter.VCFragmentAdapter;
import com.example.w3c_school.fragment.fragment.ModelFragment;
import com.example.w3c_school.fragment.fragment.VideoTypeFragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class VideoFragment extends Fragment{

	private PagerSlidingTabStrip topTabStrip;
	private ViewPager viewPager;
	private List<Fragment> fragments;
	private VCFragmentAdapter adapter;
	private List<String> categorys;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fragments = new ArrayList<Fragment>();
		categorys = new ArrayList<String>();
		adapter = new VCFragmentAdapter(
				getChildFragmentManager(), 
				fragments, categorys);
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_vc, null);
		topTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.topTabStrip);
		viewPager = (ViewPager) view.findViewById(R.id.vc_viewPager);
		initData();
		return view;
	}

	
	
	@SuppressLint("ResourceAsColor")
	private void initData(){
		fragments.clear();
		for(int i = 0 ; i < 3;i++){
			Fragment fragment = new VideoTypeFragment();
			categorys.add("Ð¡±êÌâ"+i);
			Bundle bundle = new Bundle();
			bundle.putCharSequence("title", "title"+i);
			fragment.setArguments(bundle);
			fragments.add(fragment);
		}
		viewPager.setAdapter(adapter);
		topTabStrip.setShouldExpand(true);
		topTabStrip.setViewPager(viewPager);
		
	}
	
}
