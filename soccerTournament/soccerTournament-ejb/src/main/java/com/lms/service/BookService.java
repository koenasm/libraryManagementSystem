package com.lms.service;

import java.util.List;

import javax.inject.Inject;

import com.lms.data.BookRepository;
import com.lms.model.Books;


public class BookService {
	@Inject
	private BookRepository bookRepository;

	public Books addBookDetails(Books book) {
		return bookRepository.persistBookDetails(book);
	}

	public Books updateBookDetails(Books book) {
		return bookRepository.updateBookDetails(book);
	}

	public List<Books> getAllBooks() {
		return bookRepository.findAllBooks();
	}
}
