package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Author;

public class AuthorDAO extends BaseDAO<Author> implements ResultSetExtractor<List<Author>>{

	@Autowired
	BookDAO bookDAO;
	
	/*
	save() = template.Update();
	read() = template.query(, this);
	 */
	
	private final String AUTHOR_COLLECTION = "authors";
	
	//CREATE
	public void create(Author author) throws Exception {
		mongoOps.insert(author, AUTHOR_COLLECTION);
	}

	//UPDATE
	public void update(Author author) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(author.getAuthorId()));
 
		Update update = new Update();
		update.set("authorName", author.getAuthorName());
 
		//FindAndModifyOptions().returnNew(true) = newly updated document
		//FindAndModifyOptions().returnNew(false) = old document (not update yet)
		mongoOps.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Author.class, AUTHOR_COLLECTION);
	}

	//DELETE
	public void delete(Author author) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").exists(true));
		
		mongoOps.findAndRemove(query, Author.class, AUTHOR_COLLECTION);
	}

	//SELECT ALL
	public List<Author> readAll() throws Exception{
		return mongoOps.findAll(Author.class, AUTHOR_COLLECTION);
	}

	//SELECT ONE based on ID
	public Author readOne(UUID authorId) throws Exception {
		Query query = new Query(Criteria.where("_id").is(authorId));
        return this.mongoOps.findOne(query, Author.class, AUTHOR_COLLECTION);
	}

	//author by Name
	public List<Author> readByAuthorName(String searchString) throws Exception{
		searchString = "/"+searchString+"/";
		Query query = new Query(Criteria.where("authorName").regex(searchString));
		return mongoOps.find(query, Author.class, AUTHOR_COLLECTION);
	}

	//read for PAGINATION
	public List<Author> readAll(int pageNumber, int nPerPage) throws Exception{
		//db.students.find().skip(pageNumber > 0 ? ((pageNumber-1)*nPerPage) : 0).limit(nPerPage).forEach( function(student) { print(student.name + "<p>"); } );
		return mongoOps.findAll(Author.class, AUTHOR_COLLECTION);
	}

	//returns COUNT
	public long readCount() throws Exception{
		Query query = new Query(Criteria.where("_id").exists(true));
		return mongoOps.count(query, Author.class);
	}

	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

}
