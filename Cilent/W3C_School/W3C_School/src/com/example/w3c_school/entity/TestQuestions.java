package com.example.w3c_school.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="TestQuestions")
public class TestQuestions implements Serializable{

	@DatabaseField
	private String subjectName;
	@DatabaseField(id=true)
	private String pageName;
	@DatabaseField
	private int tag;
	
	public TestQuestions() {
		super();
	}
	
	

	public TestQuestions(String subjectName, String pageName, int tag) {
		super();
		this.subjectName = subjectName;
		this.pageName = pageName;
		this.tag = tag;
	}



	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}



	public int getTag() {
		return tag;
	}



	public void setTag(int tag) {
		this.tag = tag;
	}
	
	
}
