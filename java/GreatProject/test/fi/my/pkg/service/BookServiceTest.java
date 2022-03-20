package fi.my.pkg.service;

import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fi.my.pkg.TestDataUtil;
import fi.my.pkg.dependents.AudioBook;
import fi.my.pkg.dependents.Book;
import fi.my.pkg.dependents.ClassicBook;
import fi.my.pkg.dependents.Id;
import fi.my.pkg.dependents.Isbn;
import fi.my.pkg.dependents.PdfBook;
import fi.my.pkg.dependents.SoundFileNotFoundException;
import fi.my.pkg.dependents.Title;
import fi.my.pkg.storage.Storage;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

	@Mock
	Storage mockStorage;

	private BookService robot;

	@BeforeEach
	public void setUp() throws Exception {
		robot = new BookService(mockStorage);
	}

	@Test
	public void testAddBookToArchive() {
		robot.addBookToArchive(new ClassicBook("978-3-16-148410-0", "title"));
		Book b = robot.getNewestBookFromArchive();
		Assertions.assertNotNull(b);
	}

	@Test
	public final void testAddNullBookToArchive() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			robot.addBookToArchive(null);
		});
	}

	@Test
	public final void testGetNewestBookFromArchive() {
		robot.addBookToArchive(new ClassicBook("978-3-16-148410-0", "title"));
		Book b = new ClassicBook("978-3-16-148410-0", "title");
		robot.addBookToArchive(b);
		Assertions.assertEquals(b, robot.getNewestBookFromArchive());
	}

	@Test
	public final void testSaveArchive() {
		ClassicBook b = new ClassicBook("978-1-56581-231-4", "Crime and punishment");
		TestDataUtil.createPages(b.getPages());
		robot.addBookToArchive(b);
		robot.saveArchive();
		verify(mockStorage).addOrUpdate(ArgumentMatchers.eq(b));
	}

	@Test
	void testFindABookFromArchiveWithIsbn() throws Exception {
		loadTestPdfBooks();
		Isbn isbnToFind = new Isbn("978-1-56581-231-4");
		Book found = robot.findBook(isbnToFind);
		assertBookIsFound(isbnToFind, found);
	}

	private void loadTestPdfBooks() throws IOException {
		int id = 1;
		for (String isbn : TestDataUtil.loadTestIsbnList()) {
			robot.addBookToArchive(new PdfBook(isbn, "title", "test/test.pdf"));
		}
		Assertions.assertEquals(30, robot.archiveSize());
	}

	private void assertBookIsFound(Isbn isbnToFind, Book found) {
		Assertions.assertNotNull(found);
		Assertions.assertEquals(found.getIsbn(), isbnToFind);
//		Assertions.assertEquals(found.getId(), idToFind);
	}
	
	@Test
	void testFindABookFromArchiveWithTitle() throws Exception {
		addTestBooksWithTestIsbnList();
		Assertions.assertEquals(30, robot.archiveSize());
		
		final Book found = findWithTitleAndAssertBookIsFound(new Title("title9"));
		deleteBookAndAssertDeletion(found.getIsbn());
	}

	private void addTestBooksWithTestIsbnList() throws IOException {
		int id = 1;
		for (String isbn : TestDataUtil.loadTestIsbnList()) {
			robot.addBookToArchive(new PdfBook(isbn, "title" + id, "test/test.pdf"));
			id++;
		}
	}

	private Book findWithTitleAndAssertBookIsFound(Title titleToFind) {
		Book found = robot.findBook(titleToFind );
		Assertions.assertNotNull(found);
		Assertions.assertEquals(found.getTitle(), titleToFind);
		
		final Id foundId = found.getId();
		Assertions.assertNull(foundId);
		Assertions.assertEquals(found.getIsbn().getIsbn(), "9789581054046");
		return found;
	}
	
	private void deleteBookAndAssertDeletion(final Isbn isbn) {
		robot.delete(isbn);
		Assertions.assertNull(robot.findBook(isbn));
	}
	
	@Test
	public final void testAddAudioToArchiveThrowsException() {
		SoundFileNotFoundException e = 
				Assertions.assertThrows(SoundFileNotFoundException.class, () -> {
			robot.addBookToArchive(new AudioBook("9789581054046", "title", ""));
		});
		Assertions.assertEquals("file not found", e.getMessage());
	}
	
	@Test
	void testAddAudioBook() throws Exception {
		addTestAudioBooksWithTestIsbnList();
		Isbn isbnToFind = new Isbn("978-1-56581-231-4");
		Book found = robot.findBook(isbnToFind);
		assertBookIsFound(isbnToFind, found);
	}
	
	private void addTestAudioBooksWithTestIsbnList() throws IOException {
		int id = 1;
		for (String isbn : TestDataUtil.loadTestIsbnList()) {
			robot.addBookToArchive(new AudioBook(isbn, "title" + id, "test/test.pdf"));
			id++;
		}
	}
}
