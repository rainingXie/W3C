package com.example.w3c_school;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.example.w3c_school.adapter.NoteAdapter;
import com.example.w3c_school.adapter.NoteAdapter.OnNoteDeleteListener;
import com.example.w3c_school.db.DBHelper;
import com.example.w3c_school.entity.Note;
import com.example.w3c_school.entity.User;
import com.example.w3c_school.utils.ApplicationDIV;
import com.example.w3c_school.utils.StringPostRequest;
import com.example.w3c_school.utils.UtilsURL;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.j256.ormlite.dao.Dao;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class NoteListActivity extends BaseActivity implements
		OnNoteDeleteListener ,OnRefreshListener2<ListView>{

	private PullToRefreshListView listNote;
	private List<Note> noteList;
	private NoteAdapter adapter;

//	private ListView listNote;
	private User user;
	private Handler handler = new Handler();
	private static final int NOTE_REQ = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_list);
		listNote = (PullToRefreshListView) findViewById(R.id.mylist);
		user = ApplicationDIV.getInstance().getUser();
		noteList = new ArrayList<Note>();
		adapter = new NoteAdapter(noteList, NoteListActivity.this);
		listNote.setMode(Mode.BOTH);
		listNote.setOnRefreshListener(this);
		listNote.setAdapter(adapter);

		adapter.setListener(NoteListActivity.this);
		if (null != user) {
			getConData();

		}
		listNote.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Note note = (Note) adapter.getItem(arg2-1);
				Intent intent = new Intent(NoteListActivity.this,
						NoteActivity.class);
				intent.putExtra("note", note);
				startActivityForResult(intent, NOTE_REQ);
			}
		});

	}

	/**
	 * 获得本地数据库中的notes
	 */
	private void getLocalData() {
		noteList.clear();
		Dao<Note, String> noteDao = DBHelper.getInstance(this).getNoteDao();
		try {
			List<Note> localNote = noteDao.queryForAll();
			if (null != localNote && localNote.size() > 0) {

				for (Note n : localNote) {
					if (user.getUserNo().equals(n.getUserNo())) {
						noteList.add(n);
					}
				}
				adapter.notifyDataSetChanged();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从服务器获得notes
	 */
	private void getConData() {
		getLocalData();
		
		StringPostRequest request = new StringPostRequest(UtilsURL.DATA_URL,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						listNote.onRefreshComplete();
						Gson gson = new Gson();
						List<Note> notes = gson.fromJson(arg0,
								new TypeToken<ArrayList<Note>>() {
								}.getType());
						if (null != notes && notes.size() > 0) {
							noteList.clear();
							noteList.addAll(notes);
							adapter.notifyDataSetChanged();
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						listNote.onRefreshComplete();
						getLocalData();
						Toast.makeText(NoteListActivity.this, "服务器连接失败，请重试",
								Toast.LENGTH_SHORT).show();

					}
				});
		request.putParams("find", "notes");
		request.putParams("userNo", user.getUserNo());
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
						Toast.makeText(NoteListActivity.this, "删除成功",
								Toast.LENGTH_SHORT).show();
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						getLocalData();
						Toast.makeText(NoteListActivity.this, "服务器连接失败，请重试",
								Toast.LENGTH_SHORT).show();

					}
				});
		request.putParams("find", "notesDelete");
		request.putParams("id", note.getId() + "");
		ApplicationDIV.getInstance().getRequestQueue().add(request);
	}

	@Override
	public void OnNoteDelete(Note note) {
		noteList.remove(note);
		adapter.notifyDataSetChanged();
		Dao<Note, String> noteDao = DBHelper.getInstance(this).getNoteDao();
		try {
			noteDao.deleteById(note.getName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		deleteConData(note);
	}

	public void back(View view) {
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == NOTE_REQ && resultCode == RESULT_OK) {
			getConData();
		}
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		handler .postDelayed(new Runnable() {

			@Override
			public void run() {
				
				getConData();
				listNote.onRefreshComplete();

			}
		}, 4000);
		
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		listNote.onRefreshComplete();
		
	}

}
