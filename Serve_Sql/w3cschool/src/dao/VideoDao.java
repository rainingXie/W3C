package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.Jdbc;
import entity.Video;

public class VideoDao {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	public int addVideo(Video video){
		int i = 0;
		if(findVideo(video)){
			if(deleteVideo(video)==1){
				i = insertVideo(video);
			}		
		}
		i = insertVideo(video);
		return i;
	}

	public boolean findVideo(Video video){
		con = Jdbc.getCon();
		String sql = "select name from videoinfo where name = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, video.getName());
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
	
	public int insertVideo(Video video){
		int i = 0;
		con = Jdbc.getCon();
		String sql = "insert into videoinfo (name,label,totalTime,url) values (?,?,?,?)";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, video.getName());
			pst.setString(2, video.getLabel());
			pst.setString(3, video.getTotalTime());
			pst.setString(4, video.getUrl());
			i = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;		
	}
	
	public int deleteVideo(Video video){
		int i = 0;
		con = Jdbc.getCon();
		String sql = "delete from videoinfo where name = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, video.getName());
			i = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Jdbc.free(rs, pst, con);
		}
		return i;
	}
	
	public List<HashMap<String, Object>> selectAllVideos(){
		con = Jdbc.getCon();
		String sql = "select * from videoinfo";
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("name", rs.getString("name"));
				map.put("label", rs.getString("label"));
				map.put("totalTime", rs.getString("totalTime"));
				map.put("url", rs.getString("url"));
				
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Jdbc.free(rs, pst, con);
		}
		
		return list;
	}


}
