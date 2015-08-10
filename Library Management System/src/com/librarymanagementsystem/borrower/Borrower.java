package com.librarymanagementsystem.borrower;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.librarymanagementsystem.librarian.Librarian;

public class Borrower {

	private int cardNumber;
	private String Name;
	private int phone;
	private String address;

	private Connection conn;
	private Scanner sc = new Scanner(System.in);

	public Borrower(){
		this.cardNumber = 000;
		this.Name = "Guest";
		this.phone = 0;
		this.address = "Guest";
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "1234");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	//get cardNumber for Borrower,
	public void cardNumber(){

		try{
			boolean backToCall = false;
			do{
				System.out.println();
				System.out.print("Enter your Card Number: ");

				//TODO APPLY SIZE RESTRICTION(11)
				if(cardNumber == (int) cardNumber){
					cardNumber =sc.nextInt();
					sc.nextLine();

					try {

						String selectQuery = "SELECT COUNT(cardNo) as valid FROM tbl_borrower WHERE cardNo = ?";

						PreparedStatement stmt = conn.prepareStatement(selectQuery);
						stmt.setInt(1,cardNumber);
						ResultSet rs = stmt.executeQuery();

						if(rs.next()){
							if(rs.getInt("valid") > 0){
								printBorrowMenu(cardNumber);
								backToCall = true;
							}
							else{
								System.out.println();
								System.out.println("Card Number doesn't exist in the system.");
								backToCall = false;
							}
						}else{
							System.out.println();
							System.out.println("Error: No return result.");
							backToCall = true;
						}


					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			}while(!backToCall);
		}
		catch(InputMismatchException i){
			System.out.println();
			System.out.println("Please, use ONLY INTEGER Values for Card Number. Back to Main Menu...");
		}
	}

	public int getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
	}

	private void printBorrowMenu(int cNumber) {
		int borrowerChoice = 0;
		boolean goBack = false;
		do{
			System.out.println();
			System.out.println("1) Check out a Book");
			System.out.println("2) Return a Book");
			System.out.println("3) Back to Main");
			System.out.print("Enter Your Borrower Choice: ");
			borrowerChoice = sc.nextInt();
			switch (borrowerChoice) {
			case 1:
				libraryBranchs(borrowerChoice);
				break;
			case 2:
				libraryBranchs(borrowerChoice);
				break;
			case 3:
				goBack = true;
			default:
				System.out.println("Enter Valid Ingteger.");
				break;
			}
		}while(!goBack);
	}

	//List of Library Branches
	private void libraryBranchs(int borrowerOption) {

		try {
			int libraryBranchChoice = 0;
			ArrayList<String> branchNames = new ArrayList<String>();

			//get library list
			Librarian lib = new Librarian();
			branchNames = lib.getLibraryBranchList();

			System.out.println();
			boolean goBack = false;
			do {				
				//print library list
				lib.printLibraryBranchList(branchNames);

				//User Selection
				System.out.print("Enter Library Branch Choice: ");
				libraryBranchChoice = sc.nextInt();sc.nextLine();

				if( libraryBranchChoice > 0 && libraryBranchChoice < branchNames.size() + 1){

					listLibraryBranchBooks(libraryBranchChoice, borrowerOption);
					goBack = true;

				}
				else if (libraryBranchChoice == branchNames.size() + 1){

					goBack = true;
				}
				else {

					System.out.println("Enter Valid Branch Choice.");
				}

			}while(!goBack);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void listLibraryBranchBooks(int libraryBranchChoice , int borrowerOption) {

		ArrayList<String> bookNames = new ArrayList<String>();
		try {
			//get list of bookNames based on Checkout or Return selection 
			bookNames = getListLibraryBranchBooks(libraryBranchChoice, borrowerOption);

			System.out.println();
			System.out.println("Books Under Your Account: ");
			//Display Book Names for selection
			for(int i = 0; i<bookNames.size(); i++){
				System.out.println( (i+1) +") " +bookNames.get(i));
			}
			System.out.println((bookNames.size()+1) + ") Go Back");

			//if checkout then go to insert into tbl_book_loans
			if(borrowerOption == 1){
				System.out.print("Pick the book you want to checkout: ");
				int pickBookChoice = sc.nextInt();sc.nextLine();

				if(pickBookChoice == bookNames.size() + 1){
				}
				else{
					//pick the book
					pickBookBorrower(libraryBranchChoice, bookNames.get(pickBookChoice - 1));
				}
			}
			else
				//if return then go to update tbl_book_loans
				if(borrowerOption == 2){
					System.out.print("Pick the book you want to return: ");
					int returnBookChoice = sc.nextInt();
					sc.nextLine();

					if(returnBookChoice == bookNames.size() + 1){
					}
					else{
						//return the book
						returnBookBorrower(libraryBranchChoice, bookNames.get(returnBookChoice - 1));
					}
				}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//gives you an arrayList with title of the book to checkout or return
	private ArrayList<String> getListLibraryBranchBooks(int libraryBranchChoice, int borrowerOption){

		PreparedStatement stmt = null;
		String selectQuery = null;
		ArrayList<String> bookNames = new ArrayList<String>();

		//Prepared Statement
		try {
			//checkout selected by user, get title of books for selected branchId and no of copies greater than 1
			if(borrowerOption == 1){
				selectQuery = "SELECT title "
						+ "FROM tbl_book "
						+ "JOIN tbl_book_copies "
						+ "ON tbl_book.bookId = tbl_book_copies.bookId "
						+ "WHERE branchId =? and noOfCopies >= 1";

				stmt = conn.prepareStatement(selectQuery);
				//data binding
				stmt.setInt(1, libraryBranchChoice);
			}
			//return selected by user, get title of books for selected branchId with given cardNo
			else if(borrowerOption == 2){
				selectQuery = "SELECT title "
						+ "FROM tbl_book "
						+ "JOIN tbl_book_copies "
						+ "ON tbl_book.bookId = tbl_book_copies.bookId "
						+ "JOIN tbl_book_loans "
						+ "ON tbl_book.bookId = tbl_book_loans.bookId "
						+ "WHERE tbl_book_copies.branchId =? and tbl_book_loans.cardNo = ?";
				//Prepared Statement
				stmt = conn.prepareStatement(selectQuery);
				//data binding
				stmt.setInt(1, libraryBranchChoice);
				stmt.setInt(2, getCardNumber());
			}
			//Store the return result set
			ResultSet rs = stmt.executeQuery();

			//Store the list of books in Arraylist
			while(rs.next()){
				bookNames.add(rs.getString("title"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bookNames;
	}

	private void returnBookBorrower(int libraryBranchChoice, String bookTitle) {
		int bookId = 0;
		try {
			//Get BookId
			String selectQuery = "SELECT bookId from tbl_book WHERE title=?";
			PreparedStatement stmt = conn.prepareStatement(selectQuery);
			stmt.setString(1, bookTitle);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				bookId = rs.getInt("bookId");

				//update dateIn based on bookId of the title and cardNo
				String updateQuery ="UPDATE tbl_book_loans SET dateIn = ? WHERE tbl_book_loans.bookId =? and tbl_book_loans.cardNo =?";
				stmt = conn.prepareStatement(updateQuery);
				stmt.setDate(1, getCurrentDate());
				stmt.setInt(2, bookId);
				stmt.setInt(3, getCardNumber());
				stmt.executeUpdate();

				//Update noOfCopies
				updateQuery = "UPDATE tbl_book_copies SET noOfCopies = (noOfCopies + 1) WHERE tbl_book_copies.bookId = ?";
				stmt = conn.prepareStatement(updateQuery);
				stmt.setInt(1, bookId);
				stmt.executeUpdate();

				System.out.println(bookTitle + " RETURNED. Thanks. Going Back to Branch List...");
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void pickBookBorrower(int libraryBranchChoice, String bookTitle) {

		int bookId = 0;
		try {
			//Get BookId
			String selectQuery = "SELECT bookId from tbl_book WHERE title=?";
			PreparedStatement stmt = conn.prepareStatement(selectQuery);
			stmt.setString(1, bookTitle);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				bookId = rs.getInt("bookId");

			}

			//if book already checked out (Duplication Error)
			selectQuery = "SELECT COUNT(bookId) as alreadyCheckedOut FROM tbl_book_loans WHERE bookId =? and  cardNo=?";
			stmt= conn.prepareStatement(selectQuery);
			stmt.setInt(1, bookId);
			stmt.setInt(2,getCardNumber());
			rs = stmt.executeQuery();
			int alreadyCheckdOut = 0;
			while(rs.next()){
				alreadyCheckdOut = rs.getInt("alreadyCheckedOut");
			}

			if(alreadyCheckdOut > 0){
				System.out.println();
				System.out.println("You've already checked out this book. Don't waste your time reading it again.");
				System.out.println();
			}
			else{
				//			System.out.println("Current Date: " + getCurrentDate());
				//			System.out.println("Due Date: " + getDueDate());
				String insetQuery = "INSERT INTO tbl_book_loans values(?,?,?,?,?,?)";
				stmt = conn.prepareStatement(insetQuery);
				//data binding
				stmt.setInt(1, bookId);
				stmt.setInt(2, libraryBranchChoice);
				stmt.setInt(3, getCardNumber());	
				stmt.setDate(4,getCurrentDate());
				stmt.setDate(5,getDueDate());
				stmt.setDate(6,null);
				//Store the return result set
				stmt.executeUpdate();

				//Update noOfCopies
				String updateQuery = "UPDATE tbl_book_copies SET noOfCopies = (noOfCopies - 1) WHERE tbl_book_copies.bookId = ?";
				stmt = conn.prepareStatement(updateQuery);
				stmt.setInt(1, bookId);
				stmt.executeUpdate();
			}
			System.out.println("Going Back to Branch List...");
			System.out.println();



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	//get Current Date
	private static java.sql.Date getCurrentDate() {
		Date date = new Date();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}

	//get due Date
	private static java.sql.Date getDueDate() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 7);
		date = cal.getTime();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}

}
