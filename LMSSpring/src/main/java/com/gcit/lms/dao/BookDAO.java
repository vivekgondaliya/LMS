package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Genre;

public class BookDAO extends BaseDAO<Book> implements ResultSetExtractor<List<Book>>{

	@Autowired
	AuthorDAO authorDAO;

	@Autowired
	PublisherDAO publisherDAO;

	@Autowired
	GenreDAO genreDAO;

	//INSERT
	public void create(final Book book) throws Exception{

		//for saveWithID use KeyHolder
		KeyHolder keyHolder = new GeneratedKeyHolder();
		//		template.update("insert into tbl_book (title,pubId) values(?,?)",
		//				new Object[] { book.getTitle(), book.getPublisher().getPublisherId(), keyHolder});

		template.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement pstmt = template.getDataSource().getConnection().prepareStatement("insert into tbl_book (title, pubId) values(?, ?)",  Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, book.getTitle());
				pstmt.setInt(2, book.getPublisher().getPublisherId());
				return pstmt;
			}
		}, keyHolder);

		int bookId = keyHolder.getKey().intValue();

		//insert tbl_book_authors
		for (Author a: book.getAuthors()){
			template.update("insert into tbl_book_authors (bookId, authorId) values(?,?)", new Object[] {bookId, a.getAuthorId()});
		}
		//insert tbl_book_genres
		for(Genre g: book.getGenres()){
			template.update("insert into tbl_book_genres (bookId, genre_id) values(?,?)", new Object[]{bookId, g.getGenreId()});
		}
	}

	//UPDATE
	public void update(Book book) throws Exception {
		template.update("update tbl_book set title=?, pubId=? where bookId=?",
				new Object[] { book.getTitle(), book.getPublisher().getPublisherId(), book.getBookId()});

		//update tbl_authors
		for (Author a: book.getAuthors()){
			template.update("update tbl_book_authors set authorId=? where bookId=?", new Object[] {a.getAuthorId(), book.getBookId()});
		}
		//update tbl_genres
		for (Genre g: book.getGenres()){
			template.update("update tbl_book_genres set genre_id=? where bookId=?", new Object[] {g.getGenreId(), book.getBookId()});
		}
	}

	//DELETE
	public void delete(Book book) throws Exception {
		template.update("delete from tbl_book where bookId=?",
				new Object[] { book.getBookId()});
	}

	//SELECT ALL
	public List<Book> readAll() throws Exception {
		return template.query("select * from tbl_book", this);
	}

	//SELECT ONE
	public Book readOne(int bookId) throws Exception {

		List<Book> book = (List<Book>) template.query("select * from tbl_book where bookId=?", new Object[] {bookId}, this);
		if(book!=null && book.size()>0){
			return book.get(0);
		}
		return null;
	}

	//RETURN List of Publishers
	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {

		//create a list
		List<Book> booksList = new ArrayList<Book>();

		//populate the list
		while(rs.next()){	
			Book book  = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			try {
				book.setPublisher(publisherDAO.readOne(rs.getInt("pubId")));
			} catch (Exception e) {
				e.printStackTrace();
			}

			//get list of authors with given bookId
			List<Author> authors= template.query("select * from tbl_author where authorId In"
					+ "(select authorId from tbl_book_authors where bookId=?)", new Object[] {rs.getInt("bookId")}, authorDAO);
			book.setAuthors(authors);

			//get list of genres with given bookId
			List<Genre> genres= template.query("select * from tbl_genre where genre_id In"
					+ "(select genre_id from tbl_book_genres where bookId=?)", new Object[]{rs.getInt("bookId")}, genreDAO);
			book.setGenres(genres);

			//Cycling dependencies: extractDataFirstLevel VERY IMPORTANT
			//BASE DAO: based on BASE DAO, other DAO are going to react.
			//create connection in service layer and then pass instance to DAO

			booksList.add(book);
		}
		return booksList;
	}

	//search book by Name
	public List<Book> readByBookName(String searchString) throws Exception{
		searchString = "%"+searchString+"%";
		return (List<Book>) template.query("select * from tbl_book where title like ?", new Object[] {searchString}, this);
	}

	//read for PAGINATION
	public List<Book> readAll(int pageNo, int pageSize) throws Exception{
		setPageNo(pageNo);
		setPageSize(pageSize);
		return (List<Book>) template.query("select * from tbl_book", this);
	}

	//return list of books for given branch, where noOfCopies > 0
	public List<Book> numOfCopiesBranch(int branchId){

		return template.query("SELECT * FROM tbl_book JOIN tbl_book_copies ON tbl_book.bookId=tbl_book_copies.bookId WHERE branchId=?  AND noOfCopies>0", 
				new Object[]{branchId}, this);
	}
	
	//list of boon in given branch
	public List<Book> branchBooks(int branchId){
		return template.query("SELECT * FROM tbl_book JOIN tbl_book_copies ON tbl_book.bookId = tbl_book_copies.bookID"
				+ " WHERE branchId=? AND noOfCopies >=1", new Object[] {branchId}, this);
	}
}
