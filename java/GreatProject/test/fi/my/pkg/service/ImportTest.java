package fi.my.pkg.service;

import java.util.List;
import java.util.Optional;

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

	@Test
	final void testPdfImportCreatesPdfBook() throws Exception {
		List<Book> books = new Import().importPdfBooks();
		Assertions.assertEquals(31, books.size());
		Optional<Book> book = books.stream().filter(b -> b.getIsbn().getIsbn().equals("9789894808169")).findFirst();
		Assertions.assertTrue(book.isPresent());
		Book plainBook = book.get();
		Assertions.assertTrue(plainBook instanceof PdfBook);
		assertPdfBookFieldsMatch((PdfBook) plainBook);
	}

	private void assertPdfBookFieldsMatch(PdfBook book) {
		Assertions.assertEquals(book.getIsbn().getIsbn(), "9789894808169");
		Assertions.assertEquals(book.getPdfFile().getName(), "9789894808169.pdf");
	}
	
	@Test
	void testAudioImportCreatesAudioBook() throws Exception {
		List<Book> books = new Import().importAudioBooks();
		Assertions.assertEquals(1, books.size());
		assertAudioBookFieldsMatch((AudioBook) getFirstBook(books));
	}

	private Book getFirstBook(List<Book> books) {
		return books.get(0);
	}

	private void assertAudioBookFieldsMatch(AudioBook pbook) {
		Assertions.assertEquals(pbook.getIsbn().getIsbn(), "978-3-16-148410-0");
		Assertions.assertEquals(pbook.getSoundFile().getName(), "978-3-16-148410-0.mp3");
	}

}
