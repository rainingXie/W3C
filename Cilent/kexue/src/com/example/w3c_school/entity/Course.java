package com.example.w3c_school.entity;

public class Course {
	private String ctitle_id;
	private String ctitle_name;
	private String ctitle_descrip;
	private String ctitle_remark;
	private String cid;
	private String course_name;
	private String ctype_id;
	private String ctype_name;
	public Course() {
		super();
	}
	public Course(String ctitle_id, String ctitle_name, String ctitle_descrip,
			String ctitle_remark, String cid, String course_name,
			String ctype_id, String ctype_name) {
		super();
		this.ctitle_id = ctitle_id;
		this.ctitle_name = ctitle_name;
		this.ctitle_descrip = ctitle_descrip;
		this.ctitle_remark = ctitle_remark;
		this.cid = cid;
		this.course_name = course_name;
		this.ctype_id = ctype_id;
		this.ctype_name = ctype_name;
	}
	public String getCtitle_id() {
		return ctitle_id;
	}
	public void setCtitle_id(String ctitle_id) {
		this.ctitle_id = ctitle_id;
	}
	public String getCtitle_name() {
		return ctitle_name;
	}
	public void setCtitle_name(String ctitle_name) {
		this.ctitle_name = ctitle_name;
	}
	public String getCtitle_descrip() {
		return ctitle_descrip;
	}
	public void setCtitle_descrip(String ctitle_descrip) {
		this.ctitle_descrip = ctitle_descrip;
	}
	public String getCtitle_remark() {
		return ctitle_remark;
	}
	public void setCtitle_remark(String ctitle_remark) {
		this.ctitle_remark = ctitle_remark;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public String getCtype_id() {
		return ctype_id;
	}
	public void setCtype_id(String ctype_id) {
		this.ctype_id = ctype_id;
	}
	public String getCtype_name() {
		return ctype_name;
	}
	public void setCtype_name(String ctype_name) {
		this.ctype_name = ctype_name;
	}
	
	
	

}
