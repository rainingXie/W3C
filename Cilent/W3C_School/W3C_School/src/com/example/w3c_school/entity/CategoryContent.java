package com.example.w3c_school.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "categoryContents")
public class CategoryContent implements Serializable {

	@DatabaseField
	private int parentId;
	@DatabaseField
	private String title;
	@DatabaseField
	private int id;
	@DatabaseField
	private int label;
	@DatabaseField
	private String courseTag;

	private List<ContentItem> children;

	public CategoryContent() {
		super();
		children = new ArrayList<ContentItem>();
	}

	public CategoryContent(int parentId, String title, int id) {
		super();
		this.parentId = parentId;
		this.title = title;
		this.id = id;
		children = new ArrayList<ContentItem>();
	}

	public CategoryContent(int parentId, String title, int id,
			String courseTag) {
		super();
		this.parentId = parentId;
		this.title = title;
		this.id = id;
		this.courseTag = courseTag;
		children = new ArrayList<ContentItem>();
	}

	public CategoryContent(int parentId, String title, int id, int label,
			String courseTag) {
		super();
		this.parentId = parentId;
		this.title = title;
		this.id = id;
		this.label = label;
		this.courseTag = courseTag;
		children = new ArrayList<ContentItem>();
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ContentItem> getChildren() {
		return children;
	}

	public void setChildren(List<ContentItem> children) {
		this.children = children;
	}

	public String getCourseTag() {
		return courseTag;
	}

	public void setCourseTag(String courseTag) {
		this.courseTag = courseTag;
	}

	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}

}
