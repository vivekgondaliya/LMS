package com.gcit.lms;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.service.AdministratorService;

@EnableTransactionManagement
@Configuration
public class LMSConfig {

	private static String driver = "com.mysql.jdbc.Driver";
	private static String connection = "jdbc:mysql://localhost:3306/library";
	private static String user = "root";
	private static String pass = "1234";

	//CONNECTION
	@Bean
	public BasicDataSource dataSource(){
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(connection);
		ds.setUsername(user);	
		ds.setPassword(pass);
		return ds;
	}

	//JDBC Template
	@Bean
	public JdbcTemplate template(){
		JdbcTemplate template = new JdbcTemplate();
		template.setDataSource(dataSource());
		return template;
	}
	
	//Manage transaction
	@Bean
	public PlatformTransactionManager txManager(){
		DataSourceTransactionManager tx = new DataSourceTransactionManager();
		tx.setDataSource(dataSource());
		return tx;
	}
	
	//Manage multiple transaction @Bean(name="tx1") go to point of transaction
	//SERVICE POOLING
	@Bean
	public AdministratorService adminService(){
		return new AdministratorService();
	}
	
	//DAOs POOLING
	@Bean
	public AuthorDAO authorDAO(){
		return new AuthorDAO();
	}
	
	@Bean
	public BookDAO bookDAO(){
		return new BookDAO();
	}
	
	@Bean
	public PublisherDAO publisherDAO(){
		return new PublisherDAO();
	}
	
	@Bean
	public BorrowerDAO borrowerDAO(){
		return new BorrowerDAO();
	}
	
	@Bean
	public LibraryBranchDAO branchDAO(){
		return new LibraryBranchDAO();
	}
	
	@Bean
	public GenreDAO genreDAO(){
		return new GenreDAO();
	}
	
	@Bean
	public BookLoansDAO bookLoansDAO(){
		return new BookLoansDAO();
	}
	
	@Bean
	public BookCopiesDAO bookCopiesDAO(){
		return new BookCopiesDAO();
	}
}
