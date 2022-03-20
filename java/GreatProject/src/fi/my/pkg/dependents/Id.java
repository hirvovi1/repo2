package fi.my.pkg.dependents;

public class Id {
	private Integer id;

	public Id(int id) {
		this.id = Integer.valueOf(id);
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
		return id.intValue();
	}

}
