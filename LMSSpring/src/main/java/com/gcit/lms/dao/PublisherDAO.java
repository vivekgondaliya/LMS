package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Publisher;

public class PublisherDAO extends BaseDAO<Publisher> implements ResultSetExtractor<List<Publisher>>{

	@Autowired
	BookDAO bookDAO;
	
	//INSERT
	public void create(Publisher pub) throws Exception {
		template.update("insert into tbl_publisher (publisherName, publisherAddress, publisherPhone) values(?, ?, ?)",
				new Object[] { pub.getPublisherName(), pub.getPublisherAddress(), pub.getPublisherPhone() });

		//		//add new publisher to update tbl_book
		//		for(Book b: pub.getBooks()){
		//			save("update tbl_book set pubId = ? where bookId = ?", 
		//					new Object[]{pub.getPublisherId(), b.getBookId()});
		//		}
	}

	//UPDATE
	public void update(Publisher pub) throws Exception {
		template.update("update tbl_publisher set publisherName=?, publisherAddress=?, publisherPhone=? where publisherId=?",
				new Object[] { pub.getPublisherName(), pub.getPublisherAddress(), pub.getPublisherPhone(), pub.getPublisherId()});

	}

	//DELETE
	public void delete(Publisher pub) throws Exception {
		template.update("delete from tbl_publisher where publisherId = ?",
				new Object[] { pub.getPublisherId()});
	}

	//SELECT ALL
	public List<Publisher> readAll() throws Exception {
		return template.query("select * from tbl_publisher", this);
	}

	//SELECT ONE
	public Publisher readOne(int publisherId) throws Exception {

		List<Publisher> publisher = template.query("select * from tbl_publisher WHERE publisherId = ?", new Object[] {publisherId}, this);
		if(publisher!=null && publisher.size()>0){
			return publisher.get(0);
		}
		return null;
	}

	//RETURN List of Publishers
	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException {

		//create a list
		List<Publisher> publishers = new ArrayList<Publisher>();

		//populate the list
		while(rs.next()){

			Publisher pub  = new Publisher();
			pub.setPublisherId(rs.getInt("publisherId"));
			pub.setPublisherName(rs.getString("publisherName"));
			pub.setPublisherAddress(rs.getString("publisherAddress"));
			pub.setPublisherPhone(rs.getString("publisherPhone"));

//			//list of book with given publisher
//			List<Book> books = template.query("select * from tbl_book where pubId=?"
//					, new Object[] {rs.getInt("publisherId")}, bookDAO);
//			pub.setBooks(books);

			publishers.add(pub);
		}
		return publishers;
	}
	
	//read for PAGINATION
	public List<Publisher> readAll(int pageNo, int pageSize) throws Exception{
		setPageNo(pageNo);
		setPageSize(pageSize);
		return (List<Publisher>) template.query("select * from tbl_publisher", this);

	}

	//publisher by Name
		public List<Publisher> readByPublisherName(String searchString) throws Exception{
			searchString = "%"+searchString+"%";
			return (List<Publisher>) template.query("select * from tbl_publisher where publisherName or publisherAddress or publisherPhone like ?", new Object[] {searchString}, this);
		}
}
