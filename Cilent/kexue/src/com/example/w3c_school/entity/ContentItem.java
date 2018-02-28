package com.example.w3c_school.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "contentItem")
public class ContentItem implements Serializable {

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField
	private String title;

	@DatabaseField
	private int parentId;

	@DatabaseField
	private String contentText;

	@DatabaseField
	private String codeShow;

	@DatabaseField
	private int testTag;

	@DatabaseField
	private String tryCode;


	public ContentItem() {
		super();
	}

	public ContentItem(int id, String title, String contentText) {
		super();
		this.id = id;
		this.title = title;
		this.contentText = contentText;
	}

	public ContentItem(int id, String title, int parentId) {
		super();
		this.id = id;
		this.title = title;
		this.parentId = parentId;
	}

	public ContentItem(int id, String title, int parentId, String contentText,
			String codeShow, int testTag) {
		super();
		this.id = id;
		this.title = title;
		this.parentId = parentId;
		this.contentText = contentText;
		this.codeShow = codeShow;
		this.testTag = testTag;
	}

	public ContentItem(int id, String title, int parentId, String contentText,
			String codeShow, int testTag, String tryCode) {
		super();
		this.id = id;
		this.title = title;
		this.parentId = parentId;
		this.contentText = contentText;
		this.codeShow = codeShow;
		this.testTag = testTag;
		this.tryCode = tryCode;
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

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
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

	public int getTestTag() {
		return testTag;
	}

	public void setTestTag(int testTag) {
		this.testTag = testTag;
	}

	public String getTryCode() {
		return tryCode;
	}

	public void setTryCode(String tryCode) {
		this.tryCode = tryCode;
	}

	
}
