package fi.my.pkg;

import fi.my.pkg.dependents.ClassicBook;

public class Main {

	public static void main(String[] args) {
		BookRobot br = new BookRobot(new Storage());
		ClassicBook book = TestDataUtil.addTestBookToArchive(br);
		book.printPages();
	}

}
