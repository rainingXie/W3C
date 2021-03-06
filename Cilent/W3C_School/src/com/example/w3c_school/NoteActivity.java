package com.example.w3c_school;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.example.w3c_school.db.DBHelper;
import com.example.w3c_school.entity.Note;
import com.example.w3c_school.entity.User;
import com.example.w3c_school.utils.ApplicationDIV;
import com.example.w3c_school.utils.StringPostRequest;
import com.example.w3c_school.utils.UtilsURL;
import com.j256.ormlite.dao.Dao;

public class NoteActivity extends BaseActivity {

	private Button add;
	private EditText tag;
	private EditText tagContent;

	private User user;
	private Note noteIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_view);
		add = (Button) findViewById(R.id.submit);
		tag = (EditText) findViewById(R.id.tag);
		tagContent = (EditText) findViewById(R.id.tagContent);
		user = ApplicationDIV.getInstance().getUser();
		noteIntent = (Note) getIntent().getSerializableExtra("note");
		setResult(RESULT_OK);

		iniView();
		doIt();
	}

	private void iniView() {
		if (null != noteIntent) {
			tag.setText(noteIntent.getName());
			tagContent.setText(noteIntent.getContent());
		} else {
			tag.setText("");
			tagContent.setText("");
		}
	}

	private void doIt() {
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != user) {
					if (null != noteIntent) {
						deleteConData(noteIntent);
						Dao<Note, String> noteDao = DBHelper.getInstance(
								NoteActivity.this).getNoteDao();

						try {
							noteDao.deleteById(noteIntent.getName());
						} catch (SQLException e) {
							e.printStackTrace();
						}

						insertData();
					} else {
						insertData();
					}
				} else {
					Toast.makeText(NoteActivity.this, "请先登录",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	/**
	 * 插入本地数据库
	 */
	@SuppressLint("SimpleDateFormat")
	private void insertData() {
		String tagText = tag.getText().toString();
		String tagContentTtext = tagContent.getText().toString();
		if (tagText == null && tagContentTtext == null) {
			return;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);

		Dao<Note, String> noteDao = DBHelper.getInstance(this).getNoteDao();
		Note n = new Note();
		if (null != tagText) {
			n.setName(tagText);
		}
		if (null != tagContentTtext) {
			n.setContent(tagContentTtext);
		}
		n.setDate(str);
		n.setUserNo(user.getUserNo());
		try {
			if (null != noteIntent) {
				if (null != noteDao.queryForId(noteIntent.getName())) {
					noteDao.deleteById(noteIntent.getName());
				}
			}

			noteDao.create(n);

			setConData(n);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param note
	 *            将数据插入到服务器
	 */
	private void setConData(Note note) {
		StringPostRequest request = new StringPostRequest(UtilsURL.DATA_URL,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						Toast.makeText(NoteActivity.this, "成功更新到数据库",
								Toast.LENGTH_SHORT).show();
						finish();
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(NoteActivity.this, "服务器连接失败，请重试",
								Toast.LENGTH_SHORT).show();
					}
				});

		request.putParams("name", note.getName());
		request.putParams("date", note.getDate());
		request.putParams("content", note.getContent());
		request.putParams("userNo", note.getUserNo());
		request.putParams("find", "notesInsert");// 插入数据
		ApplicationDIV.getInstance().getRequestQueue().add(request);

	}

	/**
	 * @param note
	 *            从服务器删除该条note
	 */
	private void deleteConData(Note note) {
		StringPostRequest request = new StringPostRequest(UtilsURL.DATA_URL,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						Log.i("delete", "delete");
						// Toast.makeText(NoteActivity.this, "删除成功",
						// Toast.LENGTH_SHORT).show();
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(NoteActivity.this, "服务器连接失败，请重试",
								Toast.LENGTH_SHORT).show();

					}
				});
		request.putParams("find", "notesDelete");
		request.putParams("id", note.getId() + "");
		ApplicationDIV.getInstance().getRequestQueue().add(request);
	}

	public void back(View v) {
		finish();
	}

}
