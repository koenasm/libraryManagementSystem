package com.lms.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.lms.data.ShelfRepository;
import com.lms.model.Shelf;

@Stateless
public class ShelfService {
	@Inject
	private ShelfRepository shelfRepository;

	public Shelf addShelfDetails(Shelf shelf) {
		return shelfRepository.persistShelfDetails(shelf);
	}

	public Shelf updateShelfDetails(Shelf shelf) {
		return shelfRepository.updateShelfDetails(shelf);
	}

	
	public List<Shelf> getAllShelves() {
		return shelfRepository.findAllShelves();

	}

	public ShelfRepository getShelfRepository() {
		return shelfRepository;
	}

	public void setShelfRepository(ShelfRepository shelfRepository) {
		this.shelfRepository = shelfRepository;
	}

	


}
