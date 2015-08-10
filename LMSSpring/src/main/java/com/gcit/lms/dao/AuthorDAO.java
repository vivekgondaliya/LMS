package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;

public class AuthorDAO extends BaseDAO<Author> implements ResultSetExtractor<List<Author>>{

	@Autowired
	BookDAO bookDAO;
	/*
	save() = template.Update();
	read() = template.query(, this);
	 */

	//CREATE
	public void create(Author author) throws Exception {
		template.update("insert into tbl_author (authorName) values(?)",
				new Object[] { author.getAuthorName() });
	}

	//UPDATE
	public void update(Author author) throws Exception {
		template.update("update tbl_author set authorName = ? where authorId = ?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });

	}

	//DELETE
	public void delete(Author author) throws Exception {
		template.update("delete from tbl_author where authorId = ?",
				new Object[] { author.getAuthorId() });
	}

	//SELECT ALL
	public List<Author> readAll() throws Exception{
		return template.query("select * from tbl_author", this);

	}

	//SELECT ONE based on ID
	public Author readOne(int authorId) throws Exception {
		List<Author> authors = (List<Author>) template.query("select * from tbl_author where authorId=?", new Object[] {authorId}, this);
		if(authors!=null && authors.size()>0){
			return authors.get(0);
		}
		return null;
	}

	//STORE DATA from DB to a List
	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors =  new ArrayList<Author>();
		while(rs.next()){
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
//			try {
//				List<Book> books = template.query("select * from tbl_book where bookId In"
//						+ "(select bookId from tbl_book_authors where authorId=?)", new Object[] {rs.getInt("authorId")}, bookDAO);
//				a.setBooks(books);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			authors.add(a);
		}
		return authors;
	}

	//author by Name
	public List<Author> readByAuthorName(String searchString) throws Exception{
		searchString = "%"+searchString+"%";
		return (List<Author>) template.query("select * from tbl_author where authorName like ?", new Object[] {searchString}, this);
	}

	//read for PAGINATION
	public List<Author> readAll(int pageNo, int pageSize) throws Exception{
		setPageNo(pageNo);
		setPageSize(pageSize);
		return (List<Author>) template.query("select * from tbl_author LIMIT ?, ?", new Object[] {pageNo, pageSize}, this);
	}

	//returns COUNT
	public int readCount() throws Exception{
		return template.queryForObject("select count(1) from tbl_author", Integer.class);
	}

}
