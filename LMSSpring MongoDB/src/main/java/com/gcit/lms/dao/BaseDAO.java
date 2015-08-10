package com.gcit.lms.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class BaseDAO<T> {

	//JDBC Template
	@Autowired
	protected JdbcTemplate template;
	
	//MongoDB Template
	@Autowired
	protected MongoOperations mongoOps;
	
	private int pageNo = -1;
	private int pageSize = 10;
	
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
