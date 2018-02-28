package com.example.w3c_school;

import java.util.List;

import com.example.w3c_school.dao.SaveDao;
import com.example.w3c_school.entity.ContentItem;
import com.example.w3c_school.entity.User;
import com.example.w3c_school.utils.ApplicationDIV;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class CourseContentActivity extends BaseActivity implements OnClickListener{

	private TextView title;
	private WebView contentText;
	private WebView codeShow;
	private TextView testCode;
	private ProgressDialog progressDialog;

	private boolean flagText = false;
	private boolean flagShow = false;
	private CheckBox save;
	

	private ContentItem item;

	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_content);
		
		title = (TextView) findViewById(R.id.titleName);
		contentText = (WebView) findViewById(R.id.contentText);
		codeShow = (WebView) findViewById(R.id.codeShow);
		testCode = (TextView) findViewById(R.id.testCode);
		save = (CheckBox) findViewById(R.id.save);
		item = (ContentItem) getIntent().getSerializableExtra("item");
		user = ApplicationDIV.getInstance().getUser();
		findViewById(R.id.more).setOnClickListener(this);;
		
		title.setText(item.getTitle());
	}
	
	
	private void saveState(){
		save.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (null!=user) {
					SaveDao saveDao = new SaveDao(getApplicationContext());
					if (isChecked) {
						if (saveDao.querySave(item, user)==1) {
							saveDao.deleteSave(item, user);
						}
						saveDao.addSave(item, user);
					}else {
						saveDao.deleteSave(item, user);
					}
				}else {
					Toast.makeText(getApplicationContext(), "ÇëµÇÂ¼", Toast.LENGTH_SHORT).show();
				}
				
				
			}
		});
	}
	
	private void saveTag(){
		SaveDao saveDao = new SaveDao(getApplicationContext());
		int isSave = saveDao.querySave(item, user);
		if (isSave==1) {
			save.setChecked(true);
		}else {
			save.setChecked(false);
		}
	}
	
	
	public void back(View view) {
		finish();
	}


	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.more:
			View moreView = LayoutInflater.from(this).inflate(
					R.layout.pop_more, null);
			final PopupWindow pop = new PopupWindow(moreView,
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			moreView.findViewById(R.id.topic).setOnClickListener(new OnClickListener() {
				
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(CourseContentActivity.this,TopicActivity.class);
					intent.putExtra("parentId", item.getId());
					startActivity(intent);
					
				}
			});
			
			moreView.findViewById(R.id.note).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(CourseContentActivity.this,NoteListActivity.class);
					intent.putExtra("isAdd", 1);
					intent.putExtra("parentId", item.getId());
					startActivity(intent);
					
				}
			});
			pop.setFocusable(true);
			pop.setOutsideTouchable(true);
			pop.setBackgroundDrawable(new ColorDrawable());
			pop.showAsDropDown(v, 0, 0);
			break;

		default:
			break;
		}
		
	}

}
