package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.Jdbc;
import entity.ApkInfo;

public class ApkInfoDao {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	public int addApk(ApkInfo apkInfo) {
		int i = 0;
		if (findApk(apkInfo)) {
			if (deleteApk(apkInfo) == 1) {
				i = insertApk(apkInfo);
			}
		}
		i = insertApk(apkInfo);
		return i;
	}

	public boolean findApk(ApkInfo apkInfo) {
		con = Jdbc.getCon();
		String sql = "select name from apkInfo where code = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, apkInfo.getCode());
			rs = pst.executeQuery();

			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Jdbc.free(rs, pst, con);
		}
		return false;
	}

	public int insertApk(ApkInfo apkInfo) {
		int i = 0;
		con = Jdbc.getCon();
		String sql = "insert into apkInfo(code,name,mark,vid,url) values (?,?,?,?,?)";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, apkInfo.getCode());
			pst.setString(2, apkInfo.getName());
			pst.setString(3, apkInfo.getMark());
			pst.setInt(4, apkInfo.getVid());
			pst.setString(5, apkInfo.getUrl());
			i = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public int deleteApk(ApkInfo apkInfo) {
		int i = 0;
		con = Jdbc.getCon();
		String sql = "delete from apkInfo where code = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, apkInfo.getCode());
			i = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Jdbc.free(rs, pst, con);
		}
		return i;
	}

	public ApkInfo selectApk() {
		con = Jdbc.getCon();
		String sql = "select * from apkInfo";
		ApkInfo info = null;
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				info = new ApkInfo();
				info.setCode(rs.getString("code"));
				info.setMark(rs.getString("mark"));
				info.setName(rs.getString("name"));
				info.setUrl(rs.getString("url"));
				info.setVid(rs.getInt("vid"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Jdbc.free(rs, pst, con);
		}
		return info;

	}

}
