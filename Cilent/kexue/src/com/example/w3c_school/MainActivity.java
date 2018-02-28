package com.example.w3c_school;

import java.util.ArrayList;
import java.util.List;

import com.example.w3c_school.adapter.FragmentAdapter;
import com.example.w3c_school.fragment.HomeFragment;
import com.example.w3c_school.fragment.InfoFragment;
import com.example.w3c_school.fragment.ECFragment;
import com.example.w3c_school.fragment.VideoFragment;
import com.example.w3c_school.fragment.VideosFragment;
import com.example.w3c_school.utils.AppManager;
import com.example.w3c_school.utils.ApplicationDIV;
import com.example.w3c_school.utils.Constant;
import com.example.w3c_school.utils.FaceConversionUtil;
import com.example.w3c_school.utils.MethodUtils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SlidingDrawer;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

public class MainActivity extends FragmentActivity {

	private RadioGroup bottomContainer;
	private RadioButton home, course, video, info;
	private ViewPager mainContainer;
	private TextView title;
	private List<Fragment> fragmentList;
	private FragmentAdapter adapter;
	private long firstTime;
	private ImageView imbg;
	@SuppressWarnings("deprecation")
	private SlidingDrawer mDrawer;
	private TextView note, share;
	protected boolean flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		AppManager.getAppManager().addActivity(this);
		fragmentList = new ArrayList<Fragment>();
		adapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList);
		initView();
		initData();
		siliding();
		// 加载表情
		new Thread(new Runnable() {
			@Override
			public void run() {
				FaceConversionUtil.getInstace().getFileText(
						ApplicationDIV.getInstance());
			}
		}).start();
	}

	private void initView() {
		title = (TextView) findViewById(R.id.title);
		bottomContainer = (RadioGroup) findViewById(R.id.bottomContainer);
		mainContainer = (ViewPager) findViewById(R.id.mainContainer);
		home = (RadioButton) findViewById(R.id.home);
		course = (RadioButton) findViewById(R.id.course);
		video = (RadioButton) findViewById(R.id.video);
		info = (RadioButton) findViewById(R.id.info);
	}

	private void initData() {
		fragmentList.add(new HomeFragment());
		fragmentList.add(new ECFragment());
		fragmentList.add(new VideosFragment());
		fragmentList.add(new InfoFragment());
		mainContainer.setAdapter(adapter);
		bottomContainer
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
						case R.id.home:
							mainContainer.setCurrentItem(0);
							title.setText(home.getText());
							break;
						case R.id.course:
							mainContainer.setCurrentItem(1);
							title.setText(course.getText());
							break;
						case R.id.video:
							mainContainer.setCurrentItem(2);
							title.setText(video.getText());
							break;
						case R.id.info:
							mainContainer.setCurrentItem(3);
							title.setText(info.getText());
							break;
						}

					}
				});
		mainContainer.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				switch (arg0) {
				case 0:
					bottomContainer.check(R.id.home);
					break;
				case 1:
					bottomContainer.check(R.id.course);
					break;
				case 2:
					bottomContainer.check(R.id.video);
					break;
				case 3:
					bottomContainer.check(R.id.info);
					break;
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			long secondTime = System.currentTimeMillis();
			if ((secondTime - firstTime) > 1000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				firstTime = System.currentTimeMillis();
			} else {
				SharedPreferences sp = getSharedPreferences(
						Constant.SP_FILE_NAME, MODE_PRIVATE);
				long endApp = System.currentTimeMillis();
				long startApp = sp.getLong("startAppTime", 0);
				long runTime = endApp - startApp;
				Toast.makeText(
						getApplicationContext(),
						"您一共学习了\n" + MethodUtils.getTimeFormat(runTime)
								+ "\n继续努力！！", Toast.LENGTH_LONG).show();
				AppManager.getAppManager().finishAllActivity();
			}

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@SuppressWarnings("deprecation")
	private void siliding() {
		imbg = (ImageView) findViewById(R.id.handle);
		mDrawer = (SlidingDrawer) findViewById(R.id.slidingdrawer);
		note = (TextView) findViewById(R.id.note);
		share = (TextView) findViewById(R.id.share);
		note.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						NoteActivity.class);
				startActivity(intent);

			}
		});

		share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						ShareActivity.class);
				startActivity(intent);

			}
		});

		mDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				flag = true;
				imbg.setImageResource(R.drawable.browse_back_off);
			}

		});

		mDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
			@Override
			public void onDrawerClosed() {
				flag = false;
				imbg.setImageResource(R.drawable.browse_back_on);
			}

		});

		mDrawer.setOnDrawerScrollListener(new SlidingDrawer.OnDrawerScrollListener() {
			@Override
			public void onScrollEnded() {
				// tv.setText("结束拖动");
			}

			@Override
			public void onScrollStarted() {
				// tv.setText("开始拖动");
			}

		});

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		AppManager.getAppManager().finishActivity(MainActivity.class);
	}
}
