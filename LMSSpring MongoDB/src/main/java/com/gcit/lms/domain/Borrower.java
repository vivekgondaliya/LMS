package com.gcit.lms.domain;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Borrower {
	@Id
	private UUID cardNo = UUID.randomUUID();
	private String borroweName;
	private String borrowerAddress;
	private String borrowerPhone;
	
//	private List<BookLoans> bookLoans; 
//	
//	public List<BookLoans> getBookLoans() {
//		return bookLoans;
//	}
//	public void setBookLoans(List<BookLoans> bookLoans) {
//		this.bookLoans = bookLoans;
//	}
	public UUID getCardNo() {
		return cardNo;
	}
	public void setCardNo(UUID cardNo) {
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
