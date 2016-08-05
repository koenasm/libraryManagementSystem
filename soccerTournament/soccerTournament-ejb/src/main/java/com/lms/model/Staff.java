package com.lms.model;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class Staff implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String EMPTY_MESSAGE = "May not be empty";

	
	private String _id;
	private String _rev;

	@NotEmpty(message = EMPTY_MESSAGE)
	@Length(max = 60, message = "May not be greater than 1000 characters")
	@Pattern(regexp = "^[a-zA-Z0-9() ]*$", message = "May not contain the special characters")
	private String staffName;

	@NotEmpty(message = EMPTY_MESSAGE)
	private String address;

	@NotEmpty(message = EMPTY_MESSAGE)
	private String surname;

	@NotEmpty(message = EMPTY_MESSAGE)
	private String identifier;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String get_rev() {
		return _rev;
	}

	public void set_rev(String _rev) {
		this._rev = _rev;
	}
}
