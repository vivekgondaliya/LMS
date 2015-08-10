package com.gcit.lms.domain;

import java.util.List;

public class Borrower {

	private int cardNo;
	private String borroweName;
	private String borrowerAddress;
	private String borrowerPhone;
	
	private List<BookLoans> bookLoans; 
	
	public List<BookLoans> getBookLoans() {
		return bookLoans;
	}
	public void setBookLoans(List<BookLoans> bookLoans) {
		this.bookLoans = bookLoans;
	}
	public int getCardNo() {
		return cardNo;
	}
	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}
	public String getName() {
		return borroweName;
	}
	public void setName(String name) {
		this.borroweName = name;
	}
	public String getAddress() {
		return borrowerAddress;
	}
	public void setAddress(String address) {
		this.borrowerAddress = address;
	}
	public String getPhone() {
		return borrowerPhone;
	}
	public void setPhone(String phone) {
		this.borrowerPhone = phone;
	}
	
	
}
