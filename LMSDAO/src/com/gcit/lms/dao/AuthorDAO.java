package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;

public class AuthorDAO extends BaseDAO<Author> {

	public AuthorDAO(Connection conn) throws Exception {
		super(conn);
	}

	//CREATE
	public void create(Author author) throws Exception {
		save("insert into tbl_author (authorName) values(?)",
				new Object[] { author.getAuthorName() });
	}

	//UPDATE
	public void update(Author author) throws Exception {
		save("update tbl_author set authorName = ? where authorId = ?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });

	}

	//DELETE
	public void delete(Author author) throws Exception {
		save("delete from tbl_author where authorId = ?",
				new Object[] { author.getAuthorId() });
	}

	//SELECT ALL
	public List<Author> readAll() throws Exception{
		return read("select * from tbl_author", null);
		
	}

	//SELECT ONE based on ID
	public Author readOne(int authorId) throws Exception {
		List<Author> authors = (List<Author>) read("select * from tbl_author", new Object[] {authorId});
		if(authors!=null && authors.size()>0){
			return authors.get(0);
		}
		return null;
	}

	//STORE DATA from DB to a List
	@Override
	public List<Author> extractData(ResultSet rs) throws Exception {
		List<Author> authors =  new ArrayList<Author>();
		BookDAO bDAO = new BookDAO(getConnection());
		
		while(rs.next()){
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			
			List<Book> books = (List<Book>) bDAO.readFirstLevel("select * from tbl_books where bookId In"
					+ "(select bookId from tbl_book_authors where authorId=?)", new Object[] {rs.getInt("authorId")});
			a.setBooks(books);
			
			authors.add(a);
		}
		return authors;
	}

	@Override
	public List<Author> extractDataFirstLevel(ResultSet rs) throws Exception {
		List<Author> authors =  new ArrayList<Author>();
		
		while(rs.next()){
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			
			authors.add(a);
		}
		return authors;
	}
	
	

}
