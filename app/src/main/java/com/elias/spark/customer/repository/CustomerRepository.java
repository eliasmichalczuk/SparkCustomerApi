package com.elias.spark.customer.repository;

import java.util.List;
import java.util.Random;

import com.elias.spark.customer.Book;
import com.google.common.collect.ImmutableList;

public class CustomerRepository {

	private final List<Book> books = ImmutableList.of(new Book("Moby Dick", "Herman Melville", "9789583001215"),
	                                                  new Book("A Christmas Carol",
	                                                           "Charles Dickens",
	                                                           "9780141324524"));

	public Iterable<Book> getAllBooks() {
		return books;
	}

	public Book getBookByIsbn(String isbn) {
		return books.stream().filter(b -> b.getIsbn().equals(isbn)).findFirst().orElse(null);
	}

	public Book getRandomBook() {
		return books.get(new Random().nextInt(books.size()));
	}
}
