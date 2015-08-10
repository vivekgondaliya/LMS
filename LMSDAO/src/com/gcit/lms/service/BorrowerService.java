package com.gcit.lms.service;

import java.sql.Connection;
import java.util.List;

import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.BookLoans;
import com.gcit.lms.domain.LibraryBranch;

public class BorrowerService {

	//list branches
	public List<LibraryBranch> readAllLibraryBranchs() throws Exception {
		Connection conn = ConnectionUtil.createConnection();

		try {
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			List<LibraryBranch> allLibraryBranches = lbdao.readAll();
			conn.commit();
			return allLibraryBranches;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return null;
		} finally {
			conn.close();
		}
	}

	//list books to checkout(numOfCopies>0)
	public List<Book> readAllBooks() throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		BookDAO bDAO = new BookDAO(conn);
		LibraryBranch lb = new LibraryBranch();
		
		try {
			//write queries ONLY in DAOs
			String selectQuery = "SELECT * FROM tbl_book JOIN tbl_book_copies ON tbl_book.bookId=tbl_book_copies.bookId WHERE branchId=?  AND noOfCopies>0";
			List<Book> allBooks = (List<Book>) bDAO.read(selectQuery, new Object[]{lb.getBranchId()});
			conn.commit();
			
			return allBooks;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return null;
		} finally {
			conn.close();
		}
	}

	//checkout book
	public void checkoutBook(BookLoans bl) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		try {
			if (bl == null || bl.getDateIn() == null){
				throw new Exception(
						"DateIn cannot be empty.");
			} 
			else if (bl == null || bl.getDueDate() == null){
				throw new Exception(
						"Due date cannot be empty."); 
			}
			else if (bl == null || bl.getDateOut() == null){
				throw new Exception(
						"Checkout date cannot be empty."); 
			}
			else {
				BookLoansDAO bDAO = new BookLoansDAO(conn);
				bDAO.create(bl);
				
				//update noOfCopies
				BookCopiesDAO bcDAO = new BookCopiesDAO(conn);
				BookCopies bc = bcDAO.updateNoOfCopies(bl);
				bc.setNoOfCopies(bc.getNoOfCopies()-1);
				bcDAO.update(bc);
				
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}


	//return/Update book
	public void returnBook(BookLoans bl) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		try {
			if (bl == null || bl.getDateIn() == null){
				throw new Exception(
						"DateIn cannot be empty.");
			} 
			else if (bl == null || bl.getDueDate() == null){
				throw new Exception(
						"Due date cannot be empty."); 
			}
			else if (bl == null || bl.getDateOut() == null){
				throw new Exception(
						"Checkout date cannot be empty."); 
			}
			else {
				BookLoansDAO bDAO = new BookLoansDAO(conn);
				bDAO.update(bl);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

}
