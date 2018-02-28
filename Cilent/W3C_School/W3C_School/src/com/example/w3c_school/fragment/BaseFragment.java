package com.example.w3c_school.fragment;

import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {

	protected boolean isVisible;
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser){
			isVisible = true;
			lazyLoadRefresh();
		}else{
			isVisible = false;
		}
		
	}
	public abstract void lazyLoadRefresh();

	
}
