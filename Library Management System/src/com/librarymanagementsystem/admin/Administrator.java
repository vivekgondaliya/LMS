package com.librarymanagementsystem.admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Administrator {

	private Connection conn;
	private Scanner sc;

	public Administrator(){
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "1234");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//display Admin Functions(Add more functionality or DELETE the function)
	public void displayAdminMenu(){
		
		//admin function selection
		adminFunctionChoice();
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//admin function choice
	private void adminFunctionChoice(){
		sc = new Scanner(System.in);
		int adminFunctionChoice = 0;
		boolean goBack = false;

		do{

			//print menu
			printAdminMenu();

			System.out.println();
			System.out.print("Enter Admin Function Choice: ");
			adminFunctionChoice = sc.nextInt(); sc.nextLine();

			switch (adminFunctionChoice) {
			case 1:
				bookFunctionSelection();
				break;
			case 2:
				break;
			case 3:
				publisherFunctionSelection();
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				goBack = true;
				break;
			default:
				System.out.println("Enter a valid choice.");
				break;
			}

		}while(!goBack);
	}

	//print Admin menu
	private void printAdminMenu(){

		System.out.println();
		System.out.println("1) Add/Update/Delete Book");
		System.out.println("2) Add/Update/Delete Author");
		System.out.println("3) Add/Update/Delete Publisher (AUTO-INCREMENT and Display Numbers)");
		System.out.println("4) Add/Update/Delete Library Branches");
		System.out.println("5) Add/Update/Delete Borrower");
		System.out.println("6) Over-ride due date for Book Loan");
		System.out.println("7) Go Back");
		System.out.println("To Do:");
		System.out.println("Finally: close statement and conn");
		System.out.println("Connection Class: Open and close connection instantialization.");

	}

	//display book functions
	private void bookMenu(){

		System.out.println();
		System.out.println("Administrator: Book Options:");
		System.out.println("1) Add Book");
		System.out.println("2) Update Book");
		System.out.println("3) Delete Book");
		System.out.println("4) Go Back");

	}

	//book function selection
	private void bookFunctionSelection(){

		int publisherFunctionChoice = 0;
		boolean goBack = false;

		do{
			//print publisher Menu
			bookMenu();

			System.out.println();
			System.out.print("Enter Book Function Choice: ");
			publisherFunctionChoice = sc.nextInt(); sc.nextLine();

			switch (publisherFunctionChoice) {
			case 1:
				addBook();
				break;
			case 2:
				updateBook();
				break;
			case 3:
				deleteBook();
				break;
			case 4:
				goBack = true;
				break;
			default:
				System.out.println("Please, enter valid choice.");
				break;
			}
		}while(!goBack);

	}

	//add book function calls
	private void addBook(){

		//get list of current Books
		printBooksList();

		//call to add book Menu
		addBookMenu();
	}

	//ADD BOOK menu
	private void addBookMenu(){

		boolean goBack = false;
		int addBookDetails = 0;
		do{
			System.out.println();
			System.out.println("ADD Book:");
			System.out.println("1) Add New Book Details");
			System.out.println("2) Go Back");
			System.out.print("Enter Choice: ");
			addBookDetails = sc.nextInt(); sc.nextLine();

			switch (addBookDetails) {
			case 1:
				getAddBookInput();
				goBack = true;
				break;
			case 2:
				goBack = true;
				break;
			default:
				System.out.println();
				System.out.println("Enter a valid choice");
				break;
			}

		}while(!goBack);
	}

	//ask user for NEW BOOK info
	private void getAddBookInput() {

		System.out.println();
		System.out.print("Enter New Book Name: ");
		String title = sc.nextLine();

		//list of authors
		printAuthorList();

		System.out.println("Select Author: ");
		int authorId = sc.nextInt();sc.nextLine();


		//list of genre
		printGenreList();

		System.out.println("Select Genre: ");
		int genreId = sc.nextInt(); sc.nextLine();

		//list of publisher
		printPublisherList();

		System.out.print("Enter Publisher: ");
		int publisherId = sc.nextInt();sc.nextLine();

		//add to database
		addBookDB(title, publisherId, authorId, genreId);
	}

	//get count of current books in DB
	private int getCountBookID(){

		String selectQuery = "SELECT COUNT(bookId) as CountBookId FROM tbl_book";
		int countBookId = 0;
		try {
			PreparedStatement stmt = conn.prepareStatement(selectQuery);
			ResultSet rs = stmt.executeQuery();

			while(rs.next()){
				countBookId = rs.getInt("CountBookId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return countBookId;
	}

	//add new book to DB
	private void addBookDB(String title, int publisherId, int authorId, int genreId){

		String insertQuery = "INSERT INTO tbl_book (title, pubId)  VALUES(?,?)";
		try {
			PreparedStatement stmt = conn.prepareStatement(insertQuery);
			stmt.setString(1, title);
			stmt.setInt(2, publisherId);
			stmt.executeUpdate();

			System.out.println();
			System.out.println("\"" + title + "\"" + " added to our collection of books.");

			//get bookId for the title to update tbl_book_authors
			int bookId = getBookId(title);

			//update tbl_book_authors
			insertQuery = "INSERT INTO tbl_book_authors VALUES(?,?)";
			stmt = conn.prepareStatement(insertQuery);
			stmt.setInt(1,bookId);
			stmt.setInt(2,authorId);
			stmt.executeUpdate();
			System.out.println("\"tbl_book_genres\" UPDATED.");

			//update tbl_book_genres
			insertQuery = "INSERT INTO tbl_book_genres VALUES(?,?)";
			stmt = conn.prepareStatement(insertQuery);
			stmt.setInt(1, genreId);
			stmt.setInt(2, bookId);
			stmt.executeUpdate();
			System.out.println("\"tbl_book_authors\" UPDATED.");

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				System.err.print("Transaction is being rolled back");
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

	//returns bookId from tbl_book using "title"
	private int getBookId(String title) throws SQLException {
		PreparedStatement stmt;
		String selectQuery = "SELECT bookId FROM tbl_book WHERE title=?";
		stmt = conn.prepareStatement(selectQuery);
		stmt.setString(1, title);
		ResultSet rs = stmt.executeQuery();
		int bookId = 0;
		while(rs.next()){
			bookId = rs.getInt("bookId");
		}
		return bookId;
	}

	//update book function calls
	private void updateBook(){

		//call update BOOK Menu
		updateBookMenu(); 

	}

	//UPDATE BOOK menu
	private void updateBookMenu(){
		boolean goBack = false;
		int updateBookDetails = 0;

		do{
			//get list of current books
			printBooksList();

			System.out.println();
			System.out.print("Select Book To Update Details(or 0 to go back) [Missing Validation]: ");
			updateBookDetails = sc.nextInt(); sc.nextLine();

			if(updateBookDetails > 0){
				//pass bookId to update
				getUpdateBookInput(updateBookDetails);
				goBack = true;
			}
			else if(updateBookDetails == 0){
				goBack = true;
			}
			else{
				System.out.println();
				System.out.println("Enter a Valid Choice.");
			}
		}while(!goBack);
	}

	//ask user for UPDATE BOOK info
	private void getUpdateBookInput(int bookId){

		System.out.println();

		//update author
		printAuthorList();
		System.out.println("Select Book Author: ");
		int updateBookAuthor = sc.nextInt();sc.nextLine();

		//update genre
		printGenreList();
		System.out.println("Select Book Genre: ");
		int updateBookGenre = sc.nextInt();sc.nextLine();

		//update publisher
		printPublisherList();
		System.out.print("Select Publisher to Update: ");
		int updateBookPublisher = sc.nextInt();sc.nextLine();

		//add to database
		updateBookDB(bookId, updateBookAuthor, updateBookGenre, updateBookPublisher);
	}

	//add UPDATED BOOK info to DB
	private void updateBookDB(int bookID, int updateBookAuthor, int updateBookGenre, int updateBookPublisher){

		try {
			//update tbl_book
			String updateQuery = "UPDATE tbl_book SET pubId=? WHERE bookId=?";
			PreparedStatement stmt = conn.prepareStatement(updateQuery);
			stmt.setInt(1, updateBookPublisher);
			stmt.setInt(2, bookID);
			stmt.executeUpdate();

			//update tbl_book_authors
			updateQuery = "UPDATE tbl_book_authors SET authorId=? WHERE bookId=?";
			stmt = conn.prepareStatement(updateQuery);
			stmt.setInt(1, updateBookAuthor);
			stmt.setInt(2, bookID);
			stmt.executeUpdate();

			//update tbl_book_genres
			updateQuery = "UPDATE tbl_book_genres SET genre_id=? WHERE bookId=?";
			stmt = conn.prepareStatement(updateQuery);
			stmt.setInt(1,updateBookGenre);
			stmt.setInt(2, bookID);
			stmt.executeUpdate();
			
			//get title for confirmation
			String selectQuery = "SELECT title FROM tbl_book WHERE bookId=?";
			String title = null;
			stmt = conn.prepareStatement(selectQuery);
			stmt.setInt(1,bookID);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				title = rs.getString("title");
			}
			//Confirmation Message
			System.out.println();
			System.out.println(title + ": UPDATED.");
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				System.err.println("Transaction is being rolled back.");
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	//DELETE BOOK
	private void deleteBook(){

		boolean goBack = false;
		do{
			//get List of Books
			printBooksList();
			//take user input
			System.out.println();
			System.out.println("Select Book to Delete(or 0 to go back): ");
			int deleteBookChoice = sc.nextInt(); sc.nextLine();

			//use rs.isBeforeFirst()
			if(deleteBookChoice > 0){
				deleteBookDB(deleteBookChoice);
				goBack = true;
			}
			else if(deleteBookChoice == 0){
				goBack = true;
			}
			else{
				System.out.println();
				System.out.println("Enter valid choice.");
			}
		}while(!goBack);
	}

	//delete book from DB
	private void deleteBookDB(int bookId){

		String deleteQuery = "DELETE FROM tbl_book WHERE bookId=?";
		String selectQuery = "SELECT title FROM tbl_book WHERE bookId=?";
		String title = null;
		try {
			//get title to display confirmation message
			PreparedStatement stmt = conn.prepareStatement(selectQuery);
			stmt.setInt(1, bookId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				title = rs.getString("title");
			}

			//delete the book from DB
			stmt = conn.prepareStatement(deleteQuery);
			stmt.setInt(1, bookId);
			stmt.executeUpdate();

			System.out.println();
			System.out.println(title + ": DELETED.");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//display publisher functions
	private void publisherMenu(){

		System.out.println();
		System.out.println("1) Add Publisher");
		System.out.println("2) Update Publisher");
		System.out.println("3) Delete Publisher");
		System.out.println("4) Go Back");

	}

	//publisher function selection
	private void publisherFunctionSelection(){

		int publisherFunctionChoice = 0;
		boolean goBack = false;

		do{
			//print publisher Menu
			publisherMenu();

			System.out.println();
			System.out.print("Enter Publisher Function Choice: ");
			publisherFunctionChoice = sc.nextInt(); sc.nextLine();

			switch (publisherFunctionChoice) {
			case 1:
				addPublisher();
				break;
			case 2:
				updatePublisher();
				break;
			case 3:
				deletePublisher();
				break;
			case 4:
				goBack = true;
				break;
			default:
				System.out.println("Please, enter valid choice.");
				break;
			}
		}while(!goBack);

	}

	//add Publisher
	private void addPublisher(){

		//print list of publisher
		printPublisherList();

		boolean goBack = false;
		int addPublisherDetails = 0;
		do{
			System.out.println();
			System.out.println("ADD Publisher:");
			System.out.println("1) Add Publisher Details");
			System.out.println("2) Go Back");
			System.out.print("Enter Choice: ");
			addPublisherDetails = sc.nextInt(); sc.nextLine();

			switch (addPublisherDetails) {
			case 1:
				getPublisherInput();
				break;
			case 2:
				goBack = true;
				break;
			default:
				System.out.println();
				System.out.println("Enter a valid choice");
				break;
			}

		}while(!goBack);
	}

	//ask user for NEW Publisher info
	private void getPublisherInput() {

		System.out.println();
		System.out.print("Enter Publisher Name: ");
		String publisherName = sc.nextLine();
		System.out.print("Enter Publisher Address: ");
		String publisherAddress = sc.nextLine();
		System.out.print("Enter Publisher Phone: ");
		String publisherPhone = sc.nextLine();

		//add to database
		addPublisherDB(publisherName, publisherAddress, publisherPhone);
	}

	//count of publisherId
	private int getCountPublisherID(){

		String selectQuery = "SELECT COUNT(publisherId) as CountPublisherId FROM tbl_publisher";
		int countPublisherId = 0;
		try {
			PreparedStatement stmt = conn.prepareStatement(selectQuery);
			ResultSet rs = stmt.executeQuery();

			while(rs.next()){
				countPublisherId = rs.getInt("CountPublisherId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return countPublisherId;
	}

	//add publisher to DB
	private void addPublisherDB(String publisherName, String publisherAddress, String publishPhone){


		String insertQuery = " INSERT INTO tbl_publisher(publisherName, publisherAddress, publisherPhone)VALUES(?,?,?)";
		try {
			PreparedStatement stmt = conn.prepareStatement(insertQuery);
			stmt.setString(1, publisherName);
			stmt.setString(2, publisherAddress);
			stmt.setString(3, publishPhone);
			stmt.executeUpdate();
			
			System.out.println();
			System.out.println("New Publisher: "+ publisherName+ " ADDED.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	//update publisher
	private void updatePublisher(){

		//print current publisher list
		printPublisherList();

		boolean goBack = false;
		int updatePublisherChoice= 0;

		do{
			System.out.println();
			System.out.print("Enter the publisher you would like to update(or 0 to go back): ");
			updatePublisherChoice = sc.nextInt();sc.nextLine();

			if(updatePublisherChoice > getCountOfPublisher() && updatePublisherChoice < 0){
				System.out.println("Enter a valid choice.");
			}
			else if(updatePublisherChoice == 0){
				goBack = true;
			}
			else{
				System.out.println();
				System.out.print("Enter Publisher Name: ");
				String publisherName = sc.nextLine();
				System.out.print("Enter Publisher Address: ");
				String publisherAddress = sc.nextLine();
				System.out.print("Enter Publisher Phone: ");
				int publisherPhone = sc.nextInt();sc.nextLine();

				String updateQuery = "UPDATE tbl_publisher SET publisherName=?, publisherAddress=?, publisherPhone=? WHERE publisherId=? ";
				try {
					PreparedStatement stmt = conn.prepareStatement(updateQuery);
					stmt.setString(1, publisherName);
					stmt.setString(2, publisherAddress);
					stmt.setInt(3, publisherPhone);
					stmt.setInt(4, updatePublisherChoice);
					stmt.executeUpdate();
					System.out.println();
					System.out.println("UPDATED Publisher: " + publisherName);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}while(!goBack);
	}

	//delete publisher
	private void deletePublisher(){

		//print list of publisher
		printPublisherList();

		//perform deletion based on user selection
		deleteFromPublisherDB();
	}

	//delete from publisher database
	private void deleteFromPublisherDB(){

		int deletePublisherChoice = 0;
		PreparedStatement stmt;
		
		boolean goBack = false;
		do{
			System.out.println();
			System.out.print("Enter Publisher to delete(or 0 to go back)[Validation Not Implemented]: ");
			deletePublisherChoice = sc.nextInt();sc.nextLine();

			if(deletePublisherChoice < 0){
				System.out.println("Enter Valid Choice.");
			}
			else if(deletePublisherChoice == 0){
				goBack = true;
			}
			else{
				String deleteQuery = "DELETE FROM tbl_publisher WHERE publisherId =?";
				try {
					//avoid auto commit
					conn.setAutoCommit(false);
					
					stmt = conn.prepareStatement(deleteQuery);
					stmt.setInt(1, deletePublisherChoice);
					stmt.executeUpdate();
					
					//manual commit
					conn.commit();
					
					System.out.println();
					System.out.println("Publisher: DELETED.");

				} catch (SQLException e) {
					e.printStackTrace();
					
					try {
						System.err.println("Transaction is being rolled back.");
						conn.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					finally{
						//close statements and connection
						//stmt.close();
					}
				}

			}
		}while(!goBack);
	}

	//print list of current books
	private void printBooksList(){

		String selectQuery ="SELECT bookId, title FROM tbl_book";
		try {
			PreparedStatement stmt = conn.prepareStatement(selectQuery);
			ResultSet rs = stmt.executeQuery();

			System.out.println();
			System.out.println("Books in Our Collection: ");

			while(rs.next()){
				System.out.println( "{" + rs.getInt("bookId") + "} - " + rs.getString("title"));	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//print author list
	private void printAuthorList(){
		String selectQuery = "SELECT authorId, authorName FROM tbl_author";
		try {
			PreparedStatement stmt = conn.prepareStatement(selectQuery);
			ResultSet rs = stmt.executeQuery();

			System.out.println();
			System.out.println("List of Authors: ");
			while(rs.next()){
				System.out.println("{" +rs.getInt("authorId") + "} - " + rs.getString("authorName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//print genre list
	private void printGenreList(){

		String selectQuery ="SELECT genre_id, genre_name FROM tbl_genre";
		try {
			PreparedStatement stmt = conn.prepareStatement(selectQuery);
			ResultSet rs = stmt.executeQuery();

			System.out.println();
			System.out.println("List of Genres: ");
			while(rs.next()){
				System.out.println("{" +rs.getInt("genre_id") + "} - " + rs.getString("genre_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	//print publisher list
	private void printPublisherList(){

		String selectQuery = "SELECT publisherId, publisherName from tbl_publisher";
		try {
			PreparedStatement stmt = conn.prepareStatement(selectQuery);
			ResultSet rs = stmt.executeQuery();

			System.out.println();
			System.out.println("List of Publishers: ");
			while(rs.next()){
				System.out.println("{" +rs.getInt("publisherId") + "} - " + rs.getString("publisherName"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//count total publisher
	private int getCountOfPublisher() {

		int countOfPublisher = 0;

		String selectQuery = "SELECT COUNT(publisherId) as countPublisherId FROM tbl_publisher";
		try {
			PreparedStatement stmt = conn.prepareStatement(selectQuery);
			ResultSet rs = stmt.executeQuery();

			while(rs.next()){
				countOfPublisher = rs.getInt("countPublisherId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return countOfPublisher;
	}
}
