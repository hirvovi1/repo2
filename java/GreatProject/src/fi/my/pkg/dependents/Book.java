package fi.my.pkg.dependents;

import org.apache.commons.validator.routines.ISBNValidator;
import org.bson.Document;

public class Book implements Item {
	private Id id;
	private final Isbn isbn;
	protected Title title;
	
	protected Book(String isbn, String title) {
		this.id = null;
		this.isbn = new Isbn(isbn);
		this.title = new Title(title);
		System.out.println("new Book " + toString());
	}
	
	protected Book(int id, String isbn, String title) {
		this.id = new Id(id);
		this.isbn = new Isbn(isbn);
		this.title = new Title(title);
		validate();
	}

	public void setId(Id id) {
		this.id = id;
	}
	
	protected static int id(Document document) {
		return Integer.parseInt(document.get("id", String.class));
	}
	
	protected static String isbn(Document document) {
		return document.get("isbn", String.class);
	}
	
	protected void validate() {
		if (id == null) throw new IllegalArgumentException("invalid id");
		if (id.asInt() < 1) throw new IllegalArgumentException("invalid id " + id);
		if (!isValidISBN()) throw new IllegalArgumentException("invalid isbn " + isbn);
	}
	
	public Document createDocument() {
		Document d = new Document();
		d.append("id", id.toString()).append("isbn", isbn.getIsbn()).append("title", title.getTitle());
		return d;
	}

	public Isbn getIsbn() {
		return isbn;
	}

	@Override
	public String toString() {
		return "id: " + id + " isbn: " + isbn + " title: " + title;
	}

	@Override
	public Id getId() {
		return id;
	}
	
	private boolean isValidISBN() {
		return new ISBNValidator().isValid(isbn.getIsbn());
	}
	
	protected static String title(Document document) {
		return document.get("title", String.class);
	}

	public Title getTitle() {
		return title;
	}
}
