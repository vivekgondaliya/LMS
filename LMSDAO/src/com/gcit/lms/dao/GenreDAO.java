package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Genre;

public class GenreDAO extends BaseDAO<Genre>{

	public GenreDAO(Connection conn) throws Exception {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	//INSERT
	public void create(Genre gen) throws Exception {
		save("insert into tbl_genre (genre_name) values(?)",
				new Object[] { gen.getGenreName()});
	}

	//UPDATE
	public void update(Genre gen) throws Exception {
		save("update tbl_genre set genre_name=? where genre_id=?",
				new Object[] { gen.getGenreName(), gen.getGenreId()});
	}

	//DELETE
	public void delete(Genre gen) throws Exception {
		save("delete from tbl_genre where genre_id=?",
				new Object[] { gen.getGenreId()});
	}

	//SELECT ALL
	public List<Genre> readAll() throws Exception {
		return read("select * from tbl_genre", null);
	}

	//SELECT ONE
	public Genre readOne(int genreId) throws Exception {

		List<Genre> genre = read("select * from tbl_genre", new Object[] {genreId});
		if(genre!=null && genre.size()>0){
			return genre.get(0);
		}
		return null;
	}

	//RETURN List of Publishers
	@Override
	public List<Genre> extractData(ResultSet rs) throws Exception {

		//create a list
		List<Genre> genre = new ArrayList<Genre>();
		BookDAO bDAO = new BookDAO(getConnection());
		
		//populate the list
		while(rs.next()){

			Genre gen  = new Genre();
			gen.setGenreId(rs.getInt("genre_id"));
			gen.setGenreName(rs.getString("genre_name"));
			
			List<Book> books = (List<Book>) bDAO.readFirstLevel("select * from tbl_book where bookId In"
					+ "(select bookId from tbl_book_genres where genre_id=?)", new Object[] {rs.getInt("genre_id")});
			gen.setBooks(books);
			
			genre.add(gen);
		}
		return genre;
	}

	@Override
	public List<Genre> extractDataFirstLevel(ResultSet rs) throws Exception {
		
		//create a list
		List<Genre> genre = new ArrayList<Genre>();

		//populate the list
		while(rs.next()){

			Genre gen  = new Genre();
			gen.setGenreId(rs.getInt("genre_id"));
			gen.setGenreName(rs.getString("genre_name"));
			
			genre.add(gen);
		}
		return genre;
	}

}
