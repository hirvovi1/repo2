package fi.my.pkg.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import fi.my.pkg.dependents.AudioBook;
import fi.my.pkg.dependents.Book;
import fi.my.pkg.dependents.PdfBook;

public class Import {

	private static final String APIKEY = "AIzaSyA1m77icfaklkQnM4WV-tewHGV8nEEzBFY";
	private static final String GOOGLE_BOOK_SERVICE_URL = "https://www.googleapis.com/books/v1/volumes";

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
		try {
			final String query = "q=isbn:" + isbn + "&key=" + APIKEY;
			return findBookTitleWithGoogleBookService(query);
		} catch (Exception e) {
			System.out.println("service call failed: " + e);
			e.printStackTrace();
		}
		return "";
	}

	private String findBookTitleWithGoogleBookService(String query) throws IOException, MalformedURLException {
		HttpURLConnection connection = getServiceConnection(query);
		final int responseCode = connection.getResponseCode();
		if (!isResponceCodeOK(responseCode)) {
			System.out.println("service is not responding: " + responseCode);
			return "";
		}
		return getTitleForInputStream(connection.getInputStream());
	}

	private boolean isResponceCodeOK(int responseCode) {
		return responseCode == HttpURLConnection.HTTP_OK;
	}

	private HttpURLConnection getServiceConnection(String query) throws IOException, MalformedURLException {
		final URL serviceUrl = new URL(GOOGLE_BOOK_SERVICE_URL + "?" + query);
		HttpURLConnection connection = (HttpURLConnection) serviceUrl.openConnection();
		connection.setRequestProperty("Accept-Charset", "UTF-8");
		return connection;
	}

	private String getTitleForInputStream(InputStream response) {
		return new Bison().titleFromJson(response);
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
