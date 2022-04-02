package fi.my.pkg.service;

import fi.my.pkg.dependents.Book;
import fi.my.pkg.dependents.Id;
import fi.my.pkg.dependents.Isbn;
import fi.my.pkg.dependents.Title;
import fi.my.pkg.service.util.Import;
import fi.my.pkg.storage.BookArchive;
import fi.my.pkg.storage.Storage;

public class BookServiceImpl implements BookService {
	
	private final BookArchive archive;

	public BookServiceImpl(Storage storage) throws Exception {
		archive = new BookArchive(storage);
	}

	@Override
	public void addBookToArchive(Book book) {
		archive.push(book);
	}
	

	
	@Override
	public Book findBook(Isbn isbn) {
		return archive.find(isbn);
	}
	
	@Override
	public Book findBook(Id id) {
		return archive.find(id);
	}
	
	@Override
	public Book findBook(Title title) {
		return archive.find(title);
	}

	@Override
	public void saveArchive() {
		archive.saveChanges();
	}

	@Override
	public int archiveSize() {
		return archive.size();
	}

	@Override
	public void delete(Isbn id) {
		archive.delete(id);
	}
	
	@Override
	public void importBooks() throws Exception {
		final Import bookImport = new Import();
		archive.addAll(bookImport.importAudioBooks());
		archive.addAll(bookImport.importPdfBooks());
		archive.saveChanges();
	}
	
}
