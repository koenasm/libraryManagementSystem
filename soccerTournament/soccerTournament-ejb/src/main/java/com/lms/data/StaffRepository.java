package com.lms.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.lightcouch.CouchDbClient;
import org.lightcouch.Response;

import com.google.gson.JsonObject;
import com.lms.model.Staff;

@ApplicationScoped
public class StaffRepository {

	CouchDbClient dbClient = new CouchDbClient("stuffdb.properties");
	Response resp;
	JsonObject object = new JsonObject();

	public Staff persistStaffDetails(Staff newStaff) {

		object.addProperty("_rev", newStaff.get_rev());
		object.addProperty("_id", newStaff.get_id());
		object.addProperty("staffName", newStaff.getStaffName());
		object.addProperty("surname", newStaff.getSurname());
		object.addProperty("address", newStaff.getAddress());
		object.addProperty("identifier", newStaff.getIdentifier());

		try {
			resp = dbClient.save(object);
		} catch (org.lightcouch.DocumentConflictException e) {

		}

		dbClient.shutdown();
		return newStaff;

	}

	public List<Staff> findAllStaff() {
		List<Staff> allDocs = dbClient.view("_all_docs").includeDocs(true).query(Staff.class);
		System.out.println("size" + allDocs.size());
		for (Staff tmpStaff : allDocs) {
			System.out.println("staff Name in Repo : " + tmpStaff.getStaffName());
		}
		return allDocs;
	}

	public Staff updateStaffDetails(Staff staff) {
		dbClient.update(staff);
		return staff;
	}

}
