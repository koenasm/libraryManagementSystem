package com.lms.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class Shelf implements Serializable {

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
    private String shelfName;

    @NotEmpty(message = EMPTY_MESSAGE)
    private String area;

    @NotEmpty(message = EMPTY_MESSAGE)
    private String capacity;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getShelfName() {
		return shelfName;
	}

	public void setShelfName(String shelfName) {
		this.shelfName = shelfName;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String get_rev() {
		return _rev;
	}

	public void set_rev(String _rev) {
		this._rev = _rev;
	}

   
	

	
}
