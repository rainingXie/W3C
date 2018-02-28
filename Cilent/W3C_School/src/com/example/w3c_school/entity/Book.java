package com.example.w3c_school.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "books")
public class Book implements Serializable {
	
	@DatabaseField
	private String introduce;
	@DatabaseField
	private String functionName;

	@DatabaseField(id = true)
	private int functionId;
	@DatabaseField
	private String childrenName;

	public Book() {
		super();
	}


	

	




	public Book(String introduce, String functionName, int functionId,
			String childrenName) {
		super();
		this.introduce = introduce;
		this.functionName = functionName;
		this.functionId = functionId;
		this.childrenName = childrenName;
	}









	public int getFunctionId() {
		return functionId;
	}

	public void setFunctionId(int functionId) {
		this.functionId = functionId;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}









	public String getChildrenName() {
		return childrenName;
	}









	public void setChildrenName(String childrenName) {
		this.childrenName = childrenName;
	}

}
