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

import com.lms.model.Staff;
import com.lms.service.StaffService;
@ManagedBean
@SessionScoped
public class StaffController extends AbstractController {

	protected static final String STAFF_LIST_PAGE = "/lms/staff/staff_list.xhtml?faces-redirect=true";
	protected static final String EDIT_STAFF_PAGE = "/lms/staff/edit_staff.xhtml?faces-redirect=true";
	protected static final String CREATE_STAFF = "/lms/staff/create_staff.xhtml?faces-redirect=true";
	protected static final String ACCESS_DENIED = "Access denied";

	private static final String STAFF_ID = "staff with the id ";

	private static final String HAS_BEEN_CREATED = "has been captured before";

	protected static final String EXCEPTION_OCCURRED = "Exception occurred: ";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private transient StaffService staffService;

	@Inject
	private Staff staff;

	private List<Staff> staffList;

	private static Logger log = Logger.getLogger(StaffController.class.getName());

	@PostConstruct
	public void initStaffDetails() {
		staff = new Staff();
	}

	public String validateStaffDetails() {

		List<Staff> staffList = staffService.getAllStaff();
		if (staffList != null) {
			for (Staff stf : staffList) {
				if (stf.getIdentifier().equalsIgnoreCase(staff.getIdentifier())) {
					facesContext.getExternalContext().getFlash().setKeepMessages(true);
					facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Staff with id Number, " + staff.getIdentifier() + ", " + HAS_BEEN_CREATED, null));
					return CREATE_STAFF;
				}

			}
		}
		return addStaffDetails();

	}

	public String createStaff() {
		initStaffDetails();
		return CREATE_STAFF;
	}

	public String addStaffDetails() {

		try {

			staff = staffService.addStaffDetails(staff);

			initStaffDetails();
			staffList = null;
			return STAFF_LIST_PAGE;
		} catch (PersistenceException pe) {

			return handleException(pe, STAFF_ID + staff + " " + HAS_BEEN_CREATED, facesContext);
		} catch (Exception e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				return handleException(e, "Record with the following has been found", facesContext);
			} else {
				return handleException(e, STAFF_ID + staff.get_id() + HAS_BEEN_CREATED, facesContext);
			}

		}

	}

	public String updateStaffDetails() {

		try {
			staff = staffService.updateStaffDetails(staff);
			initStaffDetails();
			staffList = null;
			return STAFF_LIST_PAGE;
		} catch (Exception e) {
			log.log(Level.INFO, "Unable to update staff details" + e.getMessage(), e);
			return "";
		}

	}

	public String displaySelectedStaff(Staff staff) {
		this.staff = staff;
		if (this.staff != null) {
			this.staff.getStaffName();

		}
		return EDIT_STAFF_PAGE;
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	public List<Staff> getStaffList() {
		if (staffList == null) {
			staffList = staffService.getAllStaff();
		}
		return staffList;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public void setStaffList(List<Staff> staffList) {
		this.staffList = staffList;
	}

}
