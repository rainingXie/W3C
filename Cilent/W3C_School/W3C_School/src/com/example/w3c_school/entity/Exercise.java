package com.example.w3c_school.entity;

public class Exercise {

	private int id;
	private String name;
	private String title;
	private String itemA;
	private String itemB;
	private String itemC;
	private String itemD;
	
	private int tag;
	private int flagTag;

	private String answer;
	
	private String type;

	public Exercise() {
		super();
	}
	
	

	public Exercise(int id,String title, String itemA,
			String itemB, String itemC, String itemD, String answer) {
		super();
		this.id = id;
		this.title = title;
		this.itemA = itemA;
		this.itemB = itemB;
		this.itemC = itemC;
		this.itemD = itemD;
		this.answer = answer;
	}



	public Exercise(int id, String name, String title, String itemA,
			String itemB, String itemC, String itemD, int tag, int flagTag,
			String answer) {
		super();
		this.id = id;
		this.name = name;
		this.title = title;
		this.itemA = itemA;
		this.itemB = itemB;
		this.itemC = itemC;
		this.itemD = itemD;
		this.tag = tag;
		this.flagTag = flagTag;
		this.answer = answer;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getItemA() {
		return itemA;
	}



	public void setItemA(String itemA) {
		this.itemA = itemA;
	}



	public String getItemB() {
		return itemB;
	}



	public void setItemB(String itemB) {
		this.itemB = itemB;
	}



	public String getItemC() {
		return itemC;
	}



	public void setItemC(String itemC) {
		this.itemC = itemC;
	}



	public String getItemD() {
		return itemD;
	}



	public void setItemD(String itemD) {
		this.itemD = itemD;
	}



	public int getTag() {
		return tag;
	}



	public void setTag(int tag) {
		this.tag = tag;
	}



	public int getFlagTag() {
		return flagTag;
	}



	public void setFlagTag(int flagTag) {
		this.flagTag = flagTag;
	}


	public String getAnswer() {
		return answer;
	}



	public void setAnswer(String answer) {
		this.answer = answer;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}
	
	

	

}
