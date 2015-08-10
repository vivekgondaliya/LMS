package com.gcit.lms.domain;

import java.util.List;
import java.util.HashMap;

public class LibraryBranch {
		
	private int branchId;
	private String branchName;
	private String branchAddress;
//	//store book and number of copies
//	private HashMap<Book, Integer> noOfCopies;
//	//list of book loaned from the branch
//	private List<BookLoans> loans;
	
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
//	public HashMap<Book, Integer> getBookOfCopies() {
//		return noOfCopies;
//	}
//	public void setBookOfCopies(HashMap<Book, Integer> bookOfCopies) {
//		this.noOfCopies = bookOfCopies;
//	}
//	public List<BookLoans> getLoans() {
//		return loans;
//	}
//	public void setLoans(List<BookLoans> loans) {
//		this.loans = loans;
//	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBranchAddress() {
		return branchAddress;
	}
	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

}
