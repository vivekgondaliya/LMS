package com.gcit.lms.domain;

import java.sql.Timestamp;

public class BookLoans {

	private Borrower borrow;
	private Book book;
	private LibraryBranch branch;
	private Timestamp dateOut;
	private Timestamp dueDate;
	private Timestamp dateIn;
	
	public Borrower getBorrow() {
		return borrow;
	}
	public void setBorrow(Borrower borrow) {
		this.borrow = borrow;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public LibraryBranch getBranch() {
		return branch;
	}
	public void setBranch(LibraryBranch branch) {
		this.branch = branch;
	}
	
	public Timestamp getDateOut() {
		return dateOut;
	}
	public void setDateOut(Timestamp dateOut) {
		this.dateOut = dateOut;
	}
	public Timestamp getDueDate() {
		return dueDate;
	}
	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}
	public Timestamp getDateIn() {
		return dateIn;
	}
	public void setDateIn(Timestamp dateIn) {
		this.dateIn = dateIn;
	}
	
	
	
}
