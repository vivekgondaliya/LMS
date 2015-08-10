package com.gcit.lms.service;

import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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

//@Scope(value="prototype"), IF DON'T WANT SINGLETON
public class AdministratorService {

	@Autowired
	BasicDataSource ds;

	//DAOs
	@Autowired
	AuthorDAO authorDAO;
	@Autowired
	BookDAO bookDAO;
	@Autowired
	PublisherDAO publisherDAO;
	@Autowired
	BorrowerDAO borrowerDAO;
	@Autowired
	LibraryBranchDAO branchDAO;
	@Autowired
	GenreDAO genreDAO;
	@Autowired
	BookLoansDAO bookLoanDAO;


	/*
	 ************CREATE************
	 */
	//for different data source @Transactional(value="tx1")
	@Transactional
	public void createAuthor(Author author) throws Exception {
		
		if (author == null || author.getAuthorName() == null
				|| author.getAuthorName().length() == 0
				|| author.getAuthorName().length() > 45) {
			throw new Exception(
					"Author Name cannot be empty or more than 45 Chars");
		} else {
			authorDAO.create(author);
		}
	}

	@Transactional
	public void createBook(Book book) throws Exception {
		
		if (book == null || book.getTitle() == null
				|| book.getTitle().length() == 0
				|| book.getTitle().length() > 45) {
			throw new Exception(
					"Book Title cannot be empty or more than 45 Chars");
		} else if (book == null || book.getPublisher() == null) {
			throw new Exception(
					"Book Publisher cannot be null.");
		}else {
			bookDAO.create(book);
		}
	}

