package com.example.w3c_school.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.w3c_school.db.DBConstant.SaveTable;
import com.example.w3c_school.db.DBHelper;
import com.example.w3c_school.db.SQLIDUS;
import com.example.w3c_school.entity.ContentItem;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SaveDao {

	private DBHelper dbHelper;

	public SaveDao(Context context) {
		super();
		dbHelper = new DBHelper(context);
	}

	public void addSave(ContentItem item) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		List<String> strs = new ArrayList<String>();
		strs.add(SaveTable.COL_ID);
		strs.add(SaveTable.COL_TITLE);
		strs.add(SaveTable.COL_TEXT);
		strs.add(SaveTable.COL_CODE);
		strs.add(SaveTable.COL_USER);
		strs.add(SaveTable.COL_TEST_TAG);
		strs.add(SaveTable.COL_TEST_CODE);
		database.execSQL(
				SQLIDUS.doInsert(strs, SaveTable.TABLE_NAME),
				new Object[] { item.getId(), item.getTitle(),
						item.getContentText(), item.getCodeShow(),
						item.getUserNo(), item.isTestTag(), item.getTryCode() });
		database.close();

	}

	public void addSave(List<ContentItem> items) {
		for (ContentItem ci : items) {
			addSave(ci);
		}
	}

	public List<ContentItem> querySave(String userNo) {
		if (null != userNo) {
			SQLiteDatabase database = dbHelper.getReadableDatabase();
			List<String> strs = new ArrayList<String>();
			strs.add(SaveTable.COL_ID);
			strs.add(SaveTable.COL_TITLE);
			strs.add(SaveTable.COL_TEXT);
			strs.add(SaveTable.COL_CODE);
			strs.add(SaveTable.COL_USER);
			strs.add(SaveTable.COL_TEST_TAG);
			strs.add(SaveTable.COL_TEST_CODE);
			Cursor cursor = database.rawQuery(SQLIDUS.querySQL(strs,
					SaveTable.TABLE_NAME, SaveTable.COL_USER, userNo), null);
			List<ContentItem> list = new ArrayList<ContentItem>();
			while (cursor.moveToNext()) {
				ContentItem contentItem = new ContentItem();
				contentItem.setId(cursor.getInt(cursor
						.getColumnIndex(SaveTable.COL_ID)));
				contentItem.setTitle(cursor.getString(cursor
						.getColumnIndex(SaveTable.COL_TITLE)));
				contentItem.setContentText(cursor.getString(cursor
						.getColumnIndex(SaveTable.COL_TEXT)));
				contentItem.setCodeShow(cursor.getString(cursor
						.getColumnIndex(SaveTable.COL_CODE)));
				contentItem.setUserNo(cursor.getString(cursor
						.getColumnIndex(SaveTable.COL_USER)));
				contentItem.setTestTag(cursor.getInt(cursor
						.getColumnIndex(SaveTable.COL_TEST_TAG)));
				contentItem.setTryCode(cursor.getString(cursor
						.getColumnIndex(SaveTable.COL_TEST_CODE)));
				list.add(contentItem);
			}
			cursor.close();
			return list;
		} else {
			return null;
		}

	}

	public List<ContentItem> querySave(ContentItem ci, String userNo) {
		if (null != userNo) {
			SQLiteDatabase database = dbHelper.getReadableDatabase();
			List<String> strs = new ArrayList<String>();
			strs.add(SaveTable.COL_ID);
			strs.add(SaveTable.COL_TITLE);
			strs.add(SaveTable.COL_TEXT);
			strs.add(SaveTable.COL_CODE);
			strs.add(SaveTable.COL_USER);
			strs.add(SaveTable.COL_TEST_TAG);
			strs.add(SaveTable.COL_TEST_CODE);
			Cursor cursor = database.rawQuery(SQLIDUS.querySQL(strs,
					SaveTable.TABLE_NAME, SaveTable.COL_TITLE, ci.getTitle(),
					SaveTable.COL_USER, userNo), null);
			List<ContentItem> list = new ArrayList<ContentItem>();
			while (cursor.moveToNext()) {
				ContentItem contentItem = new ContentItem();
				contentItem.setId(cursor.getInt(cursor
						.getColumnIndex(SaveTable.COL_ID)));
				contentItem.setTitle(cursor.getString(cursor
						.getColumnIndex(SaveTable.COL_TITLE)));
				contentItem.setContentText(cursor.getString(cursor
						.getColumnIndex(SaveTable.COL_TEXT)));
				contentItem.setCodeShow(cursor.getString(cursor
						.getColumnIndex(SaveTable.COL_CODE)));
				contentItem.setUserNo(cursor.getString(cursor
						.getColumnIndex(SaveTable.COL_USER)));
				contentItem.setTestTag(cursor.getInt(cursor
						.getColumnIndex(SaveTable.COL_TEST_TAG)));
				contentItem.setTryCode(cursor.getString(cursor
						.getColumnIndex(SaveTable.COL_TEST_CODE)));
				list.add(contentItem);
			}
			cursor.close();
			return list;
		} else {
			return null;
		}

	}

	public List<ContentItem> querySave(ContentItem ci) {
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		List<String> strs = new ArrayList<String>();
		strs.add(SaveTable.COL_ID);
		strs.add(SaveTable.COL_TITLE);
		strs.add(SaveTable.COL_TEXT);
		strs.add(SaveTable.COL_CODE);
		strs.add(SaveTable.COL_USER);
		strs.add(SaveTable.COL_TEST_TAG);
		strs.add(SaveTable.COL_TEST_CODE);
		Cursor cursor = database.rawQuery(SQLIDUS.querySQL(strs,
				SaveTable.TABLE_NAME, SaveTable.COL_ID, ci.getId()), null);
		List<ContentItem> list = new ArrayList<ContentItem>();
		while (cursor.moveToNext()) {
			ContentItem contentItem = new ContentItem();
			contentItem.setId(cursor.getInt(cursor
					.getColumnIndex(SaveTable.COL_ID)));
			contentItem.setTitle(cursor.getString(cursor
					.getColumnIndex(SaveTable.COL_TITLE)));
			contentItem.setContentText(cursor.getString(cursor
					.getColumnIndex(SaveTable.COL_TEXT)));
			contentItem.setCodeShow(cursor.getString(cursor
					.getColumnIndex(SaveTable.COL_CODE)));
			contentItem.setUserNo(cursor.getString(cursor
					.getColumnIndex(SaveTable.COL_USER)));
			contentItem.setTestTag(cursor.getInt(cursor
					.getColumnIndex(SaveTable.COL_TEST_TAG)));
			contentItem.setTryCode(cursor.getString(cursor
					.getColumnIndex(SaveTable.COL_TEST_CODE)));
			list.add(contentItem);
		}
		cursor.close();
		return list;

	}

	public void updateSave(ContentItem cm, String userNo) {

		List<ContentItem> cms = querySave(cm, userNo);
		if (cms.size() > 0) {
			cancelSave(cm);
		}
		addSave(cm);
	}

	public void cancelSave(ContentItem cm) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		String delSQL = "delete from " + SaveTable.TABLE_NAME;
		delSQL += " where ";
		delSQL += SaveTable.COL_TITLE + " = ?";
		database.execSQL(delSQL, new String[] { cm.getTitle() });
		database.close();
	}
}
