package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateTable {
	
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	public int createNoFindTable(String tableName){
		int i=0;
		con = Jdbc.getCon();
	    StringBuilder sb = new StringBuilder();
	    sb.append("CREATE TABLE " + tableName);
	    sb.append("(id int(11) NOT NULL PRIMARY KEY,");
	    sb.append("title varchar(100) NULL,");
	    sb.append("parentId int(11) NULL,");
	    sb.append("contentText text,");
	    sb.append("codeShow text,");
	    sb.append("testTag int(11) NULL,");
	    sb.append("tryCode text,");
	    sb.append("label int(11) NULL)");
	    try {
			pst = con.prepareStatement(sb.toString());
			i = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	    
	}
}
