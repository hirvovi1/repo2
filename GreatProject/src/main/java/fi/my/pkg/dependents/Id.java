package fi.my.pkg.dependents;

import java.io.Serializable;

public class Id implements Serializable {

	private static final long serialVersionUID = 1L;
	private final Integer id;

	public Id(int id) {
		this.id = id;
	}

	public Id(String string) {
		this.id = Integer.parseInt(string);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Id)) return false;
		return ((Id) obj).asInt() == id;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return id.toString();
	}

	public int asInt() {
		return id;
	}

	public Integer getId() {
		return id;
	}

}
