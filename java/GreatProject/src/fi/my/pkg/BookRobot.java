package fi.my.pkg;


public class BookRobot extends Robot {
	
	public BookRobot(Storage storage) {
		super.archive = new BookArchive(storage);
		loadArchive();	
	}

	public void addBookToArchive(Book book) {
		archive.push(book);
	}
	
	public Book getNewestBookFromArchive() {
		return (Book) archive.pop();
	}
	
}
