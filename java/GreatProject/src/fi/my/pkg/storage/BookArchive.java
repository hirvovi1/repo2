package fi.my.pkg.storage;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import fi.my.pkg.dependents.Book;
import fi.my.pkg.dependents.Id;
import fi.my.pkg.dependents.Isbn;
import fi.my.pkg.dependents.Item;
import fi.my.pkg.dependents.Title;

public class BookArchive {

	private final HashMap<Id, Book> idToBookMap = new HashMap<Id, Book>();
	private final HashMap<Title, Book> titleToBookMap = new HashMap<Title, Book>();
	private final HashMap<Isbn, Book> isbnToBookMap = new HashMap<Isbn, Book>();
	private final LinkedList<Book> books = new LinkedList<Book>();
	private final Storage storage;

	public BookArchive(Storage storage) throws Exception {
		this.storage = storage;
		addAll(storage.selectClassicBooks());
		addAll(storage.selectPdfBooks());
		addAll(storage.selectAudioBooks());
	}

	public void saveAll() {
		for (Item tem : getAll()) {
			storage.addOrUpdate((Book) tem);
		}
	}

	public Book find(Isbn isbn) {
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

	public void push(Book book) {
		if (book == null)
			throw new NullPointerException();
		books.addLast(book);
		idToBookMap.put(book.getId(), book);
		isbnToBookMap.put(book.getIsbn(), book);
		titleToBookMap.put(book.getTitle(), book);
	}

	public Book pop() {
		if (books.isEmpty())
			return null;
		Book book = books.removeLast();
		removeFromMaps(book);
		return book;
	}

	private void removeFromMaps(Book book) {
		idToBookMap.remove(book.getId());
		isbnToBookMap.remove(book.getIsbn());
		titleToBookMap.remove(book.getTitle());
	}

	public Book find(Id id) {
		if (!idToBookMap.containsKey(id)) {
			buildIdToBookMap();
		}
		return idToBookMap.get(id);
	}

	private void buildIdToBookMap() {
		for (Book b : getAll()) {
			idToBookMap.put(b.getId(), b);
		}
	}

	public void addAll(Collection<Book> itemsToAdd) {
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

	public void delete(Isbn isbn) {
		Book book = find(isbn);
		books.remove(book);
		removeFromMaps(book);
	}

}
