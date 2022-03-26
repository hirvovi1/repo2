package fi.my.pkg.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

final class Bison {
	private final Gson gson = new Gson();

	private class Library implements Serializable {
		private static final long serialVersionUID = 1L;
		private List<Item> items = new LinkedList<Item>();
		
		private String findTitle(String isbn) {
			for (Item item : items) {// brute force, O(n)
				if (item.isbn.equals(isbn)) {
					return item.title;
				}
			}
			return null;
		}
	}
	
	private class Item implements Serializable {
		private static final long serialVersionUID = 1L;
		private String isbn;
		private String title;
	}
	
	String getTitle(String isbn) {
		File f = new File("./test/import/books.json");
		try {
			Library library = libraryFromJson(new FileInputStream(f));
			final String title = library.findTitle(isbn);
			return (title == null ? "title missing" : title);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return "title missing";
	}

	private Library libraryFromJson(InputStream response) {
		final InputStreamReader reader = new InputStreamReader(response, Charset.defaultCharset());
		JsonReader jsonReader = new JsonReader(new BufferedReader(reader));
		return gson.fromJson(jsonReader, Library.class);
	}

}
