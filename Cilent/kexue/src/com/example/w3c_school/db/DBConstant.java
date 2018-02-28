package com.example.w3c_school.db;

/**
 * @author xieyu
 *
 */
public class DBConstant {
		
	public static class SaveTable{
		public static final String TABLE_NAME = "save";
		public static final String COL_ID = "_id";
		public static final String COL_CONTENT_ID = "content_id";
		public static final String COL_USER="userNo";
		
		public static String getCreateSQL() {
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE IF NOT EXISTS ");
			sb.append(TABLE_NAME);
			sb.append("(");
			sb.append(COL_ID);
			sb.append(" INTEGER NOT NULL PRIMARY KEY autoincrement,");
			sb.append(COL_CONTENT_ID);
			sb.append(" INTEGER(100),");
			sb.append(COL_USER);
			sb.append(" VARCHAR(50)");
			sb.append(")");

			return sb.toString();
		}
		
		
	}
	public static class ContentItemTable{
		public static final String TABLE_NAME = "items";
		public static final String COL_ID = "content_id";
		public static final String COL_TITLE = "content_title";
		public static final String COL_TEXT="content_text";
		public static final String COL_CODE="codeShow";
		public static final String COL_USER="content_user";
		public static final String COL_TEST_TAG="testTag";
		public static final String COL_TEST_CODE="testCode";
		public static final String COL_PARENT_ID="parent_id";
		public static String getCreateSQL() {
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE IF NOT EXISTS ");
			sb.append(TABLE_NAME);
			sb.append("(");
			sb.append(COL_ID);
			sb.append(" INTEGER NOT NULL PRIMARY KEY autoincrement,");
			sb.append(COL_PARENT_ID);
			sb.append(" INTEGER(10),");
			sb.append(COL_TITLE);
			sb.append(" VARCHAR(100),");
			sb.append(COL_TEXT);
			sb.append(" VARCHAR(1000),");
			sb.append(COL_CODE);
			sb.append(" VARCHAR(1000),");
			sb.append(COL_TEST_TAG);
			sb.append(" VARCHAR(1000),");
			sb.append(COL_TEST_CODE);
			sb.append(" VARCHAR(1000),");
			sb.append(COL_USER);
			sb.append(" VARCHAR(50)");
			sb.append(")");

			return sb.toString();
		}
	}
	public static class NoteTable{
		public static final String TABLE_NAME = "note";
		public static final String COL_ID = "id";
		public static final String COL_CONTENT_ID = "content_id";
		public static final String COL_TITLE = "title";
		public static final String COL_CONETENT="content";
		public static final String COL_DATE="date";
		public static final String COL_USER="content_user";
		
		public static String getCreateSQL() {
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE IF NOT EXISTS ");
			sb.append(TABLE_NAME);
			sb.append("(");
			sb.append(COL_ID);
			sb.append(" VARCHAR(100) NOT NULL PRIMARY KEY,");
			sb.append(COL_CONTENT_ID);
			sb.append(" INTEGER(100),");
			sb.append(COL_TITLE);
			sb.append(" VARCHAR(100),");
			sb.append(COL_CONETENT);
			sb.append(" VARCHAR(1000),");
			sb.append(COL_DATE);
			sb.append(" VARCHAR(100),");
			sb.append(COL_USER);
			sb.append(" VARCHAR(50)");
			sb.append(")");

			return sb.toString();
		}
	}

	
	public static class CourseTable{
		public static final String TABLE_NAME = "course";
		public static final String COL_TITLE_ID = "ctitle_id";
		public static final String COL_TITLE_NAME = "ctitle_name";
		public static final String COL_CID = "cid";
		public static final String COL_COUSER_NAME="course_name";
		public static final String COL_CTYPE_ID="ctype_id";
		public static final String COL_CTYPE_NAME="ctype_name";
		
		public static String getCreateSQL() {
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE IF NOT EXISTS ");
			sb.append(TABLE_NAME);
			sb.append("(");
			sb.append(COL_TITLE_ID);
			sb.append(" INTEGER(100) NOT NULL PRIMARY KEY,");
			sb.append(COL_TITLE_NAME);
			sb.append(" VARCHAR(100),");
			sb.append(COL_CID);
			sb.append(" INTEGER(100),");
			sb.append(COL_COUSER_NAME);
			sb.append(" VARCHAR(1000),");
			sb.append(COL_CTYPE_ID);
			sb.append(" INTEGER(100),");
			sb.append(COL_CTYPE_NAME);
			sb.append(" VARCHAR(50)");
			sb.append(")");

			return sb.toString();
		}
	}

}
