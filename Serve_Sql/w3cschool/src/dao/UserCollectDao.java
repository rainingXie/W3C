package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.Jdbc;

public class UserCollectDao {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	public void addUserCollect(String title,String contentText,String codeShow,String userNo,int testTag,String tryCode){
		con = Jdbc.getCon();
		String sql = "insert into usercollect(title,contentText,codeShow,userNo,testTag,tryCode) values(?,?,?,?,?,?)";
		try {
			pst = con.prepareStatement(sql);
	//		pst.setInt(1,id);
			pst.setString(1,title);
			pst.setString(2,contentText);
			pst.setString(3,codeShow);
			pst.setString(4,userNo);
			pst.setInt(5,testTag);
			pst.setString(6,tryCode);
			pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Jdbc.free(rs, pst, con);
		}
	}
	
	public void deleteUserCollect(int id){
		con = Jdbc.getCon();
		String sql = "delete from usercollect where id = " + id;
		
		try {
			pst = con.prepareStatement(sql);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public List<HashMap<String, Object>> selectAllUserCollect(String userNo) {
		con = Jdbc.getCon();
		String sql = "select * from usercollect where userNo="+userNo;		
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();		
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("id",rs.getInt("id"));
				map.put("title",rs.getString("title"));
				map.put("contentText",rs.getString("contentText"));
				map.put("codeShow",rs.getString("codeShow"));
				map.put("userNo", rs.getString("userNo"));
				map.put("testTag",rs.getInt("testTag"));
				map.put("tryCode",rs.getString("tryCode"));
				
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
