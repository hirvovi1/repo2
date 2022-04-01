package fi.my.pkg.service;

import fi.my.pkg.dependents.Book;
import fi.my.pkg.dependents.Id;
import fi.my.pkg.dependents.Isbn;
import fi.my.pkg.dependents.Title;

public interface BookService {

	void addBookToArchive(Book book);


	Book findBook(Isbn isbn);

	Book findBook(Id id);

	Book findBook(Title title);

	void saveArchive();

	int archiveSize();

	void delete(Isbn id);

	void importBooks() throws Exception;

}