package com.lms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class Books implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final String EMPTY_MESSAGE = "May not be empty";

   
	private String _id;
	private String _rev;
    private Date datePublished;
    
    @NotEmpty(message = EMPTY_MESSAGE)
    @Length(max = 60, message = "May not be greater than 1000 characters")
    @Pattern(regexp = "^[a-zA-Z0-9() ]*$", message = "May not contain the special characters")
    private String bookName;

    @NotEmpty(message = EMPTY_MESSAGE)
    private String author;

    @NotEmpty(message = EMPTY_MESSAGE)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Date getDatePublished() {
		return datePublished;
	}

	public void setDatePublished(Date datePublished) {
		this.datePublished = datePublished;
	}

	public BookStatus getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(BookStatus bookStatus) {
		this.bookStatus = bookStatus;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_rev() {
		return _rev;
	}

	public void set_rev(String _rev) {
		this._rev = _rev;
	}

	
}
