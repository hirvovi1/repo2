package fi.my.pkg.service;

import fi.my.pkg.dependents.Book;
import fi.my.pkg.dependents.Id;
import fi.my.pkg.dependents.Isbn;
import fi.my.pkg.dependents.Title;
import fi.my.pkg.storage.BookArchive;
import fi.my.pkg.storage.Storage;

public class BookService {
	
	private final BookArchive archive;

	public BookService(Storage storage) throws Exception {
		archive = new BookArchive(storage);
	}

	public void addBookToArchive(Book book) {
		archive.push(book);
	}
	
	public Book getNewestBookFromArchive() {
		return archive.pop();
	}
	
	public Book findBook(Isbn isbn) {
		return archive.find(isbn);
	}
	
	public Book findBook(Id id) {
		return archive.find(id);
	}
	
	public Book findBook(Title title) {
		return archive.find(title);
	}

	public void saveArchive() {
		archive.saveAll();
	}

	public int archiveSize() {
		return archive.size();
	}

	public void delete(Isbn id) {
		archive.delete(id);
	}
	
}
