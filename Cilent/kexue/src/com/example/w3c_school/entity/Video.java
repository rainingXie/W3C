package com.example.w3c_school.entity;

import java.io.Serializable;

import android.R.integer;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="videos")
public class Video implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@DatabaseField(id=true)
	private String name;
	@DatabaseField
	private String size;
	@DatabaseField
	private String totalTime;
	@DatabaseField
	private String url;
	
	private int id;
	
	private int totalSize;
	private int downloadState;
	private int downloadSize;

	public Video() {
		super();
	}

	public Video(int id,String name, String size, String totalTime, String url) {
		super();
		this.name = name;
		this.size = size;
		this.totalTime = totalTime;
		this.url = url;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDownloadState() {
		return downloadState;
	}

	public void setDownloadState(int downloadState) {
		this.downloadState = downloadState;
	}

	public int getDownloadSize() {
		return downloadSize;
	}

	public void setDownloadSize(int downloadSize) {
		this.downloadSize = downloadSize;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	
	

}
