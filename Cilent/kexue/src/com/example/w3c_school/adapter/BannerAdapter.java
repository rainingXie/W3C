package com.example.w3c_school.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class BannerAdapter extends PagerAdapter {

	private List<ImageView> imageViews;

	
	public BannerAdapter() {
		super();
	}

	public BannerAdapter(List<ImageView> imageViews) {
		super();
		this.imageViews = imageViews;
	}

	@Override
	public int getCount() {
		return imageViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(this.imageViews.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		container.addView(this.imageViews.get(position));
		return this.imageViews.get(position);
	}

}
