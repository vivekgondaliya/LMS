package com.gcit.lms.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Genre {
	@Id
	private UUID genreId = UUID.randomUUID();
	private String genreName;
	
	private List<Book> books;
	
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	/**
	 * @return the genreId
	 */
	public UUID getGenreId() {
		return genreId;
	}
	/**
	 * @param genreId the genreId to set
	 */
	public void setGenreId(UUID genreId) {
		this.genreId = genreId;
	}
	/**
	 * @return the genreName
	 */
	public String getGenreName() {
		return genreName;
	}
	/**
	 * @param genreName the genreName to set
	 */
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}
	
}
