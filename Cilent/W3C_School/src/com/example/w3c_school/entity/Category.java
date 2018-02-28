package com.example.w3c_school.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "categorys")
public class Category implements Serializable {

	@DatabaseField
	private int parentId;
	@DatabaseField
	private String shceduleName;
	@DatabaseField(id = true)
	private int shceduleId;
	@DatabaseField
	private String courseTag;

	private List<CategoryContent> children;

	public Category() {
		super();
		children = new ArrayList<CategoryContent>();
	}

	public Category(int parentId, String shceduleName, int shceduleId) {
		super();
		this.parentId = parentId;
		this.shceduleName = shceduleName;
		this.shceduleId = shceduleId;
		children = new ArrayList<CategoryContent>();
	}

	public Category(int parentId, String shceduleName, int shceduleId,
			String courseTag) {
		super();
		this.parentId = parentId;
		this.shceduleName = shceduleName;
		this.shceduleId = shceduleId;
		this.courseTag = courseTag;
		children = new ArrayList<CategoryContent>();
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getShceduleName() {
		return shceduleName;
	}

	public void setShceduleName(String shceduleName) {
		this.shceduleName = shceduleName;
	}

	public int getShceduleId() {
		return shceduleId;
	}

	public void setShceduleId(int shceduleId) {
		this.shceduleId = shceduleId;
	}

	public List<CategoryContent> getChildren() {
		return children;
	}

	public void setChildren(List<CategoryContent> children) {
		this.children = children;
	}

	public String getCourseTag() {
		return courseTag;
	}

	public void setCourseTag(String courseTag) {
		this.courseTag = courseTag;
	}

}
