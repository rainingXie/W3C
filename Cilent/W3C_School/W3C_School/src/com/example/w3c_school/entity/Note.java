package com.example.w3c_school.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "note")
public class Note implements Serializable{

	@DatabaseField
	private int id;
	@DatabaseField(id=true)
	private String name;
	@DatabaseField
	private String date;
	@DatabaseField
	private String content;
	@DatabaseField
	private String userNo;

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
	
	

	public Note(int id, String name, String date, String content, String userNo) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.content = content;
		this.userNo = userNo;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

}
