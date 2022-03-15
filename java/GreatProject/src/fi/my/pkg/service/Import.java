package fi.my.pkg.service;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;

import fi.my.pkg.dependents.AudioBook;
import fi.my.pkg.dependents.Book;
import fi.my.pkg.dependents.PdfBook;

public class Import {
	
	public List<Book> importPdfBooks( ) throws Exception {
		return importBooks(PdfBook.class, "./test/import/pdf");
	}

	private List<Book> importBooks(Class<? extends Book> clazz, String dir) throws Exception {
		File f = new File(dir);
		File[] files = f.listFiles();
		List<Book> books = new LinkedList<Book>();
		int id = 1;
		for (File file : files) {
			System.out.println("" + file.getCanonicalPath());
			String isbn = getIsbnFromFileName(file);
			String title = getTitle(isbn);
			books.add(createBook(clazz, id, isbn, title, file));
			id++;
		}
		return books;
	}

	private String getTitle(String isbn) {
		return "title";// TODO title
	}

	private Book createBook(Class<? extends Book> clazz, int id, String isbn, String title, File file)
			throws Exception 
	{
		Constructor<? extends Book> constr = 
			clazz.getConstructor(int.class, String.class, String.class, String.class);
		return constr.newInstance(id, isbn, title , file.getCanonicalPath());
	}

	private String getIsbnFromFileName(File file) {
		final String name = file.getName();
		return name.substring(0, name.indexOf("."));
	}

	public List<Book> importAudioBooks() throws Exception {
		return importBooks(AudioBook.class, "./test/import/audio");
	}

}
