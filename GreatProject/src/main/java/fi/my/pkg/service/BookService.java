package fi.my.pkg.service;

import java.util.List;

import fi.my.pkg.dependents.Book;
import fi.my.pkg.dependents.Id;
import fi.my.pkg.dependents.Isbn;
import fi.my.pkg.dependents.Title;

public interface BookService {

	void addBookToArchive(Book book);

	int archiveSize();

	void delete(Book book);

	void delete(Isbn id);

	Book findBook(Id id);

	Book findBook(Isbn isbn);

	Book findBook(Title title);

	void importBooks(String importPath) throws Exception;

	void saveArchive();

	Book undoLastAddBookToArchive();

	List<Book> listBooks();

}