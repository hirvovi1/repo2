package fi.my.pkg;

import java.util.Collection;
import java.util.LinkedList;

public class BookArchive extends Archive {

	@Override
	public void loadAll() {
		addAll(read());
	}

	private Collection<Item> read() {
		return new LinkedList<Item>();
	}

	@Override
	public void saveAll() {
		write(getAll());
	}

	private void write(Iterable<Item> allItems) {
		for (Item tem : allItems) {
			write((Book)tem);
		}
	}
	
	private void write(Book b) {
		
	}

}
