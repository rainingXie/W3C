package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.Jdbc;
import entity.UserInfo;

public class UserInfoDao {
	Connection con;
	PreparedStatement pst;
	ResultSet rs;

	public void addUserInfo(String userNo, String userName, String userPwd,
			String province, String city) {
		con = Jdbc.getCon();
		String sql = "insert into userinfo(userNo,userName,userPwd,province,city) values(?,?,?,?,?)";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, userNo);
			pst.setString(2, userName);
			pst.setString(3, userPwd);
			pst.setString(4, province);
			pst.setString(5, city);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Jdbc.free(rs, pst, con);
		}
	}
	
	public void addQQUserInfo(String userNo, String userName, String img,
			String province, String city) {
		con = Jdbc.getCon();
		String sql = "insert into userinfo(userNo,userName,userImg,province,city) values(?,?,?,?,?)";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, userNo);
			pst.setString(2, userName);
			pst.setString(3, img);
			pst.setString(4, province);
			pst.setString(5, city);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Jdbc.free(rs, pst, con);
		}
	}

	public int updateImg(String img, String userNo) {
		int i = 0;
		con = Jdbc.getCon();
		String sql = "update userinfo set userImg=? where userNo=?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, img);
			pst.setString(2, userNo);
			i = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Jdbc.free(rs, pst, con);
		}

		return i;
	}

	public int updatePwd(String pwd, String userNo) {
		int i = 0;
		con = Jdbc.getCon();
		String sql = "update userinfo set userPwd=? where userNo=?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, pwd);
			pst.setString(2, userNo);
			i = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Jdbc.free(rs, pst, con);
		}

		return i;
	}

	public UserInfo login(String userNo, String pwd) {
		con = Jdbc.getCon();
		String sql = "select * from userinfo where userNo=? and userPwd=?";
		UserInfo userInfo = null;
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, userNo);
			pst.setString(2, pwd);
			rs = pst.executeQuery();

			while (rs.next()) {
				userInfo = new UserInfo();
				userInfo.setUserNo(rs.getString("userNo"));
				userInfo.setUserName(rs.getString("userName"));
				userInfo.setUserPwd(rs.getString("userPwd"));
				userInfo.setUserImg(rs.getString("userImg"));
				userInfo.setUserRemark(rs.getString("userRemark"));
				userInfo.setProvince(rs.getString("province"));
				userInfo.setCity(rs.getString("city"));
			}
		} catch (SQLException var8) {
			var8.printStackTrace();
		} finally {
			Jdbc.free(rs, pst, con);
		}

		return userInfo;
	}

	public List<HashMap<String, Object>> selectAllUserInfo() {
		con = Jdbc.getCon();
		String sql = "select * from userinfo";
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("userNo", rs.getString("userNo"));
				map.put("userName", rs.getString("userName"));
				map.put("userPwd", rs.getString("userPwd"));
				map.put("userImg", rs.getString("userImg"));
				map.put("userRemark", rs.getString("userRemark"));
				map.put("province", rs.getString("province"));
				map.put("city", rs.getString("city"));

				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Jdbc.free(rs, pst, con);
		}
		return list;
	}
}
