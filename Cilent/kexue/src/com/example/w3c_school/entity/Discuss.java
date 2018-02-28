package com.example.w3c_school.entity;

import java.io.Serializable;

public class Discuss implements Serializable {

	private String no;
	private String img;
	private String userName;
	private String content;
	private String date;
	private int isQuote;
	private int quoteFloor;
	private int floor;
	private int parentId;
	
	public Discuss() {
		super();
	}

	
	public Discuss(String no, String img, String userName, String content,
			String date, int isQuote, int quoteFloor) {
		super();
		this.no = no;
		this.img = img;
		this.userName = userName;
		this.content = content;
		this.date = date;
		this.isQuote = isQuote;
		this.quoteFloor = quoteFloor;
	}
	

	public Discuss(String no, String img, String userName, String content,
			String date, int isQuote, int quoteFloor, int floor) {
		super();
		this.no = no;
		this.img = img;
		this.userName = userName;
		this.content = content;
		this.date = date;
		this.isQuote = isQuote;
		this.quoteFloor = quoteFloor;
		this.floor = floor;
	}

	public Discuss(String no, String img, String userName, String content,
			String date, int isQuote, int quoteFloor, int floor, int parentId) {
		super();
		this.no = no;
		this.img = img;
		this.userName = userName;
		this.content = content;
		this.date = date;
		this.isQuote = isQuote;
		this.quoteFloor = quoteFloor;
		this.floor = floor;
		this.parentId = parentId;
	}

	public Discuss(String no, String img, String userName, String content,
			String date, int isQuote, int quoteFloor, int floor,
			int parentId, String type) {
		super();
		this.no = no;
		this.img = img;
		this.userName = userName;
		this.content = content;
		this.date = date;
		this.isQuote = isQuote;
		this.quoteFloor = quoteFloor;
		this.floor = floor;
		this.parentId = parentId;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public int getIsQuote() {
		return isQuote;
	}

	public void setIsQuote(int isQuote) {
		this.isQuote = isQuote;
	}

	public int getQuoteFloor() {
		return quoteFloor;
	}

	public void setQuoteFloor(int quoteFloor) {
		this.quoteFloor = quoteFloor;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	
}
