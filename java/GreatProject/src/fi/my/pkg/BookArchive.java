package fi.my.pkg;

import java.util.HashMap;

public class BookArchive extends Archive {
	
	private HashMap<String, Book> index = new HashMap<String, Book>();

	public BookArchive(Storage storage) {
		super(storage);
	}

	@Override
	public void loadAll() {
		addAll(storage.select(" * from books"));
	}

	private void buildIndex() {
		for (Item tem : getAll()) {
			Book b = (Book)tem;
			index.put(b.getIsbn(), b);
		}
	}

	@Override
	public void saveAll() {
		for (Item tem : getAll()) {
			storage.addOrUpdate((Book)tem);
		}
	}
	
	public Book find(String isbn) {
		if (!index.containsKey(isbn)) {
			buildIndex();
		}
		return index.get(isbn);
	}

}
