package fi.my.pkg;

import java.util.LinkedList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		BookRobot br = new BookRobot(new Storage());
		ClassicBook book = addTestBookToArchive(br);
		book.printPages();
	}

	private static ClassicBook addTestBookToArchive(BookRobot br) {
		ClassicBook book = new ClassicBook(new Id(1), "q1222-3353-4363-234553");
		br.addBookToArchive(book);
		addTestPages(book.getPages());
		return book;
	}

	private static void addTestPages(List<Page> pages) {
		pages.add(new Page(1, getLines()));
	}

	private static List<String> getLines() {
		LinkedList<String> lines = new LinkedList<String>();
		lines.add("line 1");
		lines.add("line 2");
		lines.add("line 3");
		lines.add("line 4");
		return lines;
	}

}
