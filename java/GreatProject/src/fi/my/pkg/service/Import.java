package fi.my.pkg.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

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
			return parseTitle(findBookWithGoogleBookService(query));
		} catch (Exception e) {
			System.out.println("service call failed: " + e);
			e.printStackTrace();
		}
		return "";
	}

	private BufferedReader findBookWithGoogleBookService(String query) throws IOException, MalformedURLException {
		HttpURLConnection connection = getServiceConnection(query);
		if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
			System.out.println("service is not responding: " + connection.getResponseCode());
			return (BufferedReader) BufferedReader.nullReader();
		}
		return getReaderForInputStream(connection.getInputStream());
	}
	
	private HttpURLConnection getServiceConnection(String query) throws IOException, MalformedURLException {
		final URL serviceUrl = new URL(GOOGLE_BOOK_SERVICE_URL + "?" + query);
		HttpURLConnection connection = (HttpURLConnection) serviceUrl.openConnection();
		connection.setRequestProperty("Accept-Charset", "UTF-8");
		return connection;
	}

	private BufferedReader getReaderForInputStream(InputStream response) {
		return new BufferedReader(new InputStreamReader(response, Charset.defaultCharset()));
	}

	private class Items {
		private class Item {
			private class Volume {
				private String title;
			}
			private Volume volumeInfo;
		}
		private List<Item> items;
	}

	private String parseTitle(Reader reader) {
		Gson gson = new Gson();
		Items i = gson.fromJson(new JsonReader(reader), Items.class);
		return i.items.get(0).volumeInfo.title;
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
