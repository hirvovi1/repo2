package fi.my.pkg;

import org.apache.commons.validator.routines.ISBNValidator;
import org.bson.Document;

public class Book implements Item {
	protected static Id id(Document document) {
		return new Id(document.get("id", String.class));
	}

	protected static String isbn(Document document) {
		return document.get("isbn", String.class);
	}

	private final Id id;
	private final String isbn;

	protected Book(Id id, String isbn) {
		this.id = id;
		this.isbn = isbn;
		validate();
	}

	protected void validate() {
		if (id == null) throw new IllegalArgumentException("invalid id");
		if (id.asLong() < 1) throw new IllegalArgumentException("invalid id");
		if (!isValidISBN()) throw new IllegalArgumentException("invalid isbn " + isbn);
	}

	public String getIsbn() {
		return isbn;
	}

	@Override
	public String toString() {
		return "id: " + id + " isbn: " + isbn;
	}

	@Override
	public Id getId() {
		return id;
	}
	
	private boolean isValidISBN() {
		return new ISBNValidator().isValid(isbn);
	}

	public Document createDocument() {
		Document d = new Document();
		d.append("id", getId().toString()).append("isbn", getIsbn());
		return d;
	}
}
