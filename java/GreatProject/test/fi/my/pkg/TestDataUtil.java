package fi.my.pkg;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TestDataUtil {

	static void createPages(List<Page> list) {
		for (int i = 1; i <= 1000; i++) {
			list.add(new Page(i, Arrays.asList("line1", "line2", "line3")));
		}
	}

	static void add500Books(Storage storage) {
		for (int i = 1; i <= 500; i++) {
			storage.addOrUpdate(new ClassicBook(i, "978-3-16-148410-0"));
		}
	}

	static ClassicBook addTestBookToArchive(BookRobot br) {
		ClassicBook book = new ClassicBook(1, "q1222-3353-4363-234553");
		br.addBookToArchive(book);
		addTestPages(book.getPages());
		return book;
	}

	static void addTestPages(List<Page> pages) {
		pages.add(new Page(1, getLines()));
	}

	static List<String> getLines() {
		LinkedList<String> lines = new LinkedList<String>();
		lines.add("line 1");
		lines.add("line 2");
		lines.add("line 3");
		lines.add("line 4");
		return lines;
	}

}
