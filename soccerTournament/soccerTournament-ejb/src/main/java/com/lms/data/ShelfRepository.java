package com.lms.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.lightcouch.CouchDbClient;
import org.lightcouch.Response;

import com.google.gson.JsonObject;
import com.lms.model.Shelf;

@ApplicationScoped
public class ShelfRepository {

	CouchDbClient dbClient = new CouchDbClient("shelfdb.properties");
	Response resp;
	JsonObject object = new JsonObject();

	public Shelf persistShelfDetails(Shelf newShelf) {

		object.addProperty("_id", newShelf.get_id());
		object.addProperty("_rev", newShelf.get_rev());
		object.addProperty("shelfName", newShelf.getShelfName());
		object.addProperty("area", newShelf.getArea());
		object.addProperty("capacity", newShelf.getCapacity());

		try {
			resp = dbClient.save(object);
		} catch (org.lightcouch.DocumentConflictException e) {

		}

		dbClient.shutdown();
		return newShelf;

	}

	public List<Shelf> findAllShelves() {
		List<Shelf> allDocs = dbClient.view("_all_docs").includeDocs(true).query(Shelf.class);
		System.out.println("size" + allDocs.size());
		return allDocs;
	}

	public Shelf updateShelfDetails(Shelf shelf) {
		// TODO Auto-generated method stub
		return null;
	}

}
