package fi.my.pkg.dependents;

import org.apache.commons.validator.routines.ISBNValidator;

public class Isbn {

	private final String isbn;

	public Isbn(String isbn) {
		this.isbn = isbn;
		validate();
	}

	private void validate() {
		if (!new ISBNValidator().isValid(isbn)) {
			throw new IllegalArgumentException("invalid isbn " + isbn);
		}
	}

	public String getIsbn() {
		return isbn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
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
		Isbn other = (Isbn) obj;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Isbn [isbn=" + isbn + "]";
	}

}
