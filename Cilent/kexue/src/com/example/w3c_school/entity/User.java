package com.example.w3c_school.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users")
public class User {

	@DatabaseField(id = true)
	private String userNo;

	@DatabaseField
	private String userName;

	@DatabaseField
	private String userPwd;

	@DatabaseField
	private String userImg;

	@DatabaseField
	private String userRemark;
	@DatabaseField
	private String province;
	@DatabaseField
	private String city;

	public User() {
		super();
	}

	public User(String userNo, String userName, String userPwd, String userImg,
			String userRemark) {
		super();
		this.userNo = userNo;
		this.userName = userName;
		this.userPwd = userPwd;
		this.userImg = userImg;
		this.userRemark = userRemark;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public String getUserRemark() {
		return userRemark;
	}

	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
