package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Book;

public class BookDAO extends BaseDAO<Book> implements ResultSetExtractor<List<Book>>{

	@Autowired
	AuthorDAO authorDAO;

	@Autowired
	PublisherDAO publisherDAO;

	@Autowired
	GenreDAO genreDAO;
	
	private final String BOOK_COLLECTION = "books";
	//INSERT
	public void create(final Book book) throws Exception{

/*		//for saveWithID use KeyHolder
		KeyHolder keyHolder = new GeneratedKeyHolder();
		//		template.update("insert into tbl_book (title,pubId) values(?,?)",
		//				new Object[] { book.getTitle(), book.getPublisher().getPublisherId(), keyHolder});

		template.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement pstmt = template.getDataSource().getConnection().prepareStatement("insert into tbl_book (title, pubId) values(?, ?)",  Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, book.getTitle());
				pstmt.setObject(2, book.getPublisher().getPublisherId());
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
		}*/
		mongoOps.insert(book, BOOK_COLLECTION);
	}

	//READ
	//read ALL
	public List<Book> readAll() throws Exception {
		return mongoOps.findAll(Book.class, BOOK_COLLECTION);
	}

	//read ONE
	public Book readOne(UUID bookId) throws Exception {
		Query query = new Query(Criteria.where("_id").is(bookId));
        return this.mongoOps.findOne(query, Book.class, BOOK_COLLECTION);

	}

	//RETURN List of Publishers
	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		return null;
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

	//UPDATE
	public void update(Book book) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(book.getBookId()));
 
		Update update = new Update();
		update.set("title", book.getTitle());
		update.set("pubId", book.getPublisher().getPublisherId());
		
		//HOW DOES AUTHOR and GENRE UPDATE?????????????
		
		mongoOps.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Book.class, BOOK_COLLECTION);
	}

	//DELETE
	public void delete(Book book) throws Exception {
		//get a query
		Query query = new Query();
		//add criteria to the query
		query.addCriteria(Criteria.where("_id").exists(true));
		//execute find and remove
		mongoOps.findAndRemove(query, Book.class, BOOK_COLLECTION);
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
