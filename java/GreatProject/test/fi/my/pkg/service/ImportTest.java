package fi.my.pkg.service;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fi.my.pkg.dependents.AudioBook;
import fi.my.pkg.dependents.Book;
import fi.my.pkg.dependents.PdfBook;

class ImportTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	final void testPdfImportCreatesPdfBook() throws Exception {
		List<Book> books = new Import().importPdfBooks();
		Assertions.assertEquals(1, books.size());
		Book book = books.get(0);
		Assertions.assertTrue(book instanceof PdfBook);
		assertPdfBookFieldsMatch((PdfBook) book);
	}

	private void assertPdfBookFieldsMatch(PdfBook pbook) {
		Assertions.assertEquals(pbook.getIsbn().getIsbn(), "978-3-16-148410-0");
		Assertions.assertEquals(pbook.getPdfFile().getName(), "978-3-16-148410-0.pdf");
	}
	
	@Test
	void testAudioImportCreatesAudioBook() throws Exception {
		List<Book> books = new Import().importAudioBooks();
		Assertions.assertEquals(1, books.size());
		Book book = books.get(0);
		assertAudioBookFieldsMatch((AudioBook) book);
	}
	
	private void assertAudioBookFieldsMatch(AudioBook pbook) {
		Assertions.assertEquals(pbook.getIsbn().getIsbn(), "978-3-16-148410-0");
		Assertions.assertEquals(pbook.getSoundFile().getName(), "978-3-16-148410-0.mp3");
	}

}
