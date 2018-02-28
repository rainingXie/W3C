package com.example.w3c_school.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.w3c_school.db.DBConstant.NoteTable;
import com.example.w3c_school.db.DBHelper;
import com.example.w3c_school.db.SQLIDUS;
import com.example.w3c_school.db.SqlStatment;
import com.example.w3c_school.entity.ContentItem;
import com.example.w3c_school.entity.Note;
import com.example.w3c_school.entity.User;

public class NoteDao {

	private DBHelper dbHelper;

	public NoteDao(Context context) {
		super();
		dbHelper = new DBHelper(context);
	}

	public void addNote(Note note, User user) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		List<String> strs = new ArrayList<String>();
		strs.add(NoteTable.COL_ID);
		strs.add(NoteTable.COL_USER);
		strs.add(NoteTable.COL_TITLE);
		strs.add(NoteTable.COL_CONTENT_ID);
		strs.add(NoteTable.COL_CONETENT);
		strs.add(NoteTable.COL_DATE);
		database.execSQL(SQLIDUS.doInsert(strs, NoteTable.TABLE_NAME),
				new Object[] { note.getId(), user.getUserNo(), note.getName(),
						note.getParentId(), note.getContent(), note.getDate() });
		database.close();
	}

	public void addNotes(List<Note> notes, User user) {
		for (Note n : notes) {
			addNote(n, user);
		}
	}

	/**
	 * 查询当前用户所有的笔记
	 * 
	 * @param user
	 * @return
	 */
	public List<Note> queryNotes(User user) {
		if (null != user) {
			SQLiteDatabase database = dbHelper.getReadableDatabase();
			Cursor cursor = database.rawQuery(SqlStatment.queryNotesSql(user),
					null);
			List<Note> notes = new ArrayList<Note>();
			while (cursor.moveToNext()) {
				Note note = new Note();
				note.setParentId(cursor.getString(cursor
						.getColumnIndex(NoteTable.COL_CONTENT_ID)));
				note.setId(cursor.getString(cursor
						.getColumnIndex(NoteTable.COL_ID)));
				note.setContent(cursor.getString(cursor
						.getColumnIndex(NoteTable.COL_CONETENT)));
				note.setName(cursor.getString(cursor
						.getColumnIndex(NoteTable.COL_TITLE)));
				note.setUserNo(cursor.getString(cursor
						.getColumnIndex(NoteTable.COL_USER)));
				note.setDate(cursor.getString(cursor
						.getColumnIndex(NoteTable.COL_DATE)));
				notes.add(note);
			}
			cursor.close();
			return notes;
		} else {
			return null;
		}

	}

	/**
	 * 查询当前用户所有的笔记
	 * 
	 * @param user
	 * @return
	 */
	public List<Note> queryNotes(ContentItem item, User user) {
		if (null != user) {
			SQLiteDatabase database = dbHelper.getReadableDatabase();
			Cursor cursor = database.rawQuery(
					SqlStatment.queryNotesSql(item, user), null);
			List<Note> notes = new ArrayList<Note>();
			while (cursor.moveToNext()) {
				Note note = new Note();
				note.setParentId(cursor.getString(cursor
						.getColumnIndex(NoteTable.COL_CONTENT_ID)));
				note.setId(cursor.getString(cursor
						.getColumnIndex(NoteTable.COL_ID)));
				note.setContent(cursor.getString(cursor
						.getColumnIndex(NoteTable.COL_CONETENT)));
				note.setName(cursor.getString(cursor
						.getColumnIndex(NoteTable.COL_TITLE)));
				note.setUserNo(cursor.getString(cursor
						.getColumnIndex(NoteTable.COL_USER)));
				note.setDate(cursor.getString(cursor
						.getColumnIndex(NoteTable.COL_DATE)));
				notes.add(note);
			}
			cursor.close();
			return notes;
		} else {
			return null;
		}

	}

	/**
	 * 查询用户的该条笔记
	 * 
	 * @param user
	 * @return
	 */
	public List<Note> queryNotesByName(Note n, User user) {
		if (null != user) {
			SQLiteDatabase database = dbHelper.getReadableDatabase();
			Cursor cursor = database.rawQuery(
					SqlStatment.queryNotesSqlByName(n, user), null);
			List<Note> notes = new ArrayList<Note>();
			while (cursor.moveToNext()) {
				Note note = new Note();
				note.setParentId(cursor.getString(cursor
						.getColumnIndex(NoteTable.COL_CONTENT_ID)));
				note.setId(cursor.getString(cursor
						.getColumnIndex(NoteTable.COL_ID)));
				note.setContent(cursor.getString(cursor
						.getColumnIndex(NoteTable.COL_CONETENT)));
				note.setName(cursor.getString(cursor
						.getColumnIndex(NoteTable.COL_TITLE)));
				note.setUserNo(cursor.getString(cursor
						.getColumnIndex(NoteTable.COL_USER)));
				note.setDate(cursor.getString(cursor
						.getColumnIndex(NoteTable.COL_DATE)));
				notes.add(note);
			}
			cursor.close();
			return notes;
		} else {
			return null;
		}

	}

	/**
	 * 取消该用户的收藏
	 * 
	 * @param item
	 * @param user
	 */
	public void deleteNote(Note note, User user) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		database.execSQL(SqlStatment.deleteNoteSql(note, user), null);
		database.close();
	}

	/**
	 * 更新笔记
	 * 
	 * @param note
	 * @param user
	 */
	public void updateNote(Note note, User user) {
		List<Note> notes = queryNotesByName(note, user);
		if (null != notes && notes.size() > 0) {
			deleteNote(note, user);
		}
		addNote(note, user);
	}
}
