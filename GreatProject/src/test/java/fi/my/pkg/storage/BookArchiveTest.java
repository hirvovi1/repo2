package fi.my.pkg.storage;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import fi.my.pkg.dependents.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fi.my.pkg.TestDataUtil;

@ExtendWith(MockitoExtension.class)
class BookArchiveTest {

	@Mock
	Storage mockStorage;
	private BookArchive archive;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		this.archive = new BookArchive(mockStorage);
		archive.addAll(TestDataUtil.createPdfBooksFromIsbnList());
		Assertions.assertEquals(30, archive.size());
	}

	@Test
	final void testSaveAll() {
		archive.saveChanges();
		verify(mockStorage, times(30)).addOrUpdate(ArgumentMatchers.any(Book.class));
	}

	@Test
	final void testFindBookWithIsbn() {
		Book book = archive.find(new Isbn("978-1-56581-231-4"));
		Assertions.assertNotNull(book);
	}

	@Test
	final void testFindBookWithUnknownValidIsbnReturnsNull() {
		Book book = archive.find(new Isbn("951-98548-9-4"));
		Assertions.assertNull(book);
	}

	@Test
	final void testPushAndPop() throws PdfFileNotFoundException {
		Assertions.assertEquals(30, archive.size());
		final PdfBook book = new PdfBook("978-1-56581-231-4", "title", Fixture.PATH + "test.pdf");
		archive.push(book);
		Assertions.assertEquals(31, archive.size());
		Book book2 = archive.pop();
		Assertions.assertEquals(book, book2);
		Assertions.assertEquals(30, archive.size());
	}

	@Test
	final void testFindId() throws PdfFileNotFoundException {
		final PdfBook book = new PdfBook(2222, "978-1-56581-231-4", "title", Fixture.PATH + "test.pdf");
		archive.push(book);
		final Book foundBook = archive.find(new Id(2222));
		Assertions.assertEquals(book, foundBook);
	}

	@Test
	final void testSize() {
		Assertions.assertEquals(30, archive.size());
	}

	@Test
	final void testFindTitle() {
		final Book foundBook = archive.find(new Title("title10"));
		Assertions.assertNotNull(foundBook);
	}

	@Test
	final void testDeleteWithIsbn() {
		final Isbn isbn = new Isbn("979-8-02130-244-9");
		final Book foundBook = archive.find(isbn);
		Assertions.assertNotNull(foundBook);
		Assertions.assertEquals(isbn, foundBook.getIsbn());
		archive.delete(isbn);
		Assertions.assertNull(archive.find(isbn));
	}

	@Test
	void testDeletionIsForwardedToStorage() throws Exception {
		final Book foundBook = archive.find(new Title("title10"));
		archive.delete(foundBook);
		archive.saveChanges();
		verify(mockStorage).delete(ArgumentMatchers.eq(foundBook.getId()));
		verify(mockStorage, times(29)).addOrUpdate(ArgumentMatchers.any(Book.class));
	}

}
