package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.Jdbc;

public class NoteDao {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	public void addNote(String name,String content,String date,String userNo){
		con = Jdbc.getCon();
		String sql = "insert into note(name,content,date,userNo) values(?,?,?,?)";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1,name);
			pst.setString(2,content);
			pst.setString(3,date);
			pst.setString(4,userNo);
			pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Jdbc.free(rs, pst, con);
		}
	}
	
	public void deleteNote(int id){
		con = Jdbc.getCon();
		String sql = "delete from note where id = " + id;
		
		try {
			pst = con.prepareStatement(sql);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public List<HashMap<String, Object>> selectAllNote(String userNo) {
		con = Jdbc.getCon();
		String sql = "select * from note where userNo = "+userNo;		
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();		
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("id",rs.getInt("id"));
				map.put("name",rs.getString("name"));
				map.put("content",rs.getString("content"));
				map.put("date",rs.getString("date"));
				map.put("userNo", rs.getString("userNo"));
				
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			Jdbc.free(rs, pst, con);
		}
		return list;
	}

}