	@Transactional
	public void createPublisher(Publisher pub) throws Exception {
		
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
			publisherDAO.create(pub);
		}
	}

	@Transactional
	public void createLibraryBranch(LibraryBranch lb) throws Exception {
		
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
			branchDAO.create(lb);
		}
	}

	@Transactional
	public void createBorrower(Borrower bor) throws Exception {
		
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
			borrowerDAO.create(bor);
		}
	}

	@Transactional
	public void createGenre(Genre gen) throws Exception {
		
		if (gen == null || gen.getGenreName() == null
				|| gen.getGenreName().length() == 0
				|| gen.getGenreName().length() > 45) {
			throw new Exception(
					"Genre Name cannot be empty or more than 45 Chars");
		} else {
			genreDAO.create(gen);
		}
	}

	@Transactional
	public void createBookLoans(BookLoans bl) throws Exception {
		
		if (bl == null || bl.getDateOut() == null
				|| bl.getDueDate() == null
				|| bl.getDateOut().after(bl.getDueDate())) {
			throw new Exception(
					"Due Date must be greater than than DateOut");
		} else{
			bookLoanDAO.create(bl);
		}
	}


	/*
	 *************READ*************
	 */

	//author
	public List<Author> readAllAuthors() throws Exception {
		
		List<Author> allAuthors = authorDAO.readAll();
		return allAuthors;
	}

	public List<Author> readAllAuthors(int pageNo, int pageSize) throws Exception {
		
		return authorDAO.readAll(pageNo, pageSize);
	}

	public Author readOneAuthor(int authorId) throws Exception {
		
		Author author = authorDAO.readOne(authorId);
		return author;
	}

	//book
	public List<Book> readAllBooks() throws Exception {
		
		List<Book> allBooks =  bookDAO.readAll();
		return allBooks;
	}

	public List<Book> readAllBooks(int pageNo, int pageSize) throws Exception {
		
		return  bookDAO.readAll(pageNo, pageSize);
	}

	public Book readOneBook(int bookId) throws Exception {

		
		Book book =  bookDAO.readOne(bookId);	 
		return book;
	}

	//publisher
	public List<Publisher> readAllPublisher() throws Exception {
		
		List<Publisher> allPublishers = publisherDAO.readAll();
		return allPublishers;
	}

	public List<Publisher> readAllPublishers(int pageNo, int pageSize) throws Exception {
		
		return publisherDAO.readAll(pageNo, pageSize);
	}

	public Publisher readOnePublisher(int pubId) throws Exception {
		
		Publisher pub = publisherDAO.readOne(pubId);
		return pub;
	}

	//library branch
	public List<LibraryBranch> readAllLibraryBranch() throws Exception {
		
		List<LibraryBranch> allLibraryBranches =  branchDAO.readAll();
		return allLibraryBranches;
	} 

	public List<LibraryBranch> readAllLibraryBranch(int pageNo, int pageSize) throws Exception {
		
		return  branchDAO.readAll(pageNo, pageSize);
	}

	public LibraryBranch readOneLibraryBranch(int branchId) throws Exception {
		
		LibraryBranch lb =  branchDAO.readOne(branchId);
		return lb;
	}

	//borrower
	public List<Borrower> readAllBorrower() throws Exception {
		
		List<Borrower> allBorrowers =  borrowerDAO.readAll();
		return allBorrowers;
	} 

	public List<Borrower> readAllBorrowers(int pageNo, int pageSize) throws Exception {
		
		return  borrowerDAO.readAll(pageNo, pageSize);
	}

	public Borrower readOneBorrower(int cardNo) throws Exception {
		
		Borrower bor =  borrowerDAO.readOne(cardNo);
		return bor;
	}

	//genre
	public List<Genre> readAllGenres() throws Exception {
		
		List<Genre> allGenres = genreDAO.readAll();
		return allGenres;
	}

	public List<Genre> readAllGenres(int pageNo, int pageSize) throws Exception {
		
		return genreDAO.readAll(pageNo, pageSize);
	}

	public Genre readOneGenre(int genreId) throws Exception {
		
		Genre gen = genreDAO.readOne(genreId);
		return gen;
	}

	//book loan
	public List<BookLoans> readAllBookLoans() throws Exception {
		
		List<BookLoans> allBookLoans =  bookLoanDAO.readAll();
		return allBookLoans;
	} 

	public List<BookLoans> readAllBookLoans(int pageNo, int pageSize) throws Exception {
		
		return  bookLoanDAO.readAll(pageNo, pageSize);
	}

	public BookLoans readOneBookLoan(int bookId, int branchId, int cardNo) throws Exception {
		
		BookLoans bl =  bookLoanDAO.readOne(bookId, branchId, cardNo);
		return bl;
	}


	/*
	 *************UPDATE***********
	 */

	@Transactional
	public void updateAuthor(Author author) throws Exception{
		
		if(authorDAO.readOne(author.getAuthorId()) == null){
			throw new Exception(
					"The author you are trying to update does not exist");
		}else{
			authorDAO.update(author);
		}
	}

	@Transactional
	public void updateBook(Book book) throws Exception{
		
		if(bookDAO.readOne(book.getBookId()) == null){
			throw new Exception(
					"The Book you are trying to update is null");
		}else{
			bookDAO.update(book);

		}
	}

	@Transactional
	public void updatePublisher(Publisher pub) throws Exception{
		
		if(publisherDAO.readOne(pub.getPublisherId()) == null){
			throw new Exception(
					"The Publisher you are trying to update is null");
		}else{
			publisherDAO.update(pub);
		}
	}

	@Transactional
	public void updateLibraryBranch(LibraryBranch lb) throws Exception{
		
		if( branchDAO.readOne(lb.getBranchId()) == null){
			throw new Exception(
					"The Library branch you are trying to update doesn't exist.");
		}else{
			branchDAO.update(lb);
		}
	}

	@Transactional
	public void updateGenre(Genre gen) throws Exception{
		
		if(genreDAO.readOne(gen.getGenreId()) == null){
			throw new Exception(
					"The genre you are trying to update is null");
		}else{
			genreDAO.update(gen);
		}
	}

	@Transactional
	public void updateBorrower(Borrower bor) throws Exception{
		
		if( borrowerDAO.readOne(bor.getCardNo()) == null){
			throw new Exception(
					"The Borrower you are trying to update doesn't exist.");
		}else{
			borrowerDAO.update(bor);
		}
	}

	@Transactional
	public void updateBookLoans(BookLoans bl) throws Exception{
		
		if( bookLoanDAO.readOne(bl.getBook().getBookId(), bl.getBorrow().getCardNo(), bl.getBranch().getBranchId()) == null){
			throw new Exception(
					"The Record you are trying to update doesn't exist.");
		}else{
			bookLoanDAO.update(bl);
		}
	} 


	/*
	 *************DELETE***********
	 */

	@Transactional
	public void deleteAuthor(Author author) throws Exception{
				
		authorDAO.delete(author);
	}

	@Transactional
	public void deletebook(Book book) throws Exception{
		
		if(book == null){
			throw new Exception("The Book parameter can't be null");
		}else{

			bookDAO.delete(book);
		}
	}

	@Transactional
	public void deletePublisher(Publisher pub) throws Exception{
		
		if(pub == null){
			throw new Exception("The Publisher parameter can't be null");
		}else{
			publisherDAO.delete(pub);
		}
	}

	@Transactional
	public void deleteLibraryBranch(LibraryBranch lb) throws Exception{
		
		if(lb == null){
			throw new Exception("The Library you are trying to delete doesn't exist.");
		}else{
			branchDAO.delete(lb);
		}
	}

	@Transactional
	public void deleteBorrower(Borrower bor) throws Exception{
		
		if(bor == null){
			throw new Exception("The Borrower you are trying to delete doesn't exist.");
		}else{
			borrowerDAO.delete(bor);
		}
	}

	@Transactional
	public void deleteGenre(Genre gen) throws Exception{
		
		if(gen == null){
			throw new Exception("The Genre parameter can't be null");
		}else{
			genreDAO.delete(gen);
		}
	}

	@Transactional
	public void deleteBookLoans(BookLoans bl) throws Exception{
		
		if(bl == null){
			throw new Exception("The record you are trying to delete doesn't exist.");
		}else{
			bookLoanDAO.delete(bl);
		}
	} 


	/*
	 *************SEARCH*************
	 */
	public List<Author> searchAuthors(String searchString) throws Exception{
		
		return authorDAO.readByAuthorName(searchString);
	}

	public List<Genre> searchGenres(String searchString) throws Exception{
		
		return genreDAO.readByGenreName(searchString);
	}

	public List<Book> searchBooks(String searchString) throws Exception{
		
		return  bookDAO.readByBookName(searchString);
	}

	public List<Publisher> searchPublishers(String searchString) throws Exception{
		
		return publisherDAO.readByPublisherName(searchString);
	}

	public List<LibraryBranch> searchBranches(String searchString) throws Exception{
		
		return  branchDAO.readByBranchName(searchString);
	}

	public List<Borrower> searchBorrowers(String searchString) throws Exception{
		
		return  borrowerDAO.readByBorrowerName(searchString);
	}

	public List<BookLoans> searchBookLoans(String searchString) throws Exception{
		
		return  bookLoanDAO.readByBookLoansName(searchString);
	}
}
