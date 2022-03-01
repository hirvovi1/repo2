package fi.my.pkg;

public class Book implements Item {

	private final Id id;

	public Book(Id id) {
		this.id = id;
	}

	@Override
	public Id getId() {
		return id;
	}

}
