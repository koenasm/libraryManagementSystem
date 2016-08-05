package com.lms.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import com.lms.model.BookStatus;
import com.lms.model.Books;
import com.lms.service.BookService;

@ManagedBean
@SessionScoped
public class BookController extends AbstractController {

	protected static final String BOOK_LIST_PAGE = "/lms/book/book_list.xhtml?faces-redirect=true";
	protected static final String EDIT_BOOK_PAGE = "/lms/book/edit_book.xhtml?faces-redirect=true";
	protected static final String EDIT_BOOK_CONFIRMATION = "/lms/book/edit_book_confirmation.xhtml?faces-redirect=true";
	protected static final String CREATE_BOOK = "/lms/book/create_book.xhtml?faces-redirect=true";
	protected static final String ACCESS_DENIED = "Access denied";

	private static final String SERIAL_NUMBER = "book with the same serial number ";

	private static final String HAS_BEEN_CREATED = "has been captured before";

	protected static final String EXCEPTION_OCCURRED = "Exception occurred: ";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private transient BookService bookService;

	@Inject
	private Books book;

	private List<Books> bookList;

	private static Logger log = Logger.getLogger(BookController.class.getName());

	@PostConstruct
	public void initBookDetails() {
		book = new Books();
	}

	public String validateBookDetails() {

		List<Books> bookList = bookService.getAllBooks();
		if (bookList != null) {
			for (Books buk : bookList) {
				if (buk.getSerialNumber().equalsIgnoreCase(book.getSerialNumber())) {
					facesContext.getExternalContext().getFlash().setKeepMessages(true);
					facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Book with Serial Number, " + book.getSerialNumber() + ", " + HAS_BEEN_CREATED, null));
					return CREATE_BOOK;
				}

			}
		}
		return addBookDetails();

	}

	public String createBook() {
		initBookDetails();
		return CREATE_BOOK;
	}

	public String addBookDetails() {

		try {

			book.setBookStatus(BookStatus.AVAILABLE);
			book = bookService.addBookDetails(book);

			initBookDetails();
			bookList = null;
			return BOOK_LIST_PAGE;
		} catch (PersistenceException pe) {

			return handleException(pe, SERIAL_NUMBER + book + " " + HAS_BEEN_CREATED, facesContext);
		} catch (Exception e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				return handleException(e, "Record with the following has been found", facesContext);
			} else {
				return handleException(e, SERIAL_NUMBER + book.getSerialNumber() + HAS_BEEN_CREATED, facesContext);
			}

		}

	}

	public String updateBookDetails() {

		try {
			
			book = bookService.updateBookDetails(book);
			initBookDetails();
			bookList = null;
			return BOOK_LIST_PAGE;
		} catch (Exception e) {
			log.log(Level.INFO, "Unable to update book details" + e.getMessage(), e);
			return "";
		}

	}

	public String displaySelectedBook(Books book) {
		this.book = book;
		if (this.book != null) {
			this.book.getBookName();

		
		}
		return EDIT_BOOK_PAGE;
	}

	
	public String boorowBook() {
		try {
			book.setBookStatus(BookStatus.BORROWED);
			book = bookService.updateBookDetails(book);
			bookList = null;
			return BOOK_LIST_PAGE;
		} catch (Exception e) {
			log.log(Level.INFO, "Unable to update Inactive status" + e.getMessage(), e);
			return "";
		}
	}

	public String resrveBook() {
		try {
			book.setBookStatus(BookStatus.RESERVED);
			book = bookService.updateBookDetails(book);

			bookList = null;
			return BOOK_LIST_PAGE;
		} catch (Exception e) {
			log.log(Level.INFO, "Unable to update approved status" + e.getMessage(), e);
			return "";
		}
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	public Books getBook() {
		return book;
	}

	public void setBook(Books book) {
		this.book = book;
	}

	public List<Books> getBookList() {
	/*	if (bookList == null) {
			bookList = bookService.getAllBooks();

		} else {
			for (Books tmpBook : bookList) {
				System.out.println("book Name : " + tmpBook.getBookName());
			}
		}*/
		return bookList;
	}

	public void setBookList(List<Books> bookList) {
		this.bookList = bookList;
	}
}
