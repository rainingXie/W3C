package com.example.w3c_school.db;

import java.sql.SQLException;

import com.example.w3c_school.entity.Note;
import com.example.w3c_school.entity.User;
import com.example.w3c_school.entity.Video;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends OrmLiteSqliteOpenHelper {

	private static DBHelper helper;
	private static final String DB_NAME = "w3cschool.db";
	private static final int DB_VERSION = 1;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	public static DBHelper getInstance(Context context) {
		synchronized (DBHelper.class) {
			if (helper == null) {
				synchronized (DBHelper.class) {
					helper = new DBHelper(context);
				}
			}
		}
		return helper;
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		try {
			TableUtils.createTable(arg1, User.class);
			TableUtils.createTable(arg1, Video.class);
			arg0.execSQL(DBConstant.CourseTable.getCreateSQL());
			arg0.execSQL(DBConstant.SaveTable.getCreateSQL());
			arg0.execSQL(DBConstant.ContentItemTable.getCreateSQL());
			arg0.execSQL(DBConstant.NoteTable.getCreateSQL());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private Dao<User, String> userDao = null;

	public Dao<User, String> getUserDao() {
		if (userDao == null) {
			try {
				userDao = getDao(User.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userDao;
	}
	private Dao<Video, String> videoDao = null;

	public Dao<Video, String> getVideoDao() {
		if (videoDao == null) {
			try {
				videoDao = getDao(Video.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return videoDao;
	}

	@Override
	public void close() {
		super.close();
		userDao = null;
		videoDao = null;
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
	}

}
