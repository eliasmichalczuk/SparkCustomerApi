package com.elias.spark.customer;

public class Book {
	String title;
	String author;
	String isbn;

	public Book(String title, String author, String isbn) {
		super();
		this.title = title;
		this.author = author;
		this.isbn = isbn;
	}

	public String getMediumCover() {
		return "http://covers.openlibrary.org/b/isbn/" + this.isbn + "-M.jpg";
	}

	public String getLargeCover() {
		return "http://covers.openlibrary.org/b/isbn/" + this.isbn + "-L.jpg";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
}
