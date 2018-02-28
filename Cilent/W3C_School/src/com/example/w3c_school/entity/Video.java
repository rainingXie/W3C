package com.example.w3c_school.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="videos")
public class Video implements Serializable{

	@DatabaseField(id=true)
	private String name;
	@DatabaseField
	private String label;
	@DatabaseField
	private String totalTime;
	@DatabaseField
	private String url;

	public Video() {
		super();
	}

	public Video(String name, String label, String totalTime, String url) {
		super();
		this.name = name;
		this.label = label;
		this.totalTime = totalTime;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
