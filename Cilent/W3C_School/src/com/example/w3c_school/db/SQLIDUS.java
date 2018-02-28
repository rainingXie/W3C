package com.example.w3c_school.db;

import java.util.List;


public class SQLIDUS {

	public static String doInsert(List<String> strs,String tableName){
		
		StringBuilder insertSQL = new StringBuilder();
		insertSQL.append("INSERT INTO ");
		insertSQL.append(tableName);
		insertSQL.append("(");
		for(int i = 0 ;i < strs.size()-1 ; i++){
			insertSQL.append(strs.get(i));
			insertSQL.append(",");
		}
		
		insertSQL.append(strs.get(strs.size()-1));
		insertSQL.append(") VALUES (");
		for(int i = 0 ;i < strs.size()-1 ; i++){
			insertSQL.append("?,");
		}
		insertSQL.append("?)");
		
		return insertSQL.toString();
		
		
	}
	
	public static String querySQL(List<String> strs,String tableName,String no,Object noArg) {
		StringBuilder querySQL = new StringBuilder();
		querySQL.append("select ");
		for(int i = 0 ; i <strs.size()-1; i++){
			querySQL.append(strs.get(i)+",");
		}
		querySQL.append(strs.get(strs.size()-1));
		querySQL.append(" from ");
		querySQL.append(tableName);
		querySQL.append(" where ");
		querySQL.append(no + "=");
		querySQL.append(noArg);
		return querySQL.toString();
	}
	
	public static String querySQL(List<String> strs,String tableName,String no,Object noArg,String user,Object userArg) {
		StringBuilder querySQL = new StringBuilder();
		querySQL.append("select ");
		for(int i = 0 ; i <strs.size()-1; i++){
			querySQL.append(strs.get(i)+",");
		}
		querySQL.append(strs.get(strs.size()-1));
		querySQL.append(" from ");
		querySQL.append(tableName);
		querySQL.append(" where ");
		querySQL.append(no + "=");
		querySQL.append("\'"+ noArg+"\'"+" and ");
		querySQL.append(user + "=");
		querySQL.append("\'"+userArg+"\'");
		return querySQL.toString();
	}
	public static String querySQL(List<String> strs,String tableName) {
		StringBuilder querySQL = new StringBuilder();
		querySQL.append("select ");
		for(int i = 0 ; i <strs.size()-1; i++){
			querySQL.append(strs.get(i)+",");
		}
		querySQL.append(strs.get(strs.size()-1));
		querySQL.append(" from ");
		querySQL.append(tableName);
		return querySQL.toString();
	}
}
