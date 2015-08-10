package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.LibraryBranch;

public class LibrarianService {
	//list branches
	public List<LibraryBranch> readAllLibraryBranchs() throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		LibraryBranchDAO lbDAO = new LibraryBranchDAO(conn);

		try {
			List<LibraryBranch> allLibraryBranches = lbDAO.readAll();
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

	//update details of branch
	public void updateLibraryBranch(LibraryBranch lb) throws Exception,
	SQLException {
		Connection conn = ConnectionUtil.createConnection();
		LibraryBranchDAO lbDAO = new LibraryBranchDAO(conn);

		try {
			if(lbDAO.readOne(lb.getBranchId()) == null){
				throw new Exception(
						"Branch you are trying to update does not exist.");
			}else if (lb == null || lb.getBranchName() == null
					|| lb.getBranchName().length() == 0
					|| lb.getBranchName().length() > 45) {
				throw new Exception(
						"Branch Name cannot be empty or more than 45 Chars");
			}else if(lb == null || lb.getBranchAddress() == null
					|| lb.getBranchAddress().length() == 0
					|| lb.getBranchAddress().length() > 45){
				throw new Exception(
						"Branch Address cannot be empty or more than 45 Chars");
			}else {

				lbDAO.update(lb);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	//list books for numberOfCopies update
	public List<Book> readAllBooks() throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		BookDAO bDAO = new BookDAO(conn);

		try {
			List<Book> allBooks = bDAO.readAll();
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


	//update noOfCopies
	public void updateNumberOfCopies(int branchId, int bookId, int numOfCopies) throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		BookCopiesDAO bcDAO = new BookCopiesDAO(conn);
		BookDAO bDAO = new BookDAO(conn);
		LibraryBranchDAO lbDAO = new LibraryBranchDAO(conn);

		try{
			if(bDAO.readOne(bookId) == null){
				throw new Exception("The BookId doesn't exist.");
			}
			if(lbDAO.readOne(branchId) == null){
				throw new Exception("The BranchId doesn't exist.");
			}

			BookCopies bc = new BookCopies();
				bc.setBookId(bookId);
				bc.setBranchId(branchId);
				bc.setNoOfCopies(numOfCopies);
			bcDAO.update(bc);
			
			conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}


}
