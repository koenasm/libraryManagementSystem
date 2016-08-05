package com.lms.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.lightcouch.CouchDbClient;
import org.lightcouch.Response;

import com.google.gson.JsonObject;
import com.lms.model.Student;

@ApplicationScoped
public class StudentRepository {

	CouchDbClient dbClient = new CouchDbClient("studentdb.properties");
	Response resp;
	JsonObject object = new JsonObject();

	public Student persistStudentDetails(Student newStudent) {

		object.addProperty("_id", newStudent.get_id());
		object.addProperty("_rev", newStudent.get_rev());
		object.addProperty("studentName", newStudent.getStudentName());
		object.addProperty("surname", newStudent.getSurname());
		object.addProperty("address", newStudent.getAddress());
		object.addProperty("identifier", newStudent.getIdentifier());

		try {
			resp = dbClient.save(object);
		} catch (org.lightcouch.DocumentConflictException e) {

		}

		dbClient.shutdown();
		return newStudent;

	}

	public List<Student> findAllStudent() {
		List<Student> allDocs = dbClient.view("_all_docs").includeDocs(true).query(Student.class);
		System.out.println("size" + allDocs.size());
		return allDocs;
	}

	List<Student> list = dbClient.view("example/foo").includeDocs(true).startKey("start-key").endKey("end-key")
			.limit(10).query(Student.class);

	public List<Student> allDocs() {
		List<Student> allDocs = dbClient.view("_all_docs").query(Student.class);
		System.out.println("size" + allDocs.size());
		return allDocs;

	}

	public Student updateStudentDetails(Student student) {
		// TODO Auto-generated method stub
		return null;
	}

}
