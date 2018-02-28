package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.Jdbc;

import entity.Content;

public class ContentDao {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	public int addContentList(Content content,String tableName){
		int i = 0;
		if(findContent(content, tableName)){
			if(deleteContent(content, tableName)==1){
				i = insertContent(content, tableName);
			}		
		}
		i = insertContent(content, tableName);
		return i;
	}

	public boolean findContent(Content content,String tableName){
		con = Jdbc.getCon();
		String sql = "select id from " +tableName+ " where id = ? and parentId = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, content.getId());
			pst.setInt(2, content.getParentId());
			rs = pst.executeQuery();
			
			if(rs.next()){
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Jdbc.free(rs, pst, con);
		}
		return false;		
	}
	
	public int insertContent(Content content,String tableName){
		int i = 0;
		con = Jdbc.getCon();
		String sql = "insert into " +tableName+ " (title,parentId,contentText,codeShow,testTag,tryCode,label) values (?,?,?,?,?,?,?)";
		try {
			pst = con.prepareStatement(sql);	
			pst.setString(1, content.getTitle());
			pst.setInt(2, content.getParentId());
			pst.setString(3, content.getContentText());
			pst.setString(4, content.getCodeShow());
			pst.setInt(5, content.getTestTag());
			pst.setString(6, content.getTryCode());
			pst.setInt(7, content.getLabel());
			i = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;		
	}
	
	public int deleteContent(Content content,String tableName){
		int i = 0;
		con = Jdbc.getCon();
		String sql = "delete from " +tableName+ " where id = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, content.getId());
			i = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Jdbc.free(rs, pst, con);
		}
		return i;
	}
	
	public List<HashMap<String, Object>> selectAllContent(String tableName,int parentId) {
		con = Jdbc.getCon();
		String sql = "select * from " + tableName + " where parentId = ?";
		
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();		
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, parentId);
			rs = pst.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("id",rs.getInt("id"));
				map.put("title",rs.getString("title"));
				map.put("parentId",rs.getInt("parentId"));
				map.put("contentText",rs.getString("contentText"));
				map.put("codeShow",rs.getString("codeShow"));
				map.put("testTag",rs.getInt("testTag"));
				map.put("tryCode",rs.getString("tryCode"));
				map.put("lable", rs.getInt("lable"));
				
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
