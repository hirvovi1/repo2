package fi.my.pkg;

public class Id {
	private Long id;

	public Id(long id) {
		this.id = Long.valueOf(id);
	}

	public long getId() {
		return id.longValue();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Id)) return false;
		return ((Id) obj).getId() == id;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return "id: "+ id;
	}

}
