package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.BookLoans;

public class BookCopiesDAO extends BaseDAO<BookCopies> implements ResultSetExtractor<List<BookCopies>>{

	//CREATE
	public void create(BookCopies copies) throws Exception {
		template.update("insert into tbl_book_copies (bookId, branchId, noOfCopies) values(?,?,?)",
				new Object[] { copies.getBookId(), copies.getBranchId(), copies.getNoOfCopies()});
	}

	//UPDATE
	public void update(BookCopies copies) throws Exception {
		template.update("update tbl_book_copies set noOfCopies = ? where bookId=? and branchId=?",
				new Object[] { copies.getNoOfCopies(), copies.getBookId(), copies.getBranchId()});

	}

	//DELETE
	public void delete(BookCopies copy) throws Exception {
		template.update("delete from tbl_book_copies where bookId=? and branchId=?",
				new Object[] { copy.getBookId(), copy.getBranchId()});
	}

	//SELECT ALL
	public List<BookCopies> readAll() throws Exception{
		return template.query("select * from tbl_book_copies", this);

	}

	//SELECT ONE based on ID
	public BookCopies readOne(int bookId, int branchId) throws Exception {
		List<BookCopies> noOfCopies = (List<BookCopies>) template.query("select * from tbl_book_copies", new Object[] {bookId, branchId}, this);
		if(noOfCopies!=null && noOfCopies.size()>0){
			return noOfCopies.get(0);
		}
		return null;
	}

	@Override
	public List<BookCopies> extractData(ResultSet rs) throws SQLException {

		//create list
		List<BookCopies> noOfCopies = new ArrayList<BookCopies>();
		while(rs.next()){
			BookCopies copy = new BookCopies();
			copy.setBookId(rs.getInt("bookId"));
			copy.setBranchId(rs.getInt("branchId"));
			copy.setNoOfCopies(rs.getInt("noOfCopies"));

			noOfCopies.add(copy);
		}
		return noOfCopies;
	}

	//update noOfCopies
	public BookCopies updateNoOfCopies(BookLoans bl){		
		BookCopies bc = new BookCopies();
		bc.setBookId(bl.getBook().getBookId());
		bc.setBranchId(bl.getBranch().getBranchId());
		return bc;
	}

}
