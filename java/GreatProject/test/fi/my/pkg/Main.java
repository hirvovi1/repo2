package fi.my.pkg;

import fi.my.pkg.dependents.ClassicBook;
import fi.my.pkg.service.BookService;
import fi.my.pkg.storage.Storage;

public class Main {

	public static void main(String[] args) {
		BookService br = new BookService(new Storage());
		ClassicBook book = TestDataUtil.addTestBookToArchive(br);
		book.printPages();
	}

}
