package fi.my.pkg;

public class BookArchive extends Archive {

	public BookArchive(Storage storage) {
		super(storage);
	}

	@Override
	public void loadAll() {
		addAll(storage.select(" * from books"));
	}

	@Override
	public void saveAll() {
		for (Item tem : getAll()) {
			storage.addOrUpdate((Book)tem);
		}
	}

}
