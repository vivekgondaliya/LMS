package com.gcit.lms.service;

import java.sql.Connection;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookLoans;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.LibraryBranch;
import com.gcit.lms.domain.Publisher;

public class AdministratorService {

	public void createAuthor(Author author) throws Exception {
		new ConnectionUtil();
		Connection conn = ConnectionUtil.createConnection();
		AuthorDAO aDAO = new AuthorDAO(conn);
		try {
			if (author == null || author.getAuthorName() == null
					|| author.getAuthorName().length() == 0
					|| author.getAuthorName().length() > 45) {
				throw new Exception(
						"Author Name cannot be empty or more than 45 Chars");
			} else {
				
				aDAO.create(author);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void deleteAuthor(Author author) throws Exception{
		new ConnectionUtil();
		Connection conn = ConnectionUtil.createConnection();
		AuthorDAO aDAO = new AuthorDAO(conn);
		try {
			if(author == null){
				throw new Exception("The Author parameter can not be null");
			}else{
				aDAO.delete(author);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}

	public void updateAuthor(Author author) throws Exception{
		new ConnectionUtil();
		Connection conn = ConnectionUtil.createConnection();
		AuthorDAO aDAO = new AuthorDAO(conn);
		try {
			if(aDAO.readOne(author.getAuthorId()) == null){
				throw new Exception(
						"The author you are trying to update does not exist");
			}else{
				aDAO.update(author);
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}

	public List<Author> readAllAuthors() throws Exception {
		new ConnectionUtil();
		Connection conn = ConnectionUtil.createConnection();
		AuthorDAO aDAO = new AuthorDAO(conn);
		try {
			
			List<Author> allAuthors = aDAO.readAll();
			conn.commit();
			return allAuthors;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return null;
		} finally {
			conn.close();
		}
	}

	public Author readOneAuthor(int authorId) throws Exception {
		new ConnectionUtil();
		Connection conn = ConnectionUtil.createConnection();

		try {
			AuthorDAO aDAO = new AuthorDAO(conn);
			Author author = aDAO.readOne(authorId);
			conn.commit();
			return author;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return null;
		} finally {
			conn.close();
		}
	}

	/*
	 * GENRE
	 */

	public void create(Genre gen) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		GenreDAO gDAO = new GenreDAO(conn);

		try {
			if (gen == null || gen.getGenreName() == null
					|| gen.getGenreName().length() == 0
					|| gen.getGenreName().length() > 45) {
				throw new Exception(
						"Genre Name cannot be empty or more than 45 Chars");
			} else {
				gDAO.create(gen);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void deleteGenre(Genre gen) throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		GenreDAO gDAO = new GenreDAO(conn);
		try {
			if(gen == null){
				throw new Exception("The Genre parameter can't be null");
			}else{
				gDAO.delete(gen);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}

	public void updateGenre(Genre gen) throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		GenreDAO gDAO = new GenreDAO(conn);
		try {
			if(gDAO.readOne(gen.getGenreId()) == null){
				throw new Exception(
						"The genre you are trying to update is null");
			}else{
				gDAO.update(gen);
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}

	public List<Genre> readAllGenres() throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		GenreDAO gDAO = new GenreDAO(conn);
		try {
			List<Genre> allGenres = gDAO.readAll();
			conn.commit();
			return allGenres;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return null;
		} finally {
			conn.close();
		}

	}

	public Genre readOneGenre(int genreId) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		GenreDAO gDAO = new GenreDAO(conn);
		try {
			Genre gen = gDAO.readOne(genreId);
			conn.commit();
			return gen;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return null;
		} finally {
			conn.close();
		}
	}
	
	
	/*
	 * BOOK
	*/
	
	public void createBook(Book book) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		BookDAO bDAO = new BookDAO(conn);

		try {
			if (book == null || book.getTitle() == null
					|| book.getTitle().length() == 0
					|| book.getTitle().length() > 45) {
				throw new Exception(
						"Book Title cannot be empty or more than 45 Chars");
			} else {
				bDAO.create(book);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void deletebook(Book book) throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		BookDAO bDAO = new BookDAO(conn);
		try {
			if(book == null){
				throw new Exception("The Book parameter can't be null");
			}else{
				bDAO.delete(book);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}

	public void updateBook(Book book) throws Exception{
		new ConnectionUtil();
		Connection conn = ConnectionUtil.createConnection();
		BookDAO bDAO = new BookDAO(conn);
		try {
			if(bDAO.readOne(book.getBookId()) == null){
				throw new Exception(
						"The Book you are trying to update is null");
			}else{
				bDAO.update(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}

	public List<Book> readAllBooks() throws Exception {
		new ConnectionUtil();
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

	public Book readOneBook(int bookId) throws Exception {
		new ConnectionUtil();
		Connection conn = ConnectionUtil.createConnection();
		BookDAO bDAO = new BookDAO(conn);
		try {
			Book book = bDAO.readOne(bookId);
			conn.commit();
			return book;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return null;
		} finally {
			conn.close();
		}
	}
	
	/*
	 * PUBLISHER
	*/
	
	public void createPublisher(Publisher pub) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		PublisherDAO pDAO = new PublisherDAO(conn);

		try {
			if (pub == null || pub.getPublisherName() == null
					|| pub.getPublisherName().length() == 0
					|| pub.getPublisherName().length() > 45) {
				throw new Exception(
						"Publihser Name cannot be empty or more than 45 Chars");
			} else if (pub == null || pub.getPublisherAddress() == null
					|| pub.getPublisherAddress().length() == 0
					|| pub.getPublisherAddress().length() > 45) {
				throw new Exception(
						"Publihser Address cannot be empty or more than 45 Chars");
			} else if (pub == null || pub.getPublisherPhone() == null
					|| pub.getPublisherPhone().length() == 0
					|| pub.getPublisherPhone().length() > 45) {
				throw new Exception(
						"Publihser Phone cannot be empty or more than 45 Chars");
			} else{
				pDAO.create(pub);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void deletePublisher(Publisher pub) throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		PublisherDAO pDAO = new PublisherDAO(conn);
		try {
			if(pub == null){
				throw new Exception("The Publisehr parameter can't be null");
			}else{
				pDAO.delete(pub);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}

	public void updatePublisher(Publisher pub) throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		PublisherDAO pDAO = new PublisherDAO(conn);
		try {
			if(pDAO.readOne(pub.getPublisherId()) == null){
				throw new Exception(
						"The Publisher you are trying to update is null");
			}else{
				pDAO.update(pub);
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}

	public List<Publisher> readAllPublisher() throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		PublisherDAO pDAO = new PublisherDAO(conn);
		try {
			List<Publisher> allPublishers = pDAO.readAll();
			conn.commit();
			return allPublishers;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return null;
		} finally {
			conn.close();
		}

	}

	public Publisher readOnePublihser(int pubId) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		PublisherDAO pDAO = new PublisherDAO(conn);
		try {
			Publisher pub = pDAO.readOne(pubId);
			conn.commit();
			return pub;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return null;
		} finally {
			conn.close();
		}
	}
	
	/*
	 * LIBRARY BRANCH
	*/
	
	public void createLibraryBranch(LibraryBranch lb) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		LibraryBranchDAO lbDAO = new LibraryBranchDAO(conn);

		try {
			if (lb == null || lb.getBranchName() == null
					|| lb.getBranchName().length() == 0
					|| lb.getBranchName().length() > 45) {
				throw new Exception(
						"Branch Name cannot be empty or more than 45 Chars");
			} else if (lb == null || lb.getBranchAddress() == null
					|| lb.getBranchAddress().length() == 0
					|| lb.getBranchAddress().length() > 45) {
				throw new Exception(
						"Branch Address cannot be empty or more than 45 Chars");
			} else {
				lbDAO.create(lb);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void deleteLibraryBranch(LibraryBranch lb) throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		LibraryBranchDAO lbDAO = new LibraryBranchDAO(conn);
		try {
			if(lb == null){
				throw new Exception("The Library you are trying to delete doesn't exist.");
			}else{
				lbDAO.delete(lb);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}

	public void updateLibraryBranch(LibraryBranch lb) throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		LibraryBranchDAO lbDAO = new LibraryBranchDAO(conn);
		try {
			if(lbDAO.readOne(lb.getBranchId()) == null){
				throw new Exception(
						"The Library branch you are trying to update doesn't exist.");
			}else{
				lbDAO.update(lb);
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}

	public List<LibraryBranch> readAllLibraryBranch() throws Exception {
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

	public LibraryBranch readOneLibraryBranch(int branchId) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		LibraryBranchDAO lbDAO = new LibraryBranchDAO(conn);
		try {
			LibraryBranch lb = lbDAO.readOne(branchId);
			conn.commit();
			return lb;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return null;
		} finally {
			conn.close();
		}
	}
	
	
	/*
	 * BORROWER
	*/
	public void createBorrower(Borrower bor) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		BorrowerDAO bDAO = new BorrowerDAO(conn);

		try {
			if (bor == null || bor.getName() == null
					|| bor.getName().length() == 0
					|| bor.getName().length() > 45) {
				throw new Exception(
						"Borrower Name cannot be empty or more than 45 Chars");
			} else if (bor == null || bor.getAddress() == null
					|| bor.getAddress().length() == 0
					|| bor.getAddress().length() > 45) {
				throw new Exception(
						"Borrower Address cannot be empty or more than 45 Chars");
			} else if (bor == null || bor.getPhone() == null
					|| bor.getPhone().length() == 0
					|| bor.getPhone().length() > 45) {
				throw new Exception(
						"Borrower Phone cannot be empty or more than 45 Chars");
			} else{
				bDAO.create(bor);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void deleteBorrower(Borrower bor) throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		BorrowerDAO bDAO = new BorrowerDAO(conn);
		try {
			if(bor == null){
				throw new Exception("The Borrower you are trying to delete doesn't exist.");
			}else{
				bDAO.delete(bor);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}

	public void updateBorrower(Borrower bor) throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		BorrowerDAO bDAO = new BorrowerDAO(conn);
		try {
			if(bDAO.readOne(bor.getCardNo()) == null){
				throw new Exception(
						"The Borrower you are trying to update doesn't exist.");
			}else{
				bDAO.update(bor);
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}

	public List<Borrower> readAllBorrower() throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		BorrowerDAO bDAO = new BorrowerDAO(conn);
		try {
			List<Borrower> allBorrowers = bDAO.readAll();
			conn.commit();
			return allBorrowers;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return null;
		} finally {
			conn.close();
		}

	}

	public Borrower readOneBorrower(int cardNo) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		BorrowerDAO bDAO = new BorrowerDAO(conn);
		try {
			Borrower bor = bDAO.readOne(cardNo);
			conn.commit();
			return bor;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return null;
		} finally {
			conn.close();
		}
	}
	
	/*
	 * BOOK LOANS
	*/
	public void createBookLoans(BookLoans bl) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		BookLoansDAO bDAO = new BookLoansDAO(conn);

		try {
			if (bl == null || bl.getDateOut() == null
					|| bl.getDueDate() == null
					|| bl.getDateOut().after(bl.getDueDate())) {
				throw new Exception(
						"Due Date must be greater than than DateOut");
			} else{
				bDAO.create(bl);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public void deleteBookLoans(BookLoans bl) throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		BookLoansDAO bDAO = new BookLoansDAO(conn);
		try {
			if(bl == null){
				throw new Exception("The record you are trying to delete doesn't exist.");
			}else{
				bDAO.delete(bl);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}

	public void updateBookLoans(BookLoans bl) throws Exception{
		Connection conn = ConnectionUtil.createConnection();
		BookLoansDAO bDAO = new BookLoansDAO(conn);
		try {
			if(bDAO.readOne(bl.getBook().getBookId(), bl.getBorrow().getCardNo(), bl.getBranch().getBranchId()) == null){
				throw new Exception(
						"The Record you are trying to update doesn't exist.");
			}else{
				bDAO.update(bl);
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
	}

	public List<BookLoans> readAllBookLoans() throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		BookLoansDAO bDAO = new BookLoansDAO(conn);
		try {
			List<BookLoans> allBookLoans = bDAO.readAll();
			conn.commit();
			return allBookLoans;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return null;
		} finally {
			conn.close();
		}

	}

	public BookLoans readOneBookLoan(int bookId, int branchId, int cardNo) throws Exception {
		Connection conn = ConnectionUtil.createConnection();
		BookLoansDAO bDAO = new BookLoansDAO(conn);
		try {
			BookLoans bl = bDAO.readOne(bookId, branchId, cardNo);
			conn.commit();
			return bl;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			return null;
		} finally {
			conn.close();
		}
	}

	//librarian service
	//Borrower Service

}
