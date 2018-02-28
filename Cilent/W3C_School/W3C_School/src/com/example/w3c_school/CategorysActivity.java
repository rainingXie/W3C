package com.example.w3c_school;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.example.w3c_school.adapter.CategorysFragementAdapter;
import com.example.w3c_school.entity.Book;
import com.example.w3c_school.entity.Category;
import com.example.w3c_school.fragment.CategorysFragment;
import com.example.w3c_school.utils.AppManager;
import com.example.w3c_school.utils.ApplicationDIV;
import com.example.w3c_school.utils.StringPostRequest;
import com.example.w3c_school.utils.UtilsURL;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.LayoutParams;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class CategorysActivity extends FragmentActivity {

	private RadioGroup categorysContainer;
	private ViewPager contentContainer;
	private Book book;
	private TextView title;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		AppManager.getAppManager().addActivity(this);
		setContentView(R.layout.activity_categorys);
		categorysContainer = (RadioGroup) findViewById(R.id.categoryContainer);
		contentContainer = (ViewPager) findViewById(R.id.contentContainer);
		title = (TextView) findViewById(R.id.title_name);
		book = (Book) getIntent().getSerializableExtra("book");
		title.setText(book.getFunctionName());
		getConData();
	}
	

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@SuppressLint({ "HandlerLeak", "InflateParams" })
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 100:
				@SuppressWarnings("unchecked")
				List<Category> cs = (ArrayList<Category>) msg.obj;
				if (cs.size() > 0 && null != cs) {
					for (int i = 0; i < cs.size(); i++) {
						Category cy = cs.get(i);
						RadioButton button = (RadioButton) LayoutInflater.from(
								CategorysActivity.this).inflate(
								R.layout.categorys_radio, null);
						LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
						button.setLayoutParams(lp);
						button.setText(cy.getShceduleName());
						button.setTag(cy);
						button.setId(i);
						if (i == 0) {
							button.setChecked(true);
						}
						categorysContainer.addView(button);
					}
					List<Fragment> fragments = new ArrayList<Fragment>();
					for (int i = 0; i < cs.size(); i++) {
						Fragment f = new CategorysFragment();
						Bundle bundle = new Bundle();
						bundle.putInt("categoryId", cs.get(i).getShceduleId());
						bundle.putString("tag", book.getChildrenName());
						if(null!=bundle){
							f.setArguments(bundle);
							fragments.add(f);
						}
						
						
					}
					CategorysFragementAdapter adapter = new CategorysFragementAdapter(getSupportFragmentManager(), fragments);
					contentContainer.setAdapter(adapter);
				}
				initEvent();
				break;

			default:
				break;
			}
		}

	};
	private void initEvent() {
		categorysContainer.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				contentContainer.setCurrentItem(checkedId);

			}
		});

		contentContainer.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				categorysContainer.check(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		
	}
	private void getConData() {
		// 读取第二层数据
		final List<Category> firstList = new ArrayList<Category>();
		StringPostRequest request = new StringPostRequest(UtilsURL.DATA_URL,
				new Listener<String>() {
					@Override
					public void onResponse(String arg0) {
						book = (Book) getIntent().getSerializableExtra("book");
						try {
							JSONArray jsonArray = new JSONArray(arg0);
							if (null != jsonArray && jsonArray.length() > 0) {
								firstList.clear();
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject jsonObject = (JSONObject) jsonArray
											.get(i);
									int parentId = jsonObject
											.getInt("parentId");
									String shceduleName = jsonObject
											.getString("shceduleName");
									int shceduleId = jsonObject
											.getInt("shceduleId");
									final Category c = new Category(parentId,
											shceduleName, shceduleId, book
													.getFunctionName());
									// Dao<Category, Integer> categoryDao =
									// DBHelper
									// .getInstance(CategoryActivity.this)
									// .getCategoryDao();
									// try {
									// if (categoryDao.queryForMatching(c)
									// .size() > 0
									// && null != categoryDao
									// .queryForMatching(c)) {
									// categoryDao.delete(c);
									// }
									// categoryDao.create(c);
									// } catch (SQLException e) {
									// e.printStackTrace();
									// }
									firstList.add(c);

								}

							}
							Message msg = handler.obtainMessage();
							msg.obj = firstList;
							msg.what = 100;
							handler.sendMessage(msg);
						} catch (JSONException e1) {
							e1.printStackTrace();
						}

					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(getApplicationContext(), "连接数据失败，请重试",
								Toast.LENGTH_SHORT).show();
					}
				});
		book = (Book) getIntent().getSerializableExtra("book");
		request.putParams("find", "categorys");
		request.putParams("parentId", book.getFunctionId() + "");
		ApplicationDIV.getInstance().getRequestQueue().add(request);
	}

	
	
	public void back(View v) {
		finish();
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		AppManager.getAppManager().finishActivity(CategorysActivity.this);
		
	}
}
