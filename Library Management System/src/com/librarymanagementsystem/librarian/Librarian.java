package com.librarymanagementsystem.librarian;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Librarian {

	private Connection conn; 
	private Scanner sc = new Scanner(System.in);
	
	public Librarian(){
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "1234");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//Library Main Menu
	public void libraryMenu() {
		boolean goBack = false;
		do {
			System.out.println();
			System.out.println("1) Select your branch.");
			System.out.println("2) Go Back");
			System.out.print("Enter Choice: ");
			int libraryMenuChoice = sc.nextInt();

			switch (libraryMenuChoice) {
			case 1:
				libraryBranchs();
				break;
			case 2:
				goBack = true;
				break;
			default:
				System.out.println("Enter a valid choice.");
				System.out.println();
				break;
			}
		} while (!goBack);
	}

	//List of Library Branches
	private void libraryBranchs() {
		try{

			ArrayList<String> branchNames = new ArrayList<String>();

			//get arrayList of Branch Names
			branchNames = getLibraryBranchList();

			System.out.println();

			boolean goBack = false;
			do {
				//Display Branch Names for selection
				printLibraryBranchList(branchNames);

				//Library Branch User Selection
				System.out.print("Enter Library Branch Choice: ");
				int libraryBranchChoice = sc.nextInt();

				if( libraryBranchChoice > 0 && libraryBranchChoice < branchNames.size() + 1){
					goBack = updateLibrary(libraryBranchChoice);

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


	//return an arrayList of Library Branches
	public ArrayList<String> getLibraryBranchList(){

		ArrayList<String> branchNames = new ArrayList<String>();

		try {
			//using Statement to select data
			Statement stmt = conn.createStatement();
			String selectQuery = "SELECT branchId, branchName FROM tbl_library_branch";
			ResultSet rs = stmt.executeQuery(selectQuery);

			//Store the list of branch in Arraylist
			while(rs.next()){
				branchNames.add(rs.getString("branchName"));
			}

		}catch (SQLException e) {
			e.printStackTrace();
		}
		return branchNames;
	}

	//print List of Library Branches
	public void printLibraryBranchList(ArrayList<String> branchNames){

		System.out.println("Branch List:");
		for(int i = 0; i<branchNames.size(); i++){
			System.out.println( (i+1) +") " +branchNames.get(i));
		}
		System.out.println((branchNames.size()+1) + ") Go Back");
	}

	// Update Library Info Menu
	private boolean updateLibrary(int branchId) {
		int updateLibraryChoice = 0;
		//boolean goBack = false;

		do {
			System.out.println();
			System.out.println("1) Update the details of the library.");
			System.out.println("2) Add Copies of the book to the branch.");
			System.out.println("3) Go Back to Select Different Branch.");
			System.out.print("Enter Update Library Choice: ");
			updateLibraryChoice = sc .nextInt();
			sc.nextLine();

			switch (updateLibraryChoice) {
			case 1:
				updateBranch(branchId);
				break;
			case 2:
				addCopies(branchId);
				break;
			case 3:
				System.out.println();
				return false;
				// break;
			default:
				System.out.println("Enter valid choice.");
				System.out.println();
				break;
			}
		} while (updateLibraryChoice != 3);
		return false;
	}

	//update Existing branch Info
	private void updateBranch(int branchId) {
		try {
			//display Chosen Branch
			displayChosenBranch(branchId);

			//display Update Option
			displayUpdateOptions(branchId);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//display chosen branch
	private void displayChosenBranch(int branchId){
		try {
			String selectQuery = "SELECT branchId, branchName FROM tbl_library_branch where branchId = ?";
			//Prepared statement to avoid injection
			PreparedStatement stmt = conn.prepareStatement(selectQuery);
			//data binding
			stmt.setInt(1, branchId);
			ResultSet rs = stmt.executeQuery();

			while(rs.next()){
				System.out.println();
				System.out.println("You've chosen the branch with BRANCH ID: " + rs.getInt("branchId") + " and BRANCH NAME: " + rs.getString("branchName"));	
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	//
	private void displayUpdateOptions(int branchId){

		boolean goBack = false;
		do{
			System.out.println();
			System.out.println("1) To Update Information.");
			System.out.println("2) To Go Back");
			System.out.print("Enter your choice: ");
			int updateBranchChoice = sc.nextInt();
			sc.nextLine();

			//TODO update branchName and branchAddress()
			//updateBranchInfo(branchId, updateBranchChoice);

			switch (updateBranchChoice) {
			case 1:
				System.out.println();
				System.out.print("Please enter a new Branch Name");
				String newBranchName = sc.nextLine();
				System.out.print("Please enter a new Branch Address");
				String newBranchAddress = sc.nextLine();
				//Prepared Statement to UPDATE
				String updateQuery = "UPDATE tbl_library_branch SET branchName =?, branchAddress =? WHERE branchId =?";
				PreparedStatement stmt;
				try {
					stmt = conn.prepareStatement(updateQuery);
					//Data Binding
					stmt.setString(1, newBranchName);
					stmt.setString(2, newBranchAddress);
					stmt.setInt(3, branchId);
					stmt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				goBack = true;
				break;
			case 2:
				goBack = true;
				break;
			default:
				System.out.println("Enter a valid choice.");
				System.out.println();
				break;
			}
		}while(!goBack);

	}

	//update branch name and address
	private void updateBranchInfo(int branchId, int updateBranchChoice){

	}

	//add copies to existing books
	private void addCopies(int branchId) {
		ArrayList<String> bookNames = new ArrayList<String>();

		System.out.println();

		try {
			//get list of books in given branchId
			bookNames = getListOfBooks(branchId);

			boolean goBack = false;
			do{
				System.out.print("Select Book to add Copies to your branch:");
				int bookSelect = sc.nextInt();
				if( bookSelect > 0 && bookSelect < bookNames.size() + 1){
					goBack = changeCopies(bookSelect, bookNames);

				}
				else if (bookSelect == bookNames.size() + 1){
					goBack = true;
				}
				else {
					System.out.println("Enter Valid Book Choice.");
					System.out.println();
				}
			}while(!goBack);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//return list of books wrt branchId
	private ArrayList<String> getListOfBooks(int branchId){

		ArrayList<String> bookNames = new ArrayList<String>();

		String selectQuery = "SELECT title FROM tbl_book JOIN tbl_book_copies ON tbl_book.bookId = tbl_book_copies.bookId WHERE branchId =?";
		//Prepared Statement
		PreparedStatement stmt;
		try {

			stmt = conn.prepareStatement(selectQuery);
			//data binding
			stmt.setInt(1, branchId);
			//Store the return result set
			ResultSet rs = stmt.executeQuery();

			//Populate the arrayList
			while(rs.next()){
				bookNames.add(rs.getString("title"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//Display Book Names for selection
		System.out.println();
		printListOfBooks(bookNames);

		return bookNames;
	}

	//print list of books
	private void printListOfBooks(ArrayList<String> bookNames){
		for(int i = 0; i<bookNames.size(); i++){
			System.out.println( (i+1) +") " +bookNames.get(i));
			if((i+1) == bookNames.size()){
				System.out.println((i+2) + ") Go Back");
			}
		}
	}

	//Change Number of Copies
	private boolean changeCopies(int bookSelect, ArrayList<String> bookNames) {

		//get the title for query, subtract 1 for correct Index
		String title = bookNames.get(bookSelect-1);

		try {
			//print existing number of copies
			printExistingCopies(title);

			boolean goBack = false;
			int newNoOfCopies = 0;
			do{
				System.out.print("Enter new number of copies: ");
				if(sc.hasNextInt()){
					newNoOfCopies = sc.nextInt();
					sc.nextLine();

					//update new number of copies
					updateNewCopies(title, newNoOfCopies);
					goBack = true;
				}
				else{
					System.out.println("Integer Value Only.");
					System.out.println();
				}
			}while(!goBack);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	//print existing number of copies
	private void printExistingCopies(String title){

		String selectQuery = "SELECT noOfCopies FROM tbl_book_copies JOIN tbl_book ON tbl_book_copies.bookId = tbl_book.bookId WHERE title =?";
		//Prepared Statement
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(selectQuery);
			//data binding
			stmt.setString(1, title);
			//Store the return result set
			ResultSet rs = stmt.executeQuery();

			while(rs.next()){
				System.out.println();
				System.out.println("Existing Number of Copies: " + rs.getInt("noOfCopies"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//update new copies
	private void updateNewCopies(String title, int newNoOfCopies){

		String updateQuery = "UPDATE tbl_book_copies JOIN tbl_book ON tbl_book_copies.bookId = tbl_book.bookId AND tbl_book.title =? SET tbl_book_copies.noOfCopies =?";
		PreparedStatement stmt;
		try {

			stmt = conn.prepareStatement(updateQuery);
			//Data Binding
			stmt.setString(1, title);
			stmt.setInt(2, newNoOfCopies);
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
