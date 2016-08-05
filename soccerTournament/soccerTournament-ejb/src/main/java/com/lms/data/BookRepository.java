package com.lms.data;

import java.util.List;

import org.lightcouch.CouchDbClient;
import org.lightcouch.Response;

import com.google.gson.JsonObject;
import com.lms.model.Books;

public class BookRepository {

	CouchDbClient dbClient = new CouchDbClient("bookdb.properties");
	Response resp;
	JsonObject object = new JsonObject();

	Books book = new Books();

	public Books persistBookDetails(Books newBook) {

		object.addProperty("_id", newBook.get_id());
		object.addProperty("_rev", newBook.get_rev());
		object.addProperty("bookName", newBook.getBookName());
		object.addProperty("author", newBook.getAuthor());
		object.addProperty("serialNumber", newBook.getSerialNumber());
		object.addProperty("bookStatus", newBook.getBookStatus().toString());

		try {
			resp = dbClient.save(object);
		} catch (org.lightcouch.DocumentConflictException e) {

		}

		dbClient.shutdown();
		return newBook;

	}

	public Books updateBookDetails(Books book) {
		dbClient.update(book);
		return book;
	}

	public List<Books> findAllBooks() {
		List<Books> allDocs = dbClient.view("_all_docs").includeDocs(true).query(Books.class);
		System.out.println("size" + allDocs.size());
		return allDocs;
	}

	// start test
	List<Books> list = dbClient.view("example/foo").includeDocs(true).startKey("start-key").endKey("end-key").limit(10)
			.query(Books.class);

	public List<Books> allDocs() {
		List<Books> allDocs = dbClient.view("_all_docs").query(Books.class);
		System.out.println("size" + allDocs.size());
		return allDocs;

	}
	// end Test
}
