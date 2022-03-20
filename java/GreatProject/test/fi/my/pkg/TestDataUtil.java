package fi.my.pkg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import fi.my.pkg.dependents.AudioBook;
import fi.my.pkg.dependents.ClassicBook;
import fi.my.pkg.dependents.Page;
import fi.my.pkg.dependents.PdfBook;
import fi.my.pkg.dependents.PdfFileNotFoundException;
import fi.my.pkg.dependents.SoundFileNotFoundException;
import fi.my.pkg.service.BookService;
import fi.my.pkg.storage.Storage;

public class TestDataUtil {

	public static void createPages(List<Page> list) {
		for (int i = 1; i <= 100; i++) {
			list.add(new Page(i, Arrays.asList("line1", "line2", "line3")));
		}
	}

	public static void addClassicBooks(int howMany, Storage storage) {
		for (int i = 1; i <= howMany; i++) {
			ClassicBook book = new ClassicBook("978-3-16-148410-0", "title");
			createPages(book.getPages());
			storage.addOrUpdate(book);
		}
	}
	
	public static void addPdfBooks(int howMany, Storage storage) throws PdfFileNotFoundException {
		for (int i = 1001; i <= 1000 + howMany; i++) {
			storage.addOrUpdate(new PdfBook( "978-3-16-148410-0", "title", "./test/test.pdf"));
		}
	}

	public static void addAudioBooks(int howMany, Storage storage) throws SoundFileNotFoundException {
		for (int i = 2001; i <= 2000 + howMany; i++) {
			storage.addOrUpdate(new AudioBook( "978-3-16-148410-0", "title", "test/test.pdf"));
		}
	}

	static ClassicBook addTestBookToArchive(BookService br) {
		ClassicBook book = new ClassicBook("q1222-3353-4363-234553", "title");
		br.addBookToArchive(book);
		addTestPages(book.getPages());
		return book;
	}

	public static void addTestPages(List<Page> pages) {
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

	public static List<String> loadTestIsbnList() throws IOException{
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
