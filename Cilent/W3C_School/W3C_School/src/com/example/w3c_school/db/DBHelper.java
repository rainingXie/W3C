package com.example.w3c_school.db;

import java.sql.SQLException;

import com.example.w3c_school.entity.Book;
import com.example.w3c_school.entity.Category;
import com.example.w3c_school.entity.ContentItem;
import com.example.w3c_school.entity.Note;
import com.example.w3c_school.entity.TestQuestions;
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
			TableUtils.createTable(arg1, ContentItem.class);
			TableUtils.createTable(arg1, Note.class);
			TableUtils.createTable(arg1, Book.class);
			TableUtils.createTable(arg1, Video.class);
			TableUtils.createTable(arg1, Category.class);
			TableUtils.createTable(arg1, TestQuestions.class);
			arg0.execSQL(DBConstant.SaveTable.getCreateSQL());
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

	private Dao<ContentItem, String> itemDao = null;

	public Dao<ContentItem, String> getContentItemDao() {
		if (itemDao == null) {
			try {
				itemDao = getDao(ContentItem.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return itemDao;
	}

	private Dao<Note, String> noteDao = null;

	public Dao<Note, String> getNoteDao() {
		if (noteDao == null) {
			try {
				noteDao = getDao(Note.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return noteDao;
	}

	private Dao<Book, String> bookDao = null;

	public Dao<Book, String> getBookDao() {
		if (bookDao == null) {
			try {
				bookDao = getDao(Book.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return bookDao;
	}

	private Dao<Category, Integer> categoryDao = null;

	public Dao<Category, Integer> getCategoryDao() {
		if (categoryDao == null) {
			try {
				categoryDao = getDao(Category.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return categoryDao;
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
	private Dao<TestQuestions, String> testQuestionsDao = null;

	public Dao<TestQuestions, String> getTestQuestionsDao() {
		if (testQuestionsDao == null) {
			try {
				testQuestionsDao = getDao(TestQuestions.class);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return testQuestionsDao;
	}
	@Override
	public void close() {
		super.close();
		userDao = null;
		itemDao = null;
		noteDao = null;
		bookDao = null;
		categoryDao = null;
		videoDao = null;
		testQuestionsDao = null;
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
	}

}
