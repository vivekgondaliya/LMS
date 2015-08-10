package com.gcit.lms.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Book {
	@Id
	private UUID bookId = UUID.randomUUID();
	private String title;
	private Publisher publisher;
	
	//update other tables?
	private List<Author> authors;
	private List<Genre> genres;
	private List<BookCopies> copies;
	
	public List<BookCopies> getCopies() {
		return copies;
	}
	public void setCopies(List<BookCopies> copies) {
		this.copies = copies;
	}
	public List<LibraryBranch> getBranches() {
		return branches;
	}
	public void setBranches(List<LibraryBranch> branches) {
		this.branches = branches;
	}
	private List<LibraryBranch> branches;
	
	public UUID getBookId() {
		return bookId;
	}
	public void setBookId(UUID bookId) {
		this.bookId = bookId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Publisher getPublisher() {
		return publisher;
	}
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	public List<Author> getAuthors() {
		return authors;
	}
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
	public List<Genre> getGenres() {
		return genres;
	}
	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}
	
}
