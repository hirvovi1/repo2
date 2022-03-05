package fi.my.pkg;

import org.apache.commons.validator.routines.ISBNValidator;
import org.bson.Document;

public class Book implements Item {
	private final Id id;
	private final String isbn;

	public Book(Id id, String isbn) {
		this.id = id;
		this.isbn = isbn;
		if (!isValidISBN()) throw new IllegalArgumentException("invalid isbn");
	}

	public Book(Document document) {
		this.id = new Id(document.get("id", String.class));
		this.isbn = document.get("isbn", String.class);
	}

	public String getIsbn() {
		return isbn;
	}

	@Override
	public Id getId() {
		return id;
	}
	
	private boolean isValidISBN() {
		return new ISBNValidator().isValid(isbn);
	}

	public Document createDocument() {
		throw new RuntimeException("not implemented");
	}
}
