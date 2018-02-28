package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entity.TestPager;

import utils.Jdbc;

public class TestPagerDao {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	public int addTestPager(TestPager testPager){
		int i = 0;
		if(findTestPager(testPager)){
			return i = 0;
		}
		i = insertTestPager(testPager);
		return i;		
	}
	public int addTestPager2(TestPager testPager){
		int i = 0;
		if(findTestPager(testPager)){
			return i = 0;
		}
		i = insertTestPager2(testPager);
		return i;		
	}
	
	public int insertTestPager(TestPager testPager){
		int i=0;
		con = Jdbc.getCon();
		String sql = "insert into testpager(name,title,itemA,itemB,itemC,itemD,answer,tag,flagTag,type) values(?,?,?,?,?,?,?,?,?,?)";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1,testPager.getName());
			pst.setString(2,testPager.getTitle());
			pst.setString(3,testPager.getItemA());
			pst.setString(4,testPager.getItemB());
			pst.setString(5,testPager.getItemC());
			pst.setString(6,testPager.getItemD());
			pst.setString(7,testPager.getAnswer());
			pst.setInt(8, testPager.getTag());
			pst.setInt(9, testPager.getFlagTag());
			pst.setString(10, testPager.getType());
			
			i = pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Jdbc.free(rs, pst, con);
		}
		return i;
	}
	
	public int insertTestPager2(TestPager testPager){
		int i=0;
		con = Jdbc.getCon();
		String sql = "insert into testpager(title,itemA,itemB,itemC,itemD,answer,tag) values(?,?,?,?,?,?,?)";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1,testPager.getTitle());
			pst.setString(2,testPager.getItemA());
			pst.setString(3,testPager.getItemB());
			pst.setString(4,testPager.getItemC());
			pst.setString(5,testPager.getItemD());
			pst.setString(6,testPager.getAnswer());
			pst.setInt(7, testPager.getTag());
			
			i = pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Jdbc.free(rs, pst, con);
		}
		return i;
	}
	
	public List<Integer> allTagByFlagtag(){
		con = Jdbc.getCon();
		String sql = "select tag from testpager where flagTag = 1";
		List<Integer> list = new ArrayList<Integer>();
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				int tag = rs.getInt("tag");
				list.add(tag);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Jdbc.free(rs, pst, con);
		}
		return list;
	}
	
	public boolean findTestPager(TestPager testPager){
		con = Jdbc.getCon();
		String sql = "select id from testpager where title = " + testPager.getTitle();
		
		try {
			pst = con.prepareStatement(sql);
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
	
	public int deleteTestpager(TestPager testPager){
		int i=0;
		con = Jdbc.getCon();
		String sql = "delete from testpager where id = " + testPager.getId();
		
		try {
			pst = con.prepareStatement(sql);
			i = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;		
	}
	
	public List<Integer> findTagByName(String name){
		con = Jdbc.getCon();
		String sql = "select tag from testpager where name = " + name;
		List<Integer> list = new ArrayList<Integer>();
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				int tag = rs.getInt("tag");
				list.add(tag);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Jdbc.free(rs, pst, con);
		}
		return list;
	}
	
	public List<String> allName(){
		con = Jdbc.getCon();
		String sql = "select name from testpager where flagTag = 1";
		List<String> list = new ArrayList<String>();
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				list.add(name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Jdbc.free(rs, pst, con);
		}
		return list;
	}
	
	public List<HashMap<String, Object>> selectAllpagers() {
		con = Jdbc.getCon();
		String sql = "select * from testpager where flagTag= 1";		
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();		
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("id",rs.getInt("id"));
				map.put("name",rs.getString("name"));
				map.put("title",rs.getString("title"));
				map.put("itemA",rs.getString("itemA"));
				map.put("itemB", rs.getString("itemB"));
				map.put("itemC", rs.getString("itemC"));
				map.put("itemD", rs.getString("itemD"));
				map.put("answer", rs.getString("answer"));
				map.put("tag", rs.getInt("tag"));
				map.put("flagTag", rs.getInt("flagTag"));
				map.put("type", rs.getString("type"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			Jdbc.free(rs, pst, con);
		}
		return list;
	}

	
	public List<HashMap<String, Object>> selectTestpagers(int tag) {
		con = Jdbc.getCon();
		String sql = "select * from testpager where tag="+tag;		
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();		
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("id",rs.getInt("id"));
				map.put("name",rs.getString("name"));
				map.put("title",rs.getString("title"));
				map.put("itemA",rs.getString("itemA"));
				map.put("itemB", rs.getString("itemB"));
				map.put("itemC", rs.getString("itemC"));
				map.put("itemD", rs.getString("itemD"));
				map.put("answer", rs.getString("answer"));
				map.put("tag", rs.getInt("tag"));
				map.put("flagTag", rs.getInt("flagTag"));
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
