package com.example.w3c_school.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "saves")
public class Save implements Serializable {

	@DatabaseField(id = true)
	private int id;

	@DatabaseField
	private String title;


	@DatabaseField
	private String contentText;

	@DatabaseField
	private String codeShow;

	@DatabaseField
	private int testTag;


	@DatabaseField
	private String userNo;
	
	@DatabaseField
	private String tryCode;




	public Save() {
		super();
	}

	public Save(int id, String title, String contentText) {
		super();
		this.id = id;
		this.title = title;
		this.contentText = contentText;
	}



	public Save(int id, String title, String contentText,
			String codeShow, int testTag, String userNo) {
		super();
		this.id = id;
		this.title = title;
		this.contentText = contentText;
		this.codeShow = codeShow;
		this.testTag = testTag;
		this.userNo = userNo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getContentText() {
		return contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	public String getCodeShow() {
		return codeShow;
	}

	public void setCodeShow(String codeShow) {
		this.codeShow = codeShow;
	}

	public int isTestTag() {
		return testTag;
	}

	public void setTestTag(int testTag) {
		this.testTag = testTag;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getTryCode() {
		return tryCode;
	}

	public void setTryCode(String tryCode) {
		this.tryCode = tryCode;
	}

	public int getTestTag() {
		return testTag;
	}
	

	public Save(int id, String title, String contentText,
			String codeShow, int testTag, String userNo, String tryCode) {
		super();
		this.id = id;
		this.title = title;
		this.contentText = contentText;
		this.codeShow = codeShow;
		this.testTag = testTag;
		this.userNo = userNo;
		this.tryCode = tryCode;
	}
	
	

}
