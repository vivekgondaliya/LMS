package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Genre;

public class GenreDAO extends BaseDAO<Genre> implements ResultSetExtractor<List<Genre>>{
	
	@Autowired
	BookDAO bookDAO;
	
	//INSERT
	public void create(Genre gen) throws Exception {
		template.update("insert into tbl_genre (genre_name) values(?)",
				new Object[] { gen.getGenreName()});
	}

	//UPDATE
	public void update(Genre gen) throws Exception {
		template.update("update tbl_genre set genre_name=? where genre_id=?",
				new Object[] { gen.getGenreName(), gen.getGenreId()});
	}

	//DELETE
	public void delete(Genre gen) throws Exception {
		template.update("delete from tbl_genre where genre_id=?",
				new Object[] { gen.getGenreId()});
	}

	//SELECT ALL
	public List<Genre> readAll() throws Exception {
		return template.query("select * from tbl_genre", this);
	}

	//SELECT ONE
	public Genre readOne(int genreId) throws Exception {

		List<Genre> genre = template.query("select * from tbl_genre where genre_id=?", new Object[] {genreId}, this);
		if(genre!=null && genre.size()>0){
			return genre.get(0);
		}
		return null;
	}

	//RETURN List of Publishers
	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException {

		//create a list
		List<Genre> genre = new ArrayList<Genre>();
	
		//populate the list
		while(rs.next()){

			Genre gen  = new Genre();
			gen.setGenreId(rs.getInt("genre_id"));
			gen.setGenreName(rs.getString("genre_name"));
			
//			List<Book> books = template.query("select * from tbl_book where bookId In"
//					+ "(select bookId from tbl_book_genres where genre_id=?)", new Object[] {rs.getInt("genre_id")}, bookDAO);
//			gen.setBooks(books);

			genre.add(gen);
		}
		return genre;
	}

	//author by Name
	public List<Genre> readByGenreName(String searchString) throws Exception{
		searchString = "%"+searchString+"%";
		return (List<Genre>) template.query("select * from tbl_genre where genre_Name like ?", new Object[] {searchString}, this);
	}

	//read for PAGINATION
	public List<Genre> readAll(int pageNo, int pageSize) throws Exception{
		setPageNo(pageNo);
		setPageSize(pageSize);
		return (List<Genre>) template.query("select * from tbl_genre", this);
	}
}
