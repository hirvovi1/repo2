package fi.my.pkg;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

public class BookArchive {
	private final HashMap<String, Book> index = new HashMap<String, Book>();
	private final LinkedList<Book> books = new LinkedList<Book>();
	private final HashMap<Id, Book> idToBookMap = new HashMap<Id, Book>();
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
		if (!index.containsKey(isbn)) {
			buildIndex();
		}
		return index.get(isbn);
	}

	private void buildIndex() {
		System.out.println("building index");
		for (Book b : getAll()) {
			index.put(b.getIsbn(), b);
			System.out.print("id: " + b.getId() + " " + b.getIsbn() + " ");
		}
		System.out.println("\nbuilding index ended.");
	}

	public void push(Book item) {
		if (item == null)
			throw new NullPointerException();
		books.addLast(item);
		idToBookMap.put(item.getId(), item);
	}

	public Book pop() {
		if (books.isEmpty()) return null;
		Book temp = books.removeLast();
		idToBookMap.remove(temp.getId());
		return temp;
	}

	public Item get(Id id) {
		return idToBookMap.get(id);
	}

	protected void addAll(Collection<Book> itemsToAdd) {
		books.addAll(itemsToAdd);
	}

	protected Iterable<Book> getAll() {
		return books;
	}

	public int size() {
		return books.size();
	}

}
