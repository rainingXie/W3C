package com.example.w3c_school.entity;

import java.io.Serializable;


@SuppressWarnings("serial")
public class Topic implements Serializable{

	private String id;
	private String userName;
	private String img;
	private String content;
	private int goodNum;
	private int replyment;
	private String date;
	private int isGood;
	private String goodPerson;
	
	public Topic() {
		super();
	}
	public Topic(String id, String userName, String img, String content,
			int goodNum, int replyment, String date) {
		super();
		this.id = id;
		this.userName = userName;
		this.img = img;
		this.content = content;
		this.goodNum = goodNum;
		this.replyment = replyment;
		this.date = date;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getGoodNum() {
		return goodNum;
	}
	public void setGoodNum(int goodNum) {
		this.goodNum = goodNum;
	}
	public int getReplyment() {
		return replyment;
	}
	public void setReplyment(int replyment) {
		this.replyment = replyment;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getIsGood() {
		return isGood;
	}
	public void setIsGood(int isGood) {
		this.isGood = isGood;
	}
	public String getGoodPerson() {
		return goodPerson;
	}
	public void setGoodPerson(String goodPerson) {
		this.goodPerson = goodPerson;
	}
	
	
	
	
}
