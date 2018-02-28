package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class FindTableNameDao {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	public boolean findTableByTableName(String tableName){
		con = Jdbc.getCon();
		String sql = "SELECT NAME FROM SYSOBJECTS WHERE TYPE='U'";
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				if(rs.getString(0).equals(tableName)){
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
