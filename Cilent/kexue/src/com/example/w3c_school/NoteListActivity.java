package com.example.w3c_school;

import java.util.ArrayList;
import java.util.List;


import com.example.w3c_school.adapter.NoteAdapter;
import com.example.w3c_school.adapter.NoteAdapter.OnNoteDeleteListener;
import com.example.w3c_school.dao.NoteDao;
import com.example.w3c_school.entity.Note;
import com.example.w3c_school.entity.User;
import com.example.w3c_school.utils.ApplicationDIV;
import com.example.w3c_school.view.AutoListView;
import com.example.w3c_school.view.AutoListView.OnRefreshListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

public class NoteListActivity extends BaseActivity implements
		OnNoteDeleteListener, OnRefreshListener {

	private AutoListView listNote;
	private List<Note> noteList;
	private NoteAdapter adapter;
	private Button add;

	private User user;
	private Handler handler = new Handler();
	private static final int NOTE_REQ = 1;

	private int isAdd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_list);
		listNote = (AutoListView) findViewById(R.id.mylist);
		add = (Button) findViewById(R.id.add);
		user = ApplicationDIV.getInstance().getUser();
		noteList = new ArrayList<Note>();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		adapter = new NoteAdapter(noteList, NoteListActivity.this,
				dm.widthPixels);
		listNote.setLoadEnable(false);
		listNote.setOnRefreshListener(this);
		listNote.setAdapter(adapter);

		adapter.setListener(NoteListActivity.this);
		if (null != user) {
			//getConData();

		}
		isAdd = getIntent().getIntExtra("isAdd", 0);
		if (isAdd != 1) {
			add.setVisibility(View.INVISIBLE);
		}
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NoteListActivity.this,
						NoteActivity.class);
				startActivity(intent);

			}
		});
		listNote.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Note note = (Note) adapter.getItem(arg2 - 1);
				Intent intent = new Intent(NoteListActivity.this,
						NoteActivity.class);
				intent.putExtra("note", note);
				startActivityForResult(intent, NOTE_REQ);
			}
		});

	}

	@Override
	public void OnNoteDelete(Note note) {
		noteList.remove(note);
		adapter.notifyDataSetChanged();
		
		//deleteConData(note);
	}

	
	private void getLocalData(){
		NoteDao noteDao = new NoteDao(getApplicationContext());
		List<Note> notes = noteDao.queryNotes(user);
		if (notes.size()>0&&null!=notes) {
			noteList.clear();
			noteList.addAll(notes);
		}
		
	}
	public void back(View view) {
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == NOTE_REQ && resultCode == RESULT_OK) {
		//	getConData();
		}
	}

	

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

			//	getConData();
				listNote.onRefreshComplete();

			}
		}, 4000);
		
	}

}
