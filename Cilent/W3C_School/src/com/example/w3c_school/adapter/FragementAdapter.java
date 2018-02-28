package com.example.w3c_school.adapter;

import java.util.List;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragementAdapter extends FragmentPagerAdapter{

	
	private List<String> tCategorys;
	private List<Fragment> fragments;
	
	public FragementAdapter(FragmentManager fm,List<Fragment> fragments,List<String> tCategorys) {
		super(fm);
		this.fragments = fragments;
		this.tCategorys = tCategorys;
	}

	@Override
	public Fragment getItem(int arg0) {
		return this.fragments.get(arg0);
	}

	@Override
	public int getCount() {
		return this.fragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return this.tCategorys.get(position).toString();
	}

	
}
