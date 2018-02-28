package com.example.w3c_school.adapter;

import java.util.List;



import com.example.w3c_school.entity.Book;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CategorysFragementAdapter extends FragmentPagerAdapter{

	
	
	private List<Fragment> fragments;
	
	public CategorysFragementAdapter(FragmentManager fm,List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int arg0) {
		return this.fragments.get(arg0);
	}

	@Override
	public int getCount() {
		return this.fragments.size();
	}

	
}
