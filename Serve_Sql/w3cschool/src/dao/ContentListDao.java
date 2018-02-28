package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import entity.ContentList;

import utils.Jdbc;

public class ContentListDao {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	public int addContentList(ContentList contentList,String tableName){
		int i = 0;
		if(findContentList(contentList, tableName)){
			if(deleteContentList(contentList, tableName)==1){
				i = insertContentList(contentList, tableName);
			}		
		}
		i = insertContentList(contentList, tableName);
		return i;
	}

	public boolean findContentList(ContentList contentList,String tableName){
		con = Jdbc.getCon();
		String sql = "select id from " +tableName+ " where id = ? and parentId = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, contentList.getId());
			pst.setInt(2, contentList.getParentId());
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
	
	public int insertContentList(ContentList contentList,String tableName){
		int i = 0;
		con = Jdbc.getCon();
		String sql = "insert into " +tableName+ " (title,parentId) values (?,?)";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, contentList.getTitle());
			pst.setInt(2, contentList.getParentId());
			i = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;		
	}
	
	public int deleteContentList(ContentList contentList,String tableName){
		int i = 0;
		con = Jdbc.getCon();
		String sql = "delete from " +tableName+ " where id = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, contentList.getId());
			i = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Jdbc.free(rs, pst, con);
		}
		return i;
	}
	
	public List<Integer> findCategoryIdByName(String tableName,String categoryName){
		con = Jdbc.getCon();
		String sql = "select id from " +tableName+ " where title=" + categoryName;
		List<Integer> list = new ArrayList<Integer>();
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();		
			if(rs.next()){
				int id = rs.getInt("id");
				list.add(id);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Jdbc.free(rs, pst, con);
		}
		return list;		
	}
	
	public List<HashMap<String, Object>> selectAllContentList(String tableName,int parentId){
		con = Jdbc.getCon();
		String sql = "select * from " + tableName + " where parentId=?";
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, parentId);
			rs = pst.executeQuery();
			while(rs.next()){
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("id", rs.getInt("id"));
				map.put("title", rs.getString("title"));
				map.put("parentId", rs.getInt("parentId"));;				
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Jdbc.free(rs, pst, con);
		}
		
		return list;
	}
	
	public List<ContentList> selectContentList(String tableName,int parentId){
		con = Jdbc.getCon();
		String sql = "select * from " + tableName + " where parentId=?";
		List<ContentList> list = new ArrayList<ContentList>();
		
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, parentId);
			rs = pst.executeQuery();
			while(rs.next()){
				ContentList content = new ContentList();
				content.setId(rs.getInt("id"));
				content.setTitle(rs.getString("title"));
				content.setParentId(rs.getInt("parentId"));;				
				list.add(content);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Jdbc.free(rs, pst, con);
		}
		
		return list;
	}
	
	public List<ContentList> selectContentList(String tableName){
		con = Jdbc.getCon();
		String sql = "select * from " + tableName;
		List<ContentList> list = new ArrayList<ContentList>();
		
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				ContentList content = new ContentList();
				content.setId(rs.getInt("id"));
				content.setTitle(rs.getString("title"));
				content.setParentId(rs.getInt("parentId"));;				
				list.add(content);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Jdbc.free(rs, pst, con);
		}
		
		return list;
	}
}
