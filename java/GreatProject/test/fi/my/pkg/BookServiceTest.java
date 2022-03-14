package fi.my.pkg;

import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fi.my.pkg.dependents.AudioBook;
import fi.my.pkg.dependents.Book;
import fi.my.pkg.dependents.ClassicBook;
import fi.my.pkg.dependents.Id;
import fi.my.pkg.dependents.Isbn;
import fi.my.pkg.dependents.PdfBook;
import fi.my.pkg.dependents.Title;
import fi.my.pkg.service.BookService;
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
		robot.addBookToArchive(new ClassicBook(1, "978-3-16-148410-0", "title"));
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
		robot.addBookToArchive(new ClassicBook(1, "978-3-16-148410-0", "title"));
		Book b = new ClassicBook(2, "978-3-16-148410-0", "title");
		robot.addBookToArchive(b);
		Assertions.assertEquals(b, robot.getNewestBookFromArchive());
	}

	@Test
	public final void testSaveArchive() {
		ClassicBook b = new ClassicBook(1, "978-1-56581-231-4", "Crime and punishment");
		TestDataUtil.createPages(b.getPages());
		robot.addBookToArchive(b);
		robot.saveArchive();
		verify(mockStorage).addOrUpdate(ArgumentMatchers.eq(b));
	}

	@Test
	void testFindABookFromArchiveWithIsbn() throws Exception {
		loadTestPdfBooks();
		Isbn isbnToFind = new Isbn("978-1-56581-231-4");
		final Id idToFind = new Id(23);
		Book found = robot.findBook(isbnToFind);
		assertBookIsFound(isbnToFind, idToFind, found);
	}

	private void loadTestPdfBooks() throws IOException {
		int id = 1;
		for (String isbn : TestDataUtil.loadTestIsbnList()) {
			robot.addBookToArchive(new PdfBook(id++, isbn, "test.pdf", "title"));
		}
		Assertions.assertEquals(30, robot.archiveSize());
	}

	private void assertBookIsFound(Isbn isbnToFind, final Id idToFind, Book found) {
		Assertions.assertNotNull(found);
		Assertions.assertEquals(found.getIsbn(), isbnToFind);
		Assertions.assertEquals(found.getId(), idToFind);
	}
	
	@Test
	void testFindABookFromArchiveWithTitle() throws Exception {
		addTestBooksWithTestIsbnList();
		Assertions.assertEquals(30, robot.archiveSize());
		
		final Id foundId = findWithTitleAndAssertBookIsFound(new Title("title9"));
		deleteBookAndAssertDeletion(foundId);
	}

	private void addTestBooksWithTestIsbnList() throws IOException {
		int id = 1;
		for (String isbn : TestDataUtil.loadTestIsbnList()) {
			robot.addBookToArchive(new PdfBook(id, isbn, "test.pdf", "title" + id));
			id++;
		}
	}

	private Id findWithTitleAndAssertBookIsFound(Title titleToFind) {
		Book found = robot.findBook(titleToFind );
		Assertions.assertNotNull(found);
		Assertions.assertEquals(found.getTitle(), titleToFind);
		
		final Id foundId = found.getId();
		Assertions.assertEquals(foundId, new Id(9));
		Assertions.assertEquals(found.getIsbn().getIsbn(), "9789581054046");
		return foundId;
	}
	
	private void deleteBookAndAssertDeletion(final Id foundId) {
		robot.delete(foundId);
		Assertions.assertNull(robot.findBook(foundId));
	}
	
	@Test
	public final void testAddAudioToArchiveThrowsException() {
		IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			robot.addBookToArchive(new AudioBook(1, "9789581054046", "title", ""));
		});
		Assertions.assertEquals("file not found", e.getMessage());
	}
	
	@Test
	void testAddAudioBook() throws Exception {
		addTestAudioBooksWithTestIsbnList();
		Isbn isbnToFind = new Isbn("978-1-56581-231-4");
		final Id idToFind = new Id(23);
		Book found = robot.findBook(isbnToFind);
		assertBookIsFound(isbnToFind, idToFind, found);
	}
	
	private void addTestAudioBooksWithTestIsbnList() throws IOException {
		int id = 1;
		for (String isbn : TestDataUtil.loadTestIsbnList()) {
			robot.addBookToArchive(new AudioBook(id, isbn, "title" + id, "test.pdf"));
			id++;
		}
	}
}