package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.BookLoans;
import com.gcit.lms.domain.Borrower;

public class BorrowerDAO extends BaseDAO<Borrower> {

	public BorrowerDAO(Connection conn) throws Exception {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	//INSERT
	public void create(Borrower borrow) throws Exception {
		save("insert into tbl_borrower (name, address, phone) values(?, ?, ?)",
				new Object[] { borrow.getName(), borrow.getAddress(), borrow.getPhone() });
	}

	//UPDATE
	public void update(Borrower borrow) throws Exception {
		save("update tbl_borrower set name=?, address=?, phone=? where cardNo=?",
				new Object[] { borrow.getName(), borrow.getAddress(), borrow.getPhone(), borrow.getCardNo()});

	}

	//DELETE
	public void delete(Borrower borrow) throws Exception {
		save("delete from tbl_borrower where cardNo = ?",
				new Object[] { borrow.getCardNo()});
	}

	//SELECT ALL
	public List<Borrower> readAll() throws Exception {
		return read("select * from tbl_borrower", null);
	}

	//SELECT ONE
	public Borrower readOne(int cardNo) throws Exception {

		List<Borrower> borrower = read("select * from tbl_borrower where cardNo=?", new Object[] {cardNo});
		if(borrower!=null && borrower.size()>0){
			return borrower.get(0);
		}
		return null;
	}

	//RETURN List of Publishers
	@Override
	public List<Borrower> extractData(ResultSet rs) throws Exception {

		//create a list
		List<Borrower> borrower = new ArrayList<Borrower>();
		BookLoansDAO blDAO = new BookLoansDAO(getConnection());

		//populate the list
		while(rs.next()){

			Borrower borrow  = new Borrower();
			borrow.setCardNo(rs.getInt("cardNo"));
			borrow.setName(rs.getString("name"));
			borrow.setAddress(rs.getString("address"));
			borrow.setPhone(rs.getString("phone"));

			List<BookLoans> bookLoans = (List<BookLoans>) blDAO.readFirstLevel("select * from tbl_book_loans where cardNo = ?"
					, new Object[] {rs.getInt("cardNo")});
			borrow.setBookLoans(bookLoans);

			borrower.add(borrow);
		}
		return borrower;
	}

	@Override
	public List<Borrower> extractDataFirstLevel(ResultSet rs) throws Exception {

		//create a list
		List<Borrower> borrower = new ArrayList<Borrower>();

		//populate the list
		while(rs.next()){

			Borrower borrow  = new Borrower();
			borrow.setCardNo(rs.getInt("cardNo"));
			borrow.setName(rs.getString("name"));
			borrow.setAddress(rs.getString("address"));
			borrow.setPhone(rs.getString("phone"));

			borrower.add(borrow);
		}
		return borrower;
	}

	//borrower by Name
	public List<Borrower> readByBorrowerName(String searchString) throws Exception{
		searchString = "%"+searchString+"%";
		return (List<Borrower>) read("select * from tbl_borrower where name like ?", new Object[] {searchString});
	}

	//read for PAGINATION
	public List<Borrower> readAll(int pageNo, int pageSize) throws Exception{
		setPageNo(pageNo);
		setPageSize(pageSize);
		return (List<Borrower>) read("select * from tbl_borrower", null);

	}

}
