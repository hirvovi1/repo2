package fi.my.pkg;

import org.apache.commons.validator.routines.ISBNValidator;
import org.bson.Document;

public class Book implements Item {
	private final Id id;
	private final String isbn;
	protected Title title;
	
	protected Book(Id id, String isbn, String title) {
		this.id = id;
		this.isbn = isbn;
		this.title = new Title(title);
		validate();
	}

	protected static Id id(Document document) {
		return new Id(document.get("id", String.class));
	}
	
	protected static String isbn(Document document) {
		return document.get("isbn", String.class);
	}
	
	protected void validate() {
		if (id == null) throw new IllegalArgumentException("invalid id");
		if (id.asLong() < 1) throw new IllegalArgumentException("invalid id " + id);
		if (!isValidISBN()) throw new IllegalArgumentException("invalid isbn " + isbn);
		if (title == null) throw new IllegalArgumentException("invalid title");
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
		d.append("id", id.toString()).append("isbn", isbn).append("title", title);
		return d;
	}

	protected static String title(Document document) {
		return document.get("title", String.class);
	}

	public Title getTitle() {
		return title;
	}
}
