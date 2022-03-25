package fi.my.pkg.service;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;

import fi.my.pkg.dependents.AudioBook;
import fi.my.pkg.dependents.Book;
import fi.my.pkg.dependents.PdfBook;

public class Import {

	public List<Book> importPdfBooks() throws Exception {
		return importBooks(PdfBook.class, "./test/import/pdf");
	}

	private List<Book> importBooks(Class<? extends Book> clazz, String dir) throws Exception {
		File f = new File(dir);
		File[] files = f.listFiles();
		List<Book> books = new LinkedList<Book>();

		for (File file : files) {
			System.out.println("" + file.getCanonicalPath());
			String isbn = getIsbnFromFileName(file);
			String title = getTitle(isbn);
			books.add(createBook(clazz, isbn, title, file));
		}
		return books;
	}

	private String getTitle(String isbn) {
		return "title";
	}

	private Book createBook(Class<? extends Book> clazz, String isbn, String title, File file) throws Exception {
		Constructor<? extends Book> constr = clazz.getConstructor(String.class, String.class, String.class);
		return constr.newInstance(isbn, title, file.getCanonicalPath());
	}

	private String getIsbnFromFileName(File file) {
		final String name = file.getName();
		return name.substring(0, name.indexOf("."));
	}

	public List<Book> importAudioBooks() throws Exception {
		return importBooks(AudioBook.class, "./test/import/audio");
	}

}
