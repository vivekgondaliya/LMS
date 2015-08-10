package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Borrower;

public class BorrowerDAO extends BaseDAO<Borrower> implements ResultSetExtractor<List<Borrower>>{
	
	@Autowired
	BookLoansDAO bookLoanDAO;
	
	//INSERT
	public void create(Borrower borrow) throws Exception {
		template.update("insert into tbl_borrower (name, address, phone) values(?, ?, ?)",
				new Object[] { borrow.getName(), borrow.getAddress(), borrow.getPhone() });
	}

	//UPDATE
	public void update(Borrower borrow) throws Exception {
		template.update("update tbl_borrower set name=?, address=?, phone=? where cardNo=?",
				new Object[] { borrow.getName(), borrow.getAddress(), borrow.getPhone(), borrow.getCardNo()});

	}

	//DELETE
	public void delete(Borrower borrow) throws Exception {
		template.update("delete from tbl_borrower where cardNo = ?",
				new Object[] { borrow.getCardNo()});
	}

	//SELECT ALL
	public List<Borrower> readAll() throws Exception {
		return template.query("select * from tbl_borrower", this);
	}

	//SELECT ONE
	public Borrower readOne(int cardNo) throws Exception {

		List<Borrower> borrower = template.query("select * from tbl_borrower where cardNo=?", new Object[] {cardNo}, this);
		if(borrower!=null && borrower.size()>0){
			return borrower.get(0);
		}
		return null;
	}

	//RETURN List of Publishers
	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {

		//create a list
		List<Borrower> borrower = new ArrayList<Borrower>();

		//populate the list
		while(rs.next()){

			Borrower borrow  = new Borrower();
			borrow.setCardNo(rs.getInt("cardNo"));
			borrow.setName(rs.getString("name"));
			borrow.setAddress(rs.getString("address"));
			borrow.setPhone(rs.getString("phone"));

//			List<BookLoans> bookLoans = template.query("select * from tbl_book_loans where cardNo = ?"
//					, new Object[] {rs.getInt("cardNo")}, bookLoanDAO);
//			borrow.setBookLoans(bookLoans);

			borrower.add(borrow);
		}
		return borrower;
	}

	//borrower by Name
	public List<Borrower> readByBorrowerName(String searchString) throws Exception{
		searchString = "%"+searchString+"%";
		return (List<Borrower>) template.query("select * from tbl_borrower where name like ?", new Object[] {searchString}, this);
	}

	//read for PAGINATION
	public List<Borrower> readAll(int pageNo, int pageSize) throws Exception{
		setPageNo(pageNo);
		setPageSize(pageSize);
		return (List<Borrower>) template.query("select * from tbl_borrower", this);
	}

}
