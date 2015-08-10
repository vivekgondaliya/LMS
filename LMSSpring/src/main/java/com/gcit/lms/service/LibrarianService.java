package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.LibraryBranch;

public class LibrarianService {

	@Autowired
	LibraryBranchDAO branchDAO;
	@Autowired
	BookDAO bookDAO;
	@Autowired
	BookCopiesDAO bookCopiesDAO;

	//list branches
	public List<LibraryBranch> readAllLibraryBranchs() throws Exception {
		List<LibraryBranch> allLibraryBranches = branchDAO.readAll();
		return allLibraryBranches;
	} 

	//update details of branch
	public void updateLibraryBranch(LibraryBranch lb) throws Exception,
	SQLException {
		if(branchDAO.readOne(lb.getBranchId()) == null){
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
			branchDAO.update(lb);
		}
	}

	//list books for numberOfCopies update
	public List<Book> readAllBooks() throws Exception {
		List<Book> allBooks = bookDAO.readAll();
		return allBooks;
	}


	//update noOfCopies
	public void updateNumberOfCopies(int branchId, int bookId, int numOfCopies) throws Exception{
		if(bookCopiesDAO.readOne(bookId, branchId) == null){
			throw new Exception("The BookId doesn't exist.");
		}
		if(branchDAO.readOne(branchId) == null){
			throw new Exception("The BranchId doesn't exist.");
		}

		BookCopies bc = new BookCopies();
		bc.setBookId(bookId);
		bc.setBranchId(branchId);
		bc.setNoOfCopies(numOfCopies);
		bookCopiesDAO.update(bc);
	}


}
