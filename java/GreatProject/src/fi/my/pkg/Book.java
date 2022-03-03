package fi.my.pkg;

import org.apache.commons.validator.routines.ISBNValidator;

public class Book implements Item {
	private final Id id;
	private final String isbn;

	public Book(Id id, String isbn) {
		this.id = id;
		this.isbn = isbn;
		if (!isValidISBN()) throw new RuntimeException("invalid isbn");
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
}
