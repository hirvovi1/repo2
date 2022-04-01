package fi.my.pkg.service;

import static org.mockito.Mockito.times;
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

	private BookService service;

	@BeforeEach
	public void setUp() throws Exception {
		service = new BookServiceImpl(mockStorage);
		addTestBooksWithTestIsbnList();
	}

	@Test
	public final void testAddNullBookToArchive() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			service.addBookToArchive(null);
		});
	}

	@Test
	public final void testSaveArchive() {
		ClassicBook b = new ClassicBook("978-1-56581-231-4", "Crime and punishment");
		TestDataUtil.createPages(b.getPages());
		service.addBookToArchive(b);
		service.saveArchive();
		verify(mockStorage).addOrUpdate(ArgumentMatchers.eq(b));
	}

	@Test
	void testFindABookFromArchiveWithIsbn() throws Exception {
		Isbn isbnToFind = new Isbn("978-1-56581-231-4");
		Book found = service.findBook(isbnToFind);
		assertBookIsFound(isbnToFind, found);
	}

	private void assertBookIsFound(Isbn isbnToFind, Book found) {
		Assertions.assertNotNull(found);
		Assertions.assertEquals(found.getIsbn(), isbnToFind);
	}
	
	@Test
	void testFindABookFromArchiveWithTitle() throws Exception {
		Assertions.assertEquals(30, service.archiveSize());
		
		final Book found = findWithTitleAndAssertBookIsFound(new Title("title9"));
		deleteBookAndAssertDeletion(found.getIsbn());
	}

	private void addTestBooksWithTestIsbnList() throws IOException {
		int id = 1;
		for (String isbn : TestDataUtil.loadTestIsbnList()) {
			service.addBookToArchive(new PdfBook(id, isbn, "title" + id, "test/test.pdf"));
			id++;
		}
		Assertions.assertEquals(30, service.archiveSize());
	}

	private Book findWithTitleAndAssertBookIsFound(Title titleToFind) {
		Book found = service.findBook(titleToFind );
		Assertions.assertNotNull(found);
		Assertions.assertEquals(found.getTitle(), titleToFind);
		
		final Id foundId = found.getId();
		Assertions.assertEquals(found.getIsbn().getIsbn(), "9789581054046");
		return found;
	}
	
	@Test
	void testFindWithIdFindsBook() throws Exception {
		Book found = service.findBook(new Id(7));
		Assertions.assertNotNull(found);
		Assertions.assertEquals(7, found.getId().asInt());
		Assertions.assertEquals("title7", found.getTitle().getTitle());
	}

	private void deleteBookAndAssertDeletion(final Isbn isbn) {
		service.delete(isbn);
		Assertions.assertNull(service.findBook(isbn));
	}
	
	@Test
	public final void testAddAudioToArchiveThrowsException() {
		SoundFileNotFoundException e = 
				Assertions.assertThrows(SoundFileNotFoundException.class, () -> {
			service.addBookToArchive(new AudioBook("9789581054046", "title", ""));
		});
		Assertions.assertEquals("file not found", e.getMessage());
	}
	
	@Test
	void testAddAudioBook() throws Exception {
		addTestAudioBooksWithTestIsbnList();
		Isbn isbnToFind = new Isbn("978-1-56581-231-4");
		Book found = service.findBook(isbnToFind);
		assertBookIsFound(isbnToFind, found);
	}
	
	private void addTestAudioBooksWithTestIsbnList() throws IOException {
		int id = 1;
		for (String isbn : TestDataUtil.loadTestIsbnList()) {
			service.addBookToArchive(new AudioBook(isbn, "title" + id, "test/test.pdf"));
			id++;
		}
	}

	@Test
	void testImport() throws Exception {
		service = new BookServiceImpl(mockStorage);
		service.importBooks();
		verify(mockStorage, times(32)).addOrUpdate(ArgumentMatchers.any(Book.class));
		Assertions.assertEquals(32, service.archiveSize());
	}
}
