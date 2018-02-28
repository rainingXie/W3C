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
	private long count;

	@DatabaseField
	private int favorTag;
	@DatabaseField
	private String userNo;

	@DatabaseField
	private String tryCode;

	@DatabaseField
	private int label;

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
			String codeShow, int testTag, long count, int favorTag) {
		super();
		this.id = id;
		this.title = title;
		this.parentId = parentId;
		this.contentText = contentText;
		this.codeShow = codeShow;
		this.testTag = testTag;
		this.count = count;
		this.favorTag = favorTag;
	}

	public ContentItem(int id, String title, int parentId, String contentText,
			String codeShow, int testTag, long count, int favorTag,
			String userNo) {
		super();
		this.id = id;
		this.title = title;
		this.parentId = parentId;
		this.contentText = contentText;
		this.codeShow = codeShow;
		this.testTag = testTag;
		this.count = count;
		this.favorTag = favorTag;
		this.userNo = userNo;
	}

	public ContentItem(int id, String title, String contentText,
			String codeShow, int testTag, String userNo) {
		super();
		this.id = id;
		this.title = title;
		this.contentText = contentText;
		this.codeShow = codeShow;
		this.testTag = testTag;
		this.userNo = userNo;
	}

	public ContentItem(int id, String title, int parentId, String contentText,
			String codeShow, int testTag, long count, int favorTag,
			String userNo, String tryCode, int label) {
		super();
		this.id = id;
		this.title = title;
		this.parentId = parentId;
		this.contentText = contentText;
		this.codeShow = codeShow;
		this.testTag = testTag;
		this.count = count;
		this.favorTag = favorTag;
		this.userNo = userNo;
		this.tryCode = tryCode;
		this.label = label;
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

	public int isTestTag() {
		return testTag;
	}

	public void setTestTag(int testTag) {
		this.testTag = testTag;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public int isFavorTag() {
		return favorTag;
	}

	public void setFavorTag(int favorTag) {
		this.favorTag = favorTag;
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

	public int getFavorTag() {
		return favorTag;
	}

	public ContentItem(int id, String title, int parentId, String contentText,
			String codeShow, int testTag, long count, int favorTag,
			String userNo, String tryCode) {
		super();
		this.id = id;
		this.title = title;
		this.parentId = parentId;
		this.contentText = contentText;
		this.codeShow = codeShow;
		this.testTag = testTag;
		this.count = count;
		this.favorTag = favorTag;
		this.userNo = userNo;
		this.tryCode = tryCode;
	}

	public ContentItem(int id, String title, String contentText,
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

	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}

}
