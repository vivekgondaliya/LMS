package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.BookLoans;

public class BookLoansDAO extends BaseDAO<BookLoans> implements ResultSetExtractor<List<BookLoans>>{

	@Autowired
	BookDAO bDAO;
	@Autowired
	LibraryBranchDAO lbDAO;
	@Autowired
	BorrowerDAO borDAO;
	
	//INSERT
	public void create(BookLoans bl) throws Exception {
		template.update("insert into tbl_book_loans values(?, ?, ?, ?, ?, ?)",
				new Object[] { bl.getBook().getBookId(), bl.getBranch().getBranchId(), bl.getBorrow().getCardNo(), bl.getDateOut(), bl.getDueDate(), bl.getDateIn() });
	}

	//UPDATE
	public void update(BookLoans bl) throws Exception {
		template.update("update tbl_book_loans set dateOut=?, dueDate=?, dateIn=? where bookId=?, branchId=?, cardNo=? ",
				new Object[] { bl.getDateOut(), bl.getDueDate(), bl.getDateIn(), bl.getBook(), bl.getBranch(), bl.getBorrow()});

	}

	//DELETE
	public void delete(BookLoans bl) throws Exception {
		template.update("delete from tbl_book_loans where bookId =?, branchId=?, cardNo=?",
				new Object[] { bl.getBook(), bl.getBranch(), bl.getBorrow()});
	}

	//SELECT ALL
	public List<BookLoans> readAll() throws Exception {
		return template.query("select * from tbl_book_loans", this);
	}

	//SELECT ONE
	public BookLoans readOne(int bookId, int branchId, int cardNo) throws Exception {
		List<BookLoans> bookLoans = template.query("select * from tbl_book_loans", new Object[] {bookId, branchId, cardNo}, this);
		if(bookLoans!=null && bookLoans.size()>0){
			return bookLoans.get(0);
		}
		return null;
	}

	//RETURN List of book_loans
	@Override
	public List<BookLoans> extractData(ResultSet rs) throws SQLException {

		//create a list
		List<BookLoans> bookLoans = new ArrayList<BookLoans>(); 

		//populate the list
		while(rs.next()){

			BookLoans bl  = new BookLoans();
			try {
				bl.setBook(bDAO.readOne(rs.getInt("bookId")));
				bl.setBranch(lbDAO.readOne(rs.getInt("branchId")));
				bl.setBorrow(borDAO.readOne(rs.getInt("cardNo")));
				bl.setDateOut(rs.getDate("dateOut"));
				bl.setDueDate(rs.getDate("dueDate"));
				bl.setDateIn(rs.getDate("dateIn"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			bookLoans.add(bl);
		}
		return bookLoans;
	}

	//book loans by Name
	public List<BookLoans> readByBookLoansName(String searchString) throws Exception{
		searchString = "%"+searchString+"%";
		return (List<BookLoans>) template.query("select * from tbl_book_loans where cardNo like ?", new Object[] {searchString}, this);
	}

	//read for PAGINATION
	public List<BookLoans> readAll(int pageNo, int pageSize) throws Exception{
		setPageNo(pageNo);
		setPageSize(pageSize);
		return (List<BookLoans>) template.query("select * from tbl_book_loans", this);
	}

}
