package fi.my.pkg;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

public class BookArchive {
	private final HashMap<String, Book> isbnToBookMap = new HashMap<String, Book>();
	private final LinkedList<Book> books = new LinkedList<Book>();
	private final HashMap<Id, Book> idToBookMap = new HashMap<Id, Book>();
	private final HashMap<Title, Book> titleToBookMap = new HashMap<Title, Book>();
	private final Storage storage;

	public BookArchive(Storage storage) {
		this.storage = storage;
		addAll(storage.selectClassicBooks());
		addAll(storage.selectPdfBooks());
	}

	public void saveAll() {
		for (Item tem : getAll()) {
			storage.addOrUpdate((Book) tem);
		}
	}

	public Book find(String isbn) {
		if (!isbnToBookMap.containsKey(isbn)) {
			buildIsbnToBookMap();
		}
		return isbnToBookMap.get(isbn);
	}

	private void buildIsbnToBookMap() {
		for (Book b : getAll()) {
			isbnToBookMap.put(b.getIsbn(), b);
		}
	}

	public void push(Book item) {
		if (item == null)
			throw new NullPointerException();
		books.addLast(item);
		idToBookMap.put(item.getId(), item);
	}

	public Book pop() {
		if (books.isEmpty())
			return null;
		Book temp = books.removeLast();
		idToBookMap.remove(temp.getId());
		return temp;
	}

	public Book get(Id id) {
		return idToBookMap.get(id);
	}

	private void addAll(Collection<Book> itemsToAdd) {
		books.addAll(itemsToAdd);
	}

	private Iterable<Book> getAll() {
		return books;
	}

	public int size() {
		return books.size();
	}

	public Book find(Title title) {
		if (!titleToBookMap.containsKey(title)) {
			buildTitleToBookMap();
		}
		return titleToBookMap.get(title);
	}

	private void buildTitleToBookMap() {
		for (Book b : getAll()) {
			titleToBookMap.put(b.getTitle(), b);
		}
	}

}
