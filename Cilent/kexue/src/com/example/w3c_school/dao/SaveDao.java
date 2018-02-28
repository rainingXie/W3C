package com.example.w3c_school.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.w3c_school.db.DBConstant.ContentItemTable;
import com.example.w3c_school.db.DBConstant.SaveTable;
import com.example.w3c_school.db.DBHelper;
import com.example.w3c_school.db.SQLIDUS;
import com.example.w3c_school.db.SqlStatment;
import com.example.w3c_school.entity.ContentItem;
import com.example.w3c_school.entity.User;

import android.R.integer;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SaveDao {

	private DBHelper dbHelper;

	public SaveDao(Context context) {
		super();
		dbHelper = new DBHelper(context);
	}

	public void addSave(ContentItem item, User user) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		List<String> strs = new ArrayList<String>();
		strs.add(SaveTable.COL_USER);
		strs.add(SaveTable.COL_CONTENT_ID);
		database.execSQL(SQLIDUS.doInsert(strs, SaveTable.TABLE_NAME),
				new Object[] { item.getId(), user.getUserNo() });
		database.close();

	}

	public void addSaves(List<ContentItem> items, User user) {
		for (ContentItem ci : items) {
			addSave(ci, user);
		}
	}

	public List<ContentItem> querySave(User user) {
		if (null != user) {
			SQLiteDatabase database = dbHelper.getReadableDatabase();
			Cursor cursor = database.rawQuery(SqlStatment.querySavesSql(user),
					null);
			List<ContentItem> list = new ArrayList<ContentItem>();
			while (cursor.moveToNext()) {
				ContentItem contentItem = new ContentItem();
				contentItem.setId(cursor.getInt(cursor
						.getColumnIndex(ContentItemTable.COL_ID)));
				contentItem.setTitle(cursor.getString(cursor
						.getColumnIndex(ContentItemTable.COL_TITLE)));
				contentItem.setContentText(cursor.getString(cursor
						.getColumnIndex(ContentItemTable.COL_TEXT)));
				contentItem.setCodeShow(cursor.getString(cursor
						.getColumnIndex(ContentItemTable.COL_CODE)));
				contentItem.setTestTag(cursor.getInt(cursor
						.getColumnIndex(ContentItemTable.COL_TEST_TAG)));
				contentItem.setTryCode(cursor.getString(cursor
						.getColumnIndex(ContentItemTable.COL_TEST_CODE)));
				contentItem.setParentId(cursor.getInt(cursor
						.getColumnIndex(ContentItemTable.COL_PARENT_ID)));
				list.add(contentItem);
			}
			cursor.close();
			return list;
		} else {
			return null;
		}

	}

	/**
	 * 查找该条内容用户是否收藏
	 * @param ci
	 * @param user
	 * @return
	 */
	public int querySave(ContentItem ci, User user) {
		if (null != user) {
			SQLiteDatabase database = dbHelper.getReadableDatabase();
			Cursor cursor = database.rawQuery(SqlStatment.querySavesSql(ci, user), null);
			int count = cursor.getCount();
			cursor.close();
			return count;
		} else {
			return 0;
		}

	}

	/**
	 * 取消该用户的收藏
	 * @param item
	 * @param user
	 */
	public void deleteSave(ContentItem item,User user) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		database.execSQL(SqlStatment.deleteSaveSql(item, user), null);
		database.close();
	}
}
