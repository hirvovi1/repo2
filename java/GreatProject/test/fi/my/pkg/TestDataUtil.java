package fi.my.pkg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import fi.my.pkg.dependents.ClassicBook;
import fi.my.pkg.dependents.Page;
import fi.my.pkg.service.BookService;
import fi.my.pkg.storage.Storage;

public class TestDataUtil {

	static void createPages(List<Page> list) {
		for (int i = 1; i <= 1000; i++) {
			list.add(new Page(i, Arrays.asList("line1", "line2", "line3")));
		}
	}

	static void addBooks(int howMany, Storage storage) {
		for (int i = 1; i <= howMany; i++) {
			storage.addOrUpdate(new ClassicBook(i, "978-3-16-148410-0", "title"));
		}
	}

	static ClassicBook addTestBookToArchive(BookService br) {
		ClassicBook book = new ClassicBook(1, "q1222-3353-4363-234553", "title");
		br.addBookToArchive(book);
		addTestPages(book.getPages());
		return book;
	}

	static void addTestPages(List<Page> pages) {
		pages.add(new Page(1, getLines()));
	}

	public static List<String> getLines() {
		LinkedList<String> lines = new LinkedList<String>();
		lines.add("line 1");
		lines.add("line 2");
		lines.add("line 3");
		lines.add("line 4");
		return lines;
	}

	static List<String> loadTestIsbnList() throws IOException{
		FileReader input = new FileReader("./test/testdata.txt");
		BufferedReader reader = new BufferedReader(input);
		List<String> list = new LinkedList<String>();
		String line = reader.readLine();
		while (line != null) {
			list.add(line);
			line = reader.readLine();
		}
		reader.close();
		return list;
	}
}
