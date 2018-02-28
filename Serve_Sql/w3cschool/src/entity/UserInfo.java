package entity;

public class UserInfo {
	private String userNo;
	private String userName;
	private String userPwd;
	private String userImg;
	private String userRemark;
	private String province;
	private String city;
	
	public UserInfo() {
		super();
	}
	
	public UserInfo(String userNo, String userName, String userPwd,
			String userImg, String userRemark, String province, String city) {
		super();
		this.userNo = userNo;
		this.userName = userName;
		this.userPwd = userPwd;
		this.userImg = userImg;
		this.userRemark = userRemark;
		this.province = province;
		this.city = city;
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
