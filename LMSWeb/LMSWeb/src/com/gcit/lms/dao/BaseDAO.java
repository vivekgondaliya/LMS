package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public abstract class BaseDAO<T> {

	private int pageNo = -1;
	private int pageSize = 10;

	private Connection connection = null;
	
	public BaseDAO(Connection conn) throws Exception{
		
		this.connection = conn;
	}
	
	//get Connection 
	public Connection getConnection() throws Exception {
		
		return connection;
	}

	//for INSERT, UPDATE and DELETE
	public void save(String query, Object[] vals) throws Exception{

		connection = getConnection();
		PreparedStatement stmt = connection.prepareStatement(query);
		int count = 1;
		for(Object o: vals){
			stmt.setObject(count, o);
			count++;
		}
		stmt.executeUpdate();
	}


	public int saveWithID(String query, Object[] vals) throws Exception{

		connection = getConnection();
		
		PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		int count = 1;
		for(Object o: vals){
			stmt.setObject(count, o);
			count++;
		}
		stmt.executeUpdate();
		
		ResultSet rs = stmt.getGeneratedKeys();
		if(rs.next()){
			return rs.getInt(1);
		}
		else
			return -1;
	}
	
	//for SELECT
	public List<T> read(String query, Object[] vals) throws Exception{

		//List<T> objects = new ArrayList<T>();
		connection = getConnection();
		int pageNo = getPageNo();
		
		if(getPageNo() >-1){
			int start= ((pageNo-1) * 10) +1;
			if(start > 1){
				query = query + " LIMIT " + start + ", " +getPageSize();
			}else{
				query = query + " LIMIT 0, " + getPageSize();
			}
		}
		
		PreparedStatement stmt = connection.prepareStatement(query);
		if(vals!=null){
			int count = 1;
			for(Object o: vals){
				stmt.setObject(count, o);
				count++;
			}
		}

		ResultSet rs = stmt.executeQuery();
		return extractData(rs);
	}


	public List<T> readFirstLevel(String query, Object[] vals) throws Exception{
		
		//List<T> objects = new ArrayList<T>();
		connection = getConnection();
		PreparedStatement stmt = connection.prepareStatement(query);
		
		if(vals!=null){
			int count = 1;
			for(Object o: vals){
				stmt.setObject(count, o);
				count++;
			}
		}
		
		ResultSet rs = stmt.executeQuery();
		return extractDataFirstLevel(rs);
	}
	
	
	public abstract List<T> extractData(ResultSet rs) throws Exception;
	
	public abstract List<T> extractDataFirstLevel(ResultSet rs) throws Exception;
	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
