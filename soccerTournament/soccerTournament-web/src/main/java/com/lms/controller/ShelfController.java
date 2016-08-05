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

import com.lms.model.Shelf;
import com.lms.service.ShelfService;
@ManagedBean
@SessionScoped
public class ShelfController extends AbstractController {

	protected static final String SHELF_LIST_PAGE = "/lms/shelf/shelf_list.xhtml?faces-redirect=true";
	protected static final String EDIT_SHELF_PAGE = "/lms/shelf/edit_shelf.xhtml?faces-redirect=true";
	protected static final String EDIT_SHELF_CONFIRMATION = "/lms/shelf/edit_shelf_confirmation.xhtml?faces-redirect=true";
	protected static final String CREATE_SHELF = "/lms/shelf/create_shelf.xhtml?faces-redirect=true";
	protected static final String ACCESS_DENIED = "Access denied";

	private static final String SHELF_NAME = "shelf with the name ";

	private static final String HAS_BEEN_CREATED = "has been captured before";

	protected static final String EXCEPTION_OCCURRED = "Exception occurred: ";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private transient ShelfService shelfService;

	@Inject
	private Shelf shelf;

	private List<Shelf> shelfList;

	private static Logger log = Logger.getLogger(ShelfController.class.getName());

	@PostConstruct
	public void initShelfDetails() {
		shelf = new Shelf();
	}

	public String validateShelfDetails() {

		List<Shelf> shelfList = shelfService.getAllShelves();
		if (shelfList != null) {
			for (Shelf buk : shelfList) {
				if (buk.getShelfName().equalsIgnoreCase(shelf.getShelfName())) {
					facesContext.getExternalContext().getFlash().setKeepMessages(true);
					facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"Shelf with Name, " + shelf.getShelfName() + ", " + HAS_BEEN_CREATED, null));
					return CREATE_SHELF;
				}

			}
		}
		return addShelfDetails();

	}

	public String createShelf() {
		initShelfDetails();
		return CREATE_SHELF;
	}

	public String addShelfDetails() {

		try {

			shelf = shelfService.addShelfDetails(shelf);

			initShelfDetails();
			shelfList = null;
			return SHELF_LIST_PAGE;
		} catch (PersistenceException pe) {

			return handleException(pe, SHELF_NAME + shelf + " " + HAS_BEEN_CREATED, facesContext);
		} catch (Exception e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				return handleException(e, "Record with the following has been found", facesContext);
			} else {
				return handleException(e, SHELF_NAME + shelf.get_id() + HAS_BEEN_CREATED, facesContext);
			}

		}

	}

	public String updateShelfDetails() {

		try {
			shelf = shelfService.updateShelfDetails(shelf);
			initShelfDetails();
			shelfList = null;
			return SHELF_LIST_PAGE;
		} catch (Exception e) {
			log.log(Level.INFO, "Unable to update shelf details" + e.getMessage(), e);
			return "";
		}

	}

	public String displaySelectedShelf(Shelf shelf) {

		this.shelf = shelf;
		if (this.shelf != null) {
			this.shelf.getShelfName();
		}
		return EDIT_SHELF_PAGE;
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	public List<Shelf> getShelfList() {
		if (shelfList == null) {
			shelfList = shelfService.getAllShelves();

		} else {
			for (Shelf tmpShelf : shelfList) {
				System.out.println("shelf Name : " + tmpShelf.getShelfName());
			}
		}
		return shelfList;
	}

	public ShelfService getShelfService() {
		return shelfService;
	}

	public void setShelfService(ShelfService shelfService) {
		this.shelfService = shelfService;
	}

	public Shelf getShelf() {
		return shelf;
	}

	public void setShelf(Shelf shelf) {
		this.shelf = shelf;
	}

	public void setShelfList(List<Shelf> shelfList) {
		this.shelfList = shelfList;
	}

}
