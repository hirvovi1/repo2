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
	
	public Book findBook(Isbn isbn) {
		return ((BookArchive) archive).find(isbn);
	}
	
	public Book findBook(Id id) {
		return ((BookArchive) archive).find(id);
	}
	
	public Book findBook(Title title) {
		return  ((BookArchive) archive).find(title);
	}

	public void saveArchive() {
		archive.saveAll();
	}

	public int archiveSize() {
		return archive.size();
	}

	public void delete(Id id) {
		archive.delete(id);
	}
	
}
