package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.Jdbc;

public class DiscussDao {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	public int insertDiscuss(String content,
			String date, int isQuote, int quoteFloor, int floor,
			int parentId, String type,String no) {
		int i = 0;
		con = Jdbc.getCon();
		String sql = "insert into discuss(content,date,isQuote,quoteFloor,floor,parentId,type,no) values (?,?,?,?,?,?,?,?)";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, content);
			pst.setString(2, date);
			pst.setInt(3, isQuote);
			pst.setInt(4, quoteFloor);
			pst.setInt(5, floor);
			pst.setInt(6, parentId);
			pst.setString(7, type);
			pst.setString(8, no);
			i = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	

	public List<HashMap<String, Object>> findDiscuss(int parentId, String type) {
		con = Jdbc.getCon();
		String sql = "select * from discuss where parentId = ? and type = ?";
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, parentId);
			pst.setString(2, type);
			rs = pst.executeQuery();

			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("content", rs.getString("content"));
				map.put("date", rs.getString("date"));
				map.put("floor", rs.getInt("floor"));
				map.put("img", rs.getString("img"));
				map.put("isQuote", rs.getInt("isQuote"));
				map.put("no", rs.getString("no"));
				map.put("parentId", rs.getInt("parentId"));
				map.put("quoteFloor", rs.getString("quoteFloor"));
				map.put("type", rs.getString("type"));
				map.put("userName", rs.getString("userName"));

				list.add(map);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Jdbc.free(rs, pst, con);
		}
		return list;
	}

	public List<HashMap<String, Object>> selectDiscusses() {
		con = Jdbc.getCon();
		String sql = "select * from discuss";
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("content", rs.getString("content"));
				map.put("date", rs.getString("date"));
				map.put("floor", rs.getInt("floor"));
				map.put("img", rs.getString("img"));
				map.put("isQuote", rs.getInt("isQuote"));
				map.put("no", rs.getString("no"));
				map.put("parentId", rs.getInt("parentId"));
				map.put("quoteFloor", rs.getString("quoteFloor"));
				map.put("type", rs.getString("type"));
				map.put("userName", rs.getString("userName"));

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
