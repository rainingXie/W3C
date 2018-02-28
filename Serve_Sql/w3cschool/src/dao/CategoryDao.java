package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entity.Category;

import utils.Jdbc;

public class CategoryDao {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	public int addCategory(Category category){
		int i = 0;
		if(findCategory(category)){
			if(deleteCategory(category)==1){
				i = insertCategory(category);
			}		
		}
		i = insertCategory(category);
		return i;
	}

	public boolean findCategory(Category category){
		con = Jdbc.getCon();
		String sql = "select shceduleId from type_categorys where shceduleName = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, category.getShceduleName());
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
	
	public int insertCategory(Category category){
		int i = 0;
		con = Jdbc.getCon();
		String sql = "insert into type_categorys (shceduleName,parentId) values (?,?)";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(0, category.getShceduleName());
			pst.setInt(1, category.getParentId());
			i = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;		
	}
	
	public int deleteCategory(Category category){
		int i = 0;
		con = Jdbc.getCon();
		String sql = "delete from type_categorys where shceduleId = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, category.getShceduleId());
			i = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Jdbc.free(rs, pst, con);
		}
		return i;
	}
	
	public List<HashMap<String, Object>> selectAllCategory(int parentId){
		con = Jdbc.getCon();
		String sql = "select * from type_categorys where parentId = ?";
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, parentId);
			rs = pst.executeQuery();
			while(rs.next()){
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("shceduleId", rs.getInt("shceduleId"));
				map.put("shceduleName", rs.getString("shceduleName"));
				map.put("parentId", rs.getInt("parentId"));
				
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Jdbc.free(rs, pst, con);
		}	
		
		return list;	
	}
	
	public List<HashMap<String, Object>> selectAllCategory(){
		con = Jdbc.getCon();
		String sql = "select * from type_categorys";
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("shceduleId", rs.getInt("shceduleId"));
				map.put("shceduleName", rs.getString("shceduleName"));
				map.put("parentId", rs.getInt("parentId"));
				
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Jdbc.free(rs, pst, con);
		}	
		
		return list;	
	}
	public List<Category> selectCategorysByParentId(int parentId){
		con = Jdbc.getCon();
		String sql = "select * from type_categorys where parentId = ?";
		List<Category> list = new ArrayList<Category>();
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, parentId);
			rs = pst.executeQuery();
			while(rs.next()){
				Category date = new Category();
				date.setShceduleId(rs.getInt("shceduleId"));
				date.setShceduleName(rs.getString("shceduleName"));
				date.setParentId(rs.getInt("parentId"));
				
				list.add(date);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Jdbc.free(rs, pst, con);
		}	
		
		return list;	
	}
	
	public int selectCategorysByName(String name){
		con = Jdbc.getCon();
		String sql = "select shceduleId from type_categorys where shceduleName = ?";
		int id = -1;
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, name);
			rs = pst.executeQuery();
			while(rs.next()){
				id = rs.getInt("shceduleId");							
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Jdbc.free(rs, pst, con);
		}			
		return id;	
	}
}
