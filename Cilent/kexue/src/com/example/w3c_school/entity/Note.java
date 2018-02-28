package com.example.w3c_school.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

public class Note implements Serializable{

	
	private String id;
	private String name;
	private String date;
	private String content;
	private String userNo;
	private String parentId;

	public Note() {
		super();
	}

	public Note(String name, String date, String content) {
		super();
		this.name = name;
		this.date = date;
		this.content = content;
	}

	public Note(String name, String date, String content, String userNo) {
		super();
		this.name = name;
		this.date = date;
		this.content = content;
		this.userNo = userNo;
	}
	
	

	public Note(String id, String name, String date, String content, String userNo) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.content = content;
		this.userNo = userNo;
	}
	

	public Note(String id, String name, String date, String content,
			String userNo, String parentId) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.content = content;
		this.userNo = userNo;
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	
	

}
