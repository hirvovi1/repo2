package fi.my.pkg;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ClassicBook extends Book {

	private final List<Page> pages = new LinkedList<Page>();

	public ClassicBook(Id id, String isbn) {
		super(id, isbn);
	}

	public List<Page> getPages() {
		return pages;
	}

	public void printPages() {
		for (Iterator<Page> iterator = pages.iterator(); iterator.hasNext();) {
			Page page = (Page) iterator.next();
			page.print();
		}
	}

}
