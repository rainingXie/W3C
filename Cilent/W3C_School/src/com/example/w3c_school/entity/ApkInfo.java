package com.example.w3c_school.entity;

public class ApkInfo {

	private String code;
	private String mark;
	private String name;
	private String url;
	private int  vid;
	public ApkInfo() {
		super();
	}
	public ApkInfo(String code, String mark, String name, String url, int vid) {
		super();
		this.code = code;
		this.mark = mark;
		this.name = name;
		this.url = url;
		this.vid = vid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getVid() {
		return vid;
	}
	public void setVid(int vid) {
		this.vid = vid;
	}
	
	
}
