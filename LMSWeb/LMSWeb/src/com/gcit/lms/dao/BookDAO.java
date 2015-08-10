package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Genre;

public class BookDAO extends BaseDAO<Book>{

	public BookDAO(Connection conn) throws Exception {
		super(conn);
	}

	//INSERT
	public void create(Book book) throws Exception {

		//		save("insert into tbl_book (title,pubId) values(?,?)",
		//				new Object[] { book.getTitle(), book.getPublisher().getPublisherId() });
		//		
		int bookId = saveWithID("insert into tbl_book (title,pubId) values(?,?)",
				new Object[] { book.getTitle(), book.getPublisher().getPublisherId() });

		//insert tbl_book_authors
		for (Author a: book.getAuthors()){
			save("insert into tbl_book_authors (bookId, authorId) values(?,?)", new Object[] {bookId, a.getAuthorId()});
		}
		//insert tbl_book_genres
		for(Genre g: book.getGenres()){
			save("insert into tbl_book_genres (bookId, genre_id) values(?,?)", new Object[]{bookId, g.getGenreId()});
		}
	}

	//UPDATE
	public void update(Book book) throws Exception {
		int bookId = saveWithID("update tbl_book set title=?, pubId=? where bookId=?",
				new Object[] { book.getTitle(), book.getPublisher().getPublisherId(), book.getBookId()});

		//update tbl_authors
		for (Author a: book.getAuthors()){
			save("update tbl_book_authors set authorId=? where bookId=?", new Object[] {a.getAuthorId(), bookId});
		}
		//update tbl_genres
		for (Genre g: book.getGenres()){
			save("update tbl_book_genres set genre_id=? where bookId=?", new Object[] {g.getGenreId(), bookId});
		}
	}

	//DELETE
	public void delete(Book book) throws Exception {
		save("delete from tbl_book where bookId=?",
				new Object[] { book.getBookId()});
	}

	//SELECT ALL
	public List<Book> readAll() throws Exception {
		return read("select * from tbl_book", null);
	}

	//SELECT ONE
	public Book readOne(int bookId) throws Exception {

		List<Book> book = (List<Book>)read("select * from tbl_book where bookId=?", new Object[] {bookId});
		if(book!=null && book.size()>0){
			return book.get(0);
		}
		return null;
	}

	//RETURN List of Publishers
	@Override
	public List<Book> extractData(ResultSet rs) throws Exception {

		//create a list
		List<Book> booksList = new ArrayList<Book>();

		PublisherDAO pDAO = new PublisherDAO(getConnection());
		AuthorDAO aDAO = new AuthorDAO(getConnection());
		GenreDAO gDAO = new GenreDAO(getConnection());

		//populate the list
		while(rs.next()){	
			Book book  = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			book.setPublisher(pDAO.readOne(rs.getInt("pubId")));

			//get list of authors with given bookId
			//List<Author> authors= (List<Author>) aDAO.readFirstLevel("select * from tbl_author where authorId In"
			//	+ "(select authorId from tbl_book_authors where bookId=?)", new Object[] {rs.getInt("bookId")});
			//book.setAuthors(authors);

			//get list of genres with given bookId
			//List<Genre> genres= (List<Genre>) gDAO.readFirstLevel("select * from tbl_genre where genre_id In"
			//	+ "(select genre_id from tbl_book_genres where bookId=?)", new Object[]{rs.getInt("bookId")});
			//book.setGenres(genres);

			//Cycling dependencies: extractDataFirstLevel VERY IMPORTANT
			//BASE DAO: based on BASE DAO, other DAO are going to react.
			//create connection in service layer and then pass instance to DAO

			booksList.add(book);
		}
		return booksList;
	}

	@Override
	public List<Book> extractDataFirstLevel(ResultSet rs) throws Exception {

		List<Book> booksList = new ArrayList<Book>();

		while(rs.next()){
			Book book = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));

			booksList.add(book);
		}

		return booksList;
	}

	//search book by Name
	public List<Book> readByBookName(String searchString) throws Exception{
		searchString = "%"+searchString+"%";
		return (List<Book>) read("select * from tbl_book where title like ?", new Object[] {searchString});
	}

	//read for PAGINATION
	public List<Book> readAll(int pageNo, int pageSize) throws Exception{
		setPageNo(pageNo);
		setPageSize(pageSize);
		return (List<Book>) read("select * from tbl_book", null);

	}

}
