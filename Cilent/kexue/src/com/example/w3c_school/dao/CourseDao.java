package com.example.w3c_school.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.w3c_school.db.DBConstant.CourseTable;
import com.example.w3c_school.db.DBHelper;
import com.example.w3c_school.db.SQLIDUS;
import com.example.w3c_school.db.SqlStatment;
import com.example.w3c_school.entity.Category;
import com.example.w3c_school.entity.Course;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CourseDao {

	private DBHelper dbHelper;

	public CourseDao(Context context) {
		super();
		dbHelper = new DBHelper(context);
	}

	public void addCourse(Course course) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		List<String> strs = new ArrayList<String>();
		strs.add(CourseTable.COL_CID);
		strs.add(CourseTable.COL_COUSER_NAME);
		strs.add(CourseTable.COL_CTYPE_ID);
		strs.add(CourseTable.COL_CTYPE_NAME);
		strs.add(CourseTable.COL_TITLE_ID);
		strs.add(CourseTable.COL_TITLE_NAME);
		database.execSQL(
				SQLIDUS.doInsert(strs, CourseTable.TABLE_NAME),
				new Object[] { course.getCid(), course.getCourse_name(),
						course.getCtype_id(), course.getCtype_name(),
						course.getCtitle_id(), course.getCtitle_name()});
		database.close();

	}

	public void addSaves(List<Course> cs) {
		for (Course c : cs) {
			addCourse(c);
		}
	}

	/**
	 * 查询第一层的数据
	 * @return
	 */
	public List<Category> queryCType() {
			SQLiteDatabase database = dbHelper.getReadableDatabase();
			Cursor cursor = database.rawQuery(SqlStatment.queryCTypeSql(),
					null);
			List<Category> list = new ArrayList<Category>();
			while (cursor.moveToNext()) {
				Category c = new Category();
				c.setParentId(0);
				c.setShceduleName(cursor.getString(cursor.getColumnIndex(CourseTable.COL_CTYPE_NAME)));
				c.setShceduleId(cursor.getInt(cursor.getColumnIndex(CourseTable.COL_CTYPE_ID)));
				list.add(c);
			}
			cursor.close();
		return list;

	}
	
	/**
	 * 查询第二层的数据
	 * @return
	 */
	public List<Category> queryCCouser() {
			SQLiteDatabase database = dbHelper.getReadableDatabase();
			Cursor cursor = database.rawQuery(SqlStatment.queryCCouserSql(),
					null);
			List<Category> list = new ArrayList<Category>();
			while (cursor.moveToNext()) {
				Category c = new Category();
				c.setParentId(cursor.getInt(cursor.getColumnIndex(CourseTable.COL_CTYPE_ID)));
				c.setShceduleName(cursor.getString(cursor.getColumnIndex(CourseTable.COL_CID)));
				c.setShceduleId(cursor.getInt(cursor.getColumnIndex(CourseTable.COL_COUSER_NAME)));
				list.add(c);
			}
			cursor.close();
		return list;

	}
	
	/**
	 * 查询第三层的数据
	 * @return
	 */
	public List<Category> queryCTitle() {
			SQLiteDatabase database = dbHelper.getReadableDatabase();
			Cursor cursor = database.rawQuery(SqlStatment.queryCTitleSql(),
					null);
			List<Category> list = new ArrayList<Category>();
			while (cursor.moveToNext()) {
				Category c = new Category();
				c.setParentId(cursor.getInt(cursor.getColumnIndex(CourseTable.COL_CID)));
				c.setShceduleName(cursor.getString(cursor.getColumnIndex(CourseTable.COL_TITLE_NAME)));
				c.setShceduleId(cursor.getInt(cursor.getColumnIndex(CourseTable.COL_TITLE_ID)));
				list.add(c);
			}
			cursor.close();
		return list;

	}

}
