package fi.my.pkg.dependents;

public class Id {
	private Long id;

	public Id(long id) {
		this.id = Long.valueOf(id);
	}

	public Id(String string) {
		this.id = Long.parseLong(string);
	}

	public long asLong() {
		return id.longValue();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Id)) return false;
		return ((Id) obj).asLong() == id;
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
		return id.intValue();
	}

}
