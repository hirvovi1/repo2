package fi.my.pkg;


public class BookRobot {
	
	private final BookArchive archive;

	public BookRobot(Storage storage) {
		archive = new BookArchive(storage);
	}

	public void addBookToArchive(Book book) {
		archive.push(book);
	}
	
	public Book getNewestBookFromArchive() {
		return (Book) archive.pop();
	}
	
	public Book findBook(String isbn) {
		return ((BookArchive) archive).find(isbn);
	}

	public void saveArchive() {
		archive.saveAll();
	}

	public int archiveSize() {
		return archive.size();
	}
	
}
