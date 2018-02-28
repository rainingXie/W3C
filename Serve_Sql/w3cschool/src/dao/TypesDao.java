package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entity.Types;

import utils.Jdbc;

public class TypesDao {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	public int addType(Types types){
		int i = 0;
		if(findType(types)){
//			if(deleteType(types)==1){
//				i = insertType(types);
//			}		
			return i = 0;
		}
		i = insertType(types);
		return i;
	}

	public boolean findType(Types types){
		con = Jdbc.getCon();
		String sql = "select functionId from totle_types where functionName = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, types.getFunctionName());
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
	
	public int insertType(Types types){
		int i = 0;
		con = Jdbc.getCon();
		String sql = "insert into totle_types (functionName,introduce,childrenName) values (?,?,?)";
		try {
			pst = con.prepareStatement(sql);
			pst.setString(1, types.getFunctionName());
			pst.setString(2, types.getIntroduce());
			pst.setString(3, types.getChildrenName());
			i = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;		
	}
	
	public int deleteType(Types types){
		int i = 0;
		con = Jdbc.getCon();
		String sql = "delete from totle_types where functionId = ?";
		try {
			pst = con.prepareStatement(sql);
			pst.setInt(1, types.getFunctionId());
			i = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Jdbc.free(rs, pst, con);
		}
		return i;
	}
	
	public List<Types> selectTypes(){
		con = Jdbc.getCon();
		String sql = "select * from totle_types";
		List<Types> list = new ArrayList<Types>();
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				Types types = new Types();
				types.setFunctionId(rs.getInt("functionId"));
				types.setFunctionName(rs.getString("functionName"));
				types.setIntroduce(rs.getString("introduce"));
				types.setChildrenName(rs.getString("childrenName"));
				list.add(types);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Jdbc.free(rs, pst, con);
		}
		
		return list;
	}
	
	public List<HashMap<String, Object>> selectAllTypes(){
		con = Jdbc.getCon();
		String sql = "select * from totle_types";
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("functionId", rs.getInt("functionId"));
				map.put("functionName", rs.getString("functionName"));
				map.put("introduce", rs.getString("introduce"));
				map.put("childrenName", rs.getString("childrenName"));
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
