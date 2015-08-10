package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.BookLoans;

public class BookLoansDAO extends BaseDAO<BookLoans> {

	public BookLoansDAO(Connection conn) throws Exception {
		super(conn);
		// TODO Auto-generated constructor stub
	}

		//INSERT
		public void create(BookLoans bl) throws Exception {
			save("insert into tbl_book_loans values(?, ?, ?, ?, ?, ?)",
					new Object[] { bl.getBook(), bl.getBranch(), bl.getBorrow(), bl.getDateOut(), bl.getDueDate(), bl.getDateIn() });
		}

		//UPDATE
		public void update(BookLoans bl) throws Exception {
			save("update tbl_book_loans set dateOut=?, dueDate=?, dateIn=? where bookId=?, branchId=?, cardNo=? ",
					new Object[] { bl.getDateOut(), bl.getDueDate(), bl.getDateIn(), bl.getBook(), bl.getBranch(), bl.getBorrow()});

		}

		//DELETE
		public void delete(BookLoans bl) throws Exception {
			save("delete from tbl_book_loans where bookId =?, branchId=?, cardNo=?",
					new Object[] { bl.getBook(), bl.getBranch(), bl.getBorrow()});
		}

		//SELECT ALL
		public List<BookLoans> readAll() throws Exception {
			return read("select * from tbl_book_loans", null);
		}

		//SELECT ONE
		public BookLoans readOne(int bookId, int branchId, int cardNo) throws Exception {
			
			List<BookLoans> bookLoans = read("select * from tbl_book_loans", new Object[] {bookId, branchId, cardNo});
			if(bookLoans!=null && bookLoans.size()>0){
				return bookLoans.get(0);
			}
			return null;
		}

		//RETURN List of book_loans
		@Override
		public List<BookLoans> extractData(ResultSet rs) throws Exception {
			
			//create a list
			List<BookLoans> bookLoans = new ArrayList<BookLoans>();
			BookDAO bDAO = new BookDAO(getConnection());
			LibraryBranchDAO lbDAO = new LibraryBranchDAO(getConnection());
			BorrowerDAO borDAO = new BorrowerDAO(getConnection()); 
			
			//populate the list
			while(rs.next()){
				
				BookLoans bl  = new BookLoans();
				bl.setBook(bDAO.readOne(rs.getInt("bookId")));
				bl.setBranch(lbDAO.readOne(rs.getInt("branchId")));
				bl.setBorrow(borDAO.readOne(rs.getInt("cardNo")));
				bl.setDateOut(rs.getTimestamp("dateOut"));
				bl.setDueDate(rs.getTimestamp("dueDate"));
				bl.setDateIn(rs.getTimestamp("dateIn"));
				bookLoans.add(bl);
			}
			return bookLoans;
		}

		@Override
		public List<BookLoans> extractDataFirstLevel(ResultSet rs)
				throws Exception {
			
			//create a list
			List<BookLoans> bookLoans = new ArrayList<BookLoans>();
			
			while(rs.next()){
				
				BookLoans bl = new BookLoans();
				bl.setDateOut(rs.getTimestamp("dateOut"));
				bl.setDueDate(rs.getTimestamp("dueDate"));
				bl.setDateIn(rs.getTimestamp("dateIn"));
				
				bookLoans.add(bl);
			}
			return bookLoans;
		}

}
