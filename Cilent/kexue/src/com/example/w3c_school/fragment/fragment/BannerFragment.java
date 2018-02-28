package com.example.w3c_school.fragment.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import java.util.concurrent.TimeUnit;
import com.example.w3c_school.R;
import com.example.w3c_school.adapter.BannerAdapter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class BannerFragment extends Fragment{

	private ViewPager viewPager;
	private LinearLayout dots;
	private TextView label;

	private List<ImageView> imageViews;
	private BannerAdapter adapter;
	private List<View> dotViews;

	private int curSelItem = 0;

	private ScheduledExecutorService scheduledExecutorService;
	int[] imgs = new int[] { R.drawable.home1, R.drawable.home,
			R.drawable.home1, R.drawable.home };
	
	String[] labels = new String[]{"label1","label2","label3","label4"};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		imageViews = new ArrayList<ImageView>();
		dotViews = new ArrayList<View>();
		adapter = new BannerAdapter(imageViews);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.banner_fragment, null);
		viewPager = (ViewPager)view.findViewById(R.id.bannerViewPager);
		dots = (LinearLayout)view.findViewById(R.id.dots);
		label = (TextView) view.findViewById(R.id.label);
		viewPager.setAdapter(adapter);
		initDotViews(imgs.length);
		initPagerData();
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 6, 6,
				TimeUnit.SECONDS);
		return view;
	}

	private class ScrollTask implements Runnable {

		@Override
		public void run() {
			synchronized (viewPager) {
				curSelItem = (curSelItem + 1) % imageViews.size();
				bannerHandler.obtainMessage().sendToTarget();
			}
		}

	}

	private Handler bannerHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			viewPager.setCurrentItem(curSelItem);
		}

	};

	@SuppressWarnings("deprecation")
	private void initDotViews(int num) {
		if (num > 1) {
			viewPager.setOnPageChangeListener(new OnPageChangeListener() {

				private int oldPosition = 0;

				@Override
				public void onPageSelected(int arg0) {
					// TODO Auto-generated method stub
					curSelItem = arg0;
					label.setText(labels[arg0]);
					dotViews.get(oldPosition).setBackgroundResource(
							R.drawable.image_indicator);
					dotViews.get(arg0).setBackgroundResource(
							R.drawable.image_indicator_focus);
					oldPosition = arg0;

				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub

				}
			});
		}

		imageViews.clear();
		dots.removeAllViews();
		dotViews.clear();
		for (int i = 0; i < num; i++) {
			ImageView iView = new ImageView(getActivity());
			dots.addView(iView);
			if (i == 0) {
				label.setText(labels[0]);
				iView.setBackgroundResource(R.drawable.image_indicator_focus);
			} else {
				iView.setBackgroundResource(R.drawable.image_indicator);
			}

			dotViews.add(iView);

			ImageView imageView = new ImageView(getActivity());
			imageView.setScaleType(ScaleType.FIT_XY);
			imageViews.add(imageView);
		}
		if (imageViews.size() > 0 && null != imageViews) {
			for (int i = 0; i < num; i++) {
				imageViews.get(i).setOnClickListener(
						new BannerOnClickListener(i));
			}
		}
		adapter.notifyDataSetChanged();

	}

	class BannerOnClickListener implements OnClickListener {

		int position;

		BannerOnClickListener(int pos) {
			this.position = pos;
		}

		@Override
		public void onClick(View v) {
			int pos = position % imageViews.size();
			// Toast.makeText(getApplicationContext(), pos, Toast.LENGTH_SHORT)
			// .show();
		}

	}
	@Override
	public void onPause() {
		scheduledExecutorService.shutdown();
		super.onPause();
	}

	private void initPagerData() {
		for (int i = 0; i < imgs.length; i++) {
			imageViews.get(i).setImageResource(imgs[i]);
		}
	}
}
