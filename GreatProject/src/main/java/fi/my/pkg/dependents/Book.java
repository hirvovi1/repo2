package fi.my.pkg.dependents;

import org.bson.Document;

public class Book implements Item {
	private Id id;
	private final Isbn isbn;
	protected Title title;
	
	protected Book(String isbn, String title) {
		this.id = null;
		this.isbn = new Isbn(isbn);
		this.title = new Title(title);
		System.out.println("new Book " + this);
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
		if (id.asInt() < 1) throw new IllegalArgumentException("invalid id " + id);
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
	
	protected static String title(Document document) {
		return document.get("title", String.class);
	}

	public Title getTitle() {
		return title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		if (title == null) {
			return other.title == null;
		} else return title.equals(other.title);
	}
}
