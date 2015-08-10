package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Borrower;

public class BorrowerDAO extends BaseDAO<Borrower> implements ResultSetExtractor<List<Borrower>>{

	@Autowired
	BookLoansDAO bookLoanDAO;

	private final String BORROWER_COLLECTION = "borrowers";

	//CREATE
	public void create(Borrower borrow) throws Exception {
		mongoOps.insert(borrow, BORROWER_COLLECTION);
	}

	//READ
	public List<Borrower> readAll() throws Exception {
		return mongoOps.findAll(Borrower.class, BORROWER_COLLECTION);
	}

	//read ONE
	public Borrower readOne(UUID cardNo) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(cardNo));
		return mongoOps.findOne(query, Borrower.class);
	}

	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		return null;
	}

	//read Search
	public List<Borrower> readByBorrowerName(String searchString) throws Exception{
		searchString = "%"+searchString+"%";
		return (List<Borrower>) template.query("select * from tbl_borrower where name like ?", new Object[] {searchString}, this);
	}

	//read for PAGINATION
	public List<Borrower> readAll(int pageNo, int pageSize) throws Exception{
		return mongoOps.findAll(Borrower.class, BORROWER_COLLECTION);
	}


	//UPDATE
	public void update(Borrower borrow) throws Exception {
		template.update("update tbl_borrower set name=?, address=?, phone=? where cardNo=?",
				new Object[] { borrow.getName(), borrow.getAddress(), borrow.getPhone(), borrow.getCardNo()});

	}

	//DELETE
	public void delete(Borrower borrow) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").exists(true));
		mongoOps.findAndRemove(query, Borrower.class, BORROWER_COLLECTION);
	}

}
