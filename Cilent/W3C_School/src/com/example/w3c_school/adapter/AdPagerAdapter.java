package com.example.w3c_school.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AdPagerAdapter extends PagerAdapter {

	private List<ImageView> mData;
	
	
	public AdPagerAdapter() {
		super();
	}

	public AdPagerAdapter(List<ImageView> mData) {
		super();
		this.mData = mData;
	}

	@Override
	public int getCount() {
		return this.mData.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(this.mData.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView view = this.mData.get(position);
		container.addView(view);
		return view;
	}

}
