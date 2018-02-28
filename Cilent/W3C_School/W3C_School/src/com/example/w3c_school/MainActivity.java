package com.example.w3c_school;

import java.util.ArrayList;
import java.util.List;

import com.astuetz.PagerSlidingTabStrip;
import com.example.w3c_school.adapter.AdPagerAdapter;
import com.example.w3c_school.adapter.FragementAdapter;
import com.example.w3c_school.entity.ApkInfo;
import com.example.w3c_school.entity.User;
import com.example.w3c_school.fragment.BookFragment;
import com.example.w3c_school.fragment.TestFragment;
import com.example.w3c_school.fragment.VideoFragment;
import com.example.w3c_school.service.VersionUpdateService;
import com.example.w3c_school.utils.AppManager;
import com.example.w3c_school.utils.ApplicationDIV;
import com.example.w3c_school.utils.Constant;
import com.example.w3c_school.utils.DataManager;
import com.example.w3c_school.utils.FaceConversionUtil;
import com.example.w3c_school.utils.ImageLoaderUitil;
import com.example.w3c_school.utils.MethodUtils;
import com.example.w3c_school.utils.UtilsURL;
import com.example.w3c_school.view.FloatView;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener {

	private static final int REQ_CODE = 1;
	private ViewPager adViewPager;
	private PagerSlidingTabStrip tabHost;
	private AdPagerAdapter viewPagerAdapter;
	private RelativeLayout myInfo;

	private ImageView photo;
	private RelativeLayout save;

	private RelativeLayout updatePwd;

	private ViewPager mainViewPager;
	private List<ImageView> imageData;
	private LinearLayout indicatorContainer;
	private ActionBar actBar;
	private DrawerLayout myDrawerLayout;
	private ActionBarDrawerToggle myActionBarDrawerToggle;

	private CheckBox selfLogin;

	private User user;

	private TextView name;

	private WindowManager windowManager = null;
	private WindowManager.LayoutParams windowManagerParams = null;
	private FloatView floatView = null;
	private DisplayMetrics dm;
	private int sWidth;
	private int sHeight;
	private ApkInfo apkInfo;
	private TextView versionName;
	private long firstTime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		apkInfo = ApplicationDIV.getInstance().getApkInfo();
		AppManager.getAppManager().addActivity(this);
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		user = ApplicationDIV.getInstance().getUser();
		adViewPager = (ViewPager) findViewById(R.id.adViewPager);
		tabHost = (PagerSlidingTabStrip) findViewById(R.id.tabHost);
		mainViewPager = (ViewPager) findViewById(R.id.mainViewPager);
		photo = (ImageView) findViewById(R.id.photo);
		save = (RelativeLayout) findViewById(R.id.save);
		name = (TextView) findViewById(R.id.name);
		myInfo = (RelativeLayout) findViewById(R.id.myInfo);
		updatePwd = (RelativeLayout) findViewById(R.id.updatePwd);
		selfLogin = (CheckBox) findViewById(R.id.auto_login);
		versionName = (TextView) findViewById(R.id.versionName);
		findViewById(R.id.note).setOnClickListener(this);
		findViewById(R.id.version).setOnClickListener(this);

		save.setOnClickListener(this);
		myInfo.setOnClickListener(this);
		updatePwd.setOnClickListener(this);
		imageData = new ArrayList<ImageView>();
		initViewPagerData();
		viewPagerAdapter = new AdPagerAdapter(imageData);
		indicatorContainer = (LinearLayout) findViewById(R.id.indicatorContainer);
		adViewPager.setAdapter(viewPagerAdapter);
		initAdView();
		initTabView();
		initActionBar();
		if (null != user) {
			initLeftView();
		}
		setVersionName();
		createFloatView();
		// 加载表情
		new Thread(new Runnable() {
			@Override
			public void run() {
				FaceConversionUtil.getInstace().getFileText(ApplicationDIV.getInstance());
			}
		}).start();

	}

	/**
	 * 编辑actionBar
	 */
	@SuppressLint("NewApi")
	private void initActionBar() {
		// 获取actionBar
		actBar = getActionBar();
		actBar.show();// 显示样式

		actBar.setDisplayShowHomeEnabled(false);
//
//		actBar.setDisplayUseLogoEnabled(true);// 决定logo显示
//	    actBar.setLogo(R.drawable.logo);

		actBar.setDisplayShowTitleEnabled(true);// 标题开关
		actBar.setTitle("渴  学");

		actBar.setDisplayHomeAsUpEnabled(true);// 返回按钮
		actBar.setDisplayShowCustomEnabled(true);// 自定义开关布局
		myDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
		myActionBarDrawerToggle = new ActionBarDrawerToggle(this,
				myDrawerLayout, R.string.app_name, R.string.hello_world);
		myDrawerLayout.setDrawerListener(myActionBarDrawerToggle);
	}

	/**
	 * 主界面的三大功能区
	 */
	private void initTabView() {
		List<Fragment> fragments = new ArrayList<Fragment>();
		List<String> categories = DataManager.getTotalCategory();
		fragments.add(new BookFragment());
		fragments.add(new TestFragment());
		fragments.add(new VideoFragment());

		FragementAdapter adapter = new FragementAdapter(
				getSupportFragmentManager(), fragments, categories);
		this.mainViewPager.setAdapter(adapter);

		tabHost.setTextSize(40);
		tabHost.setTextColor(Color.BLACK);
		tabHost.setViewPager(mainViewPager);
	}

	/**
	 * 设置个人中心数据
	 */
	private void initLeftView() {
		SharedPreferences sp = getSharedPreferences(Constant.SP_FILE_NAME,
				MODE_PRIVATE);

	//	String nameStr = sp.getString("userName", null);
		name.setText(user.getUserName());

		ImageLoaderUitil.display(user.getUserImg(), photo);

		boolean self_login_flag = sp.getBoolean("self_login", true);
		selfLogin.setChecked(self_login_flag);
		selfLogin.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				SharedPreferences sp = getSharedPreferences(
						Constant.SP_FILE_NAME, MODE_PRIVATE);
				SharedPreferences.Editor editor = sp.edit();
				editor.putBoolean("self_login", isChecked);
				editor.commit();

			}
		});
	}

	/**
	 * 动态滑动广告栏
	 */
	private void initAdView() {
		for (int i = 0; i < imageData.size(); i++) {
			ImageView iView = new ImageView(MainActivity.this);
			if (i == 0) {
				iView.setImageResource(R.drawable.image_indicator_focus);
			} else {
				iView.setImageResource(R.drawable.image_indicator);
			}

			indicatorContainer.addView(iView);
		}

		adViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(final int arg0) {

				for (int i = 0; i < imageData.size(); i++) {
					ImageView indicator = (ImageView) indicatorContainer
							.getChildAt(i);
					if (i == arg0) {
						indicator
								.setImageResource(R.drawable.image_indicator_focus);
					} else {
						indicator.setImageResource(R.drawable.image_indicator);
					}
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
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
		default:
			break;
		}
		return super.onOptionsItemSelected(item)
				|| myActionBarDrawerToggle.onOptionsItemSelected(item);

	}

	// 配置放生变化时
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		myActionBarDrawerToggle.onConfigurationChanged(newConfig);
	}

	/**
	 * 广告图片
	 */
	private void initViewPagerData() {
		
		
		ImageView view = new ImageView(this);
		view.setImageResource(R.drawable.ad1);
		view.setScaleType(ScaleType.FIT_XY);
		imageData.add(view);

		view = new ImageView(this);
		view.setImageResource(R.drawable.w3welcome);
		view.setScaleType(ScaleType.FIT_XY);
		imageData.add(view);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myInfo:
			user = ApplicationDIV.getInstance().getUser();
			if (user == null) {
				startActivityForResult(new Intent(MainActivity.this,
						LoginActivity.class), REQ_CODE);

			} else {
				startActivity(new Intent(MainActivity.this,
						SelfInfoActivity.class));
			}
			break;
		case R.id.updatePwd:
			if (user == null) {
				Toast.makeText(getApplicationContext(), "请先登录！", Toast.LENGTH_SHORT).show();

			} else {
				startActivity(new Intent(MainActivity.this, UpdataPwdActivity.class));
			}
			

			break;
		case R.id.save:
			if (user == null) {
				Toast.makeText(getApplicationContext(), "请先登录！", Toast.LENGTH_SHORT).show();

			} else {
				startActivity(new Intent(MainActivity.this, SaveActivity.class));
			}

			

			break;
		case R.id.note:
			if (user == null) {
				Toast.makeText(getApplicationContext(), "请先登录！", Toast.LENGTH_SHORT).show();

			} else {
				startActivity(new Intent(MainActivity.this, NoteListActivity.class));
			}

			

			break;
		case R.id.version:
			updateVersion();
			break;
		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE && resultCode == RESULT_OK) {
			user = ApplicationDIV.getInstance().getUser();
			initLeftView();
		}
		if (requestCode == REQ_CODE && resultCode == RESULT_FIRST_USER) {
			user = ApplicationDIV.getInstance().getUser();
			initLeftView();
		}

	}

	/**
	 * 版本检测及更新
	 */
	public void updateVersion() {
		int vcode;
		// 将版本号和当前安装版本的编号进行对比
		PackageManager pm = getPackageManager();
		PackageInfo pki = null;
		try {
			pki = pm.getPackageInfo("com.example.w3c_school", 0);
			int curCode = pki.versionCode;// 当前版本编号
			if (null != apkInfo) {
				vcode = apkInfo.getVid();
			} else {
				vcode = curCode;
			}
			if (vcode > curCode) {
				// 版本有更新
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("版本更新").setMessage("检测到新版本，是否立即更新");
				builder.setPositiveButton("是",
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {// which是按钮id

								// 准备下载
								Intent intent = new Intent(
										getApplicationContext(),
										VersionUpdateService.class);
								String url = UtilsURL.ROOT_URL
										+ apkInfo.getUrl();
								intent.putExtra("url", url);
								startService(intent);
								dialog.dismiss();// 消失
							}
						});
				builder.setNegativeButton("否",
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();// 消失
							}
						});
				AlertDialog alertDialog = builder.create();
				alertDialog.setCancelable(false);
				alertDialog.show();

			} else {
				Toast.makeText(getApplicationContext(), "当前为最新版本",
						Toast.LENGTH_SHORT).show();
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置版本
	 */
	public void setVersionName() {
		PackageManager pm = getPackageManager();
		PackageInfo pki = null;
		try {
			pki = pm.getPackageInfo("com.example.w3c_school", 0);
			String curName = pki.versionName;// 当前版本名称
			versionName.setText(curName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 全局浮动按钮
	 */
	private void createFloatView() {

		sWidth = dm.widthPixels;
		sHeight = dm.heightPixels;
		floatView = new FloatView(getApplicationContext());
		floatView.setOnClickListener(new OnClickListener() {

			@SuppressLint("InflateParams")
			@Override
			public void onClick(View v) {
				View contextView = LayoutInflater.from(MainActivity.this)
						.inflate(R.layout.pop_float, null);
				final PopupWindow pop = new PopupWindow(contextView,
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
				LinearLayout share = (LinearLayout) contextView
						.findViewById(R.id.share);
				share.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						startActivity(new Intent(MainActivity.this,
								ShareActivity.class));
						pop.dismiss();
					}
				});

				LinearLayout note = (LinearLayout) contextView
						.findViewById(R.id.note);
				note.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						startActivity(new Intent(MainActivity.this,
								NoteActivity.class));
						pop.dismiss();
					}
				});
				pop.setFocusable(true);
				pop.setOutsideTouchable(true);
				pop.setBackgroundDrawable(new ColorDrawable());
				pop.showAtLocation(v, Gravity.RIGHT, 120, 0);
			}
		});
		floatView.setImageResource(R.drawable.morefloat); // 这里简单的用自带的icon来做演示
		// 获取WindowManager
		windowManager = (WindowManager) getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);
		// 设置LayoutParams(全局变量）相关参数
		windowManagerParams = ApplicationDIV.getInstance().getWindowParams();

		windowManagerParams.type = LayoutParams.TYPE_PHONE; // 设置window type
		windowManagerParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
		// 设置Window flag
		windowManagerParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
				| LayoutParams.FLAG_NOT_FOCUSABLE;
		/*
		 * 注意，flag的值可以为： 下面的flags属性的效果形同“锁定”。 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
		 * LayoutParams.FLAG_NOT_TOUCH_MODAL 不影响后面的事件
		 * LayoutParams.FLAG_NOT_FOCUSABLE 不可聚焦 LayoutParams.FLAG_NOT_TOUCHABLE
		 * 不可触摸
		 */
		// 调整悬浮窗口至左上角，便于调整坐标
		windowManagerParams.gravity = Gravity.LEFT | Gravity.TOP;
		// 以屏幕左上角为原点，设置x、y初始值
		windowManagerParams.x = sWidth - 50;
		windowManagerParams.y = sHeight - 50;
		// 设置悬浮窗口长宽数据
		windowManagerParams.width = LayoutParams.WRAP_CONTENT;
		windowManagerParams.height = LayoutParams.WRAP_CONTENT;
		// 显示myFloatView图像
		windowManager.addView(floatView, windowManagerParams);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// 在程序退出(Activity销毁）时销毁悬浮窗口
		windowManager.removeView(floatView);
		AppManager.getAppManager().finishActivity(MainActivity.class);
	}

	@Override
	protected void onStart() {
		super.onStart();
		user = ApplicationDIV.getInstance().getUser();
		if (null != user) {
			initLeftView();
		}
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
				long runTime = endApp-startApp;
				Toast.makeText(getApplicationContext(),"您一共学习了\n"+ MethodUtils.getTimeFormat(runTime)+"\n继续努力！！", Toast.LENGTH_LONG).show();
				AppManager.getAppManager().finishAllActivity();
			}

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
