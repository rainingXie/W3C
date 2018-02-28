package com.example.w3c_school.entity;

public class StudyLog {
	
	
	private String content;
	private String date;
	private String tag;
	public StudyLog() {
		super();
	}
	public StudyLog(String content, String date, String tag) {
		super();
		this.content = content;
		this.date = date;
		this.tag = tag;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	
	

}
