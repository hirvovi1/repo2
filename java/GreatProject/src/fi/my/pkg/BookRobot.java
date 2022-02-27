package fi.my.pkg;


public class BookRobot extends Robot {
	
	public BookRobot() {
		super.archive = new BookArchive();
		archive.loadAll();
	}
	
	public void addBookToArchive(Book book) {
		archive.push(book);
	}
	
	public Book getNewestBookFromArchive() {
		return (Book) archive.pop();
	}

}
