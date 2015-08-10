package com.gcit.lms.service;

import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.BookLoans;
import com.gcit.lms.domain.LibraryBranch;

public class BorrowerService {

	@Autowired
	BasicDataSource ds;

	//DAOs
	@Autowired
	BookDAO bookDAO;
	@Autowired
	LibraryBranchDAO branchDAO;
	@Autowired
	BookLoansDAO bookLoanDAO;
	@Autowired
	BookCopiesDAO bookCopiesDAO;



	//list branches
	public List<LibraryBranch> readAllLibraryBranchs() throws Exception {
		List<LibraryBranch> allLibraryBranches = branchDAO.readAll();
		return allLibraryBranches;
	}

	//list books to checkout(numOfCopies>0)
	public List<Book> readAllBooks(LibraryBranch branch) throws Exception {
		List<Book> allBooks = bookDAO.numOfCopiesBranch(branch.getBranchId());	
		return allBooks;
	}

	//checkout book
	public void checkoutBook(BookLoans bl) throws Exception {
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
				bookLoanDAO.create(bl);

				//update noOfCopies
				BookCopies bc = bookCopiesDAO.updateNoOfCopies(bl);
				bc.setNoOfCopies(bc.getNoOfCopies()-1);
				bookCopiesDAO.update(bc);
			}
	}


	//return/Update book
	public void returnBook(BookLoans bl) throws Exception {
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
				bookLoanDAO.update(bl);
		} 
	}

}
