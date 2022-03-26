package fi.my.pkg.dependents;

import java.io.File;

import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PdfBookTest {

	PdfBook book;
	
	@BeforeEach
	void setUp() throws Exception {
		this.book = new PdfBook(1, "978-3-16-148410-0", "title", "test/test.pdf");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testValidate() throws PdfFileNotFoundException {
		printCurrentDir();
		new PdfBook("978-3-16-148410-0", "title", "test/test.pdf");
	}
	
	@Test
	void testValidateThrowsExceptionIfFileIsNotFound() {
		Assertions.assertThrows(PdfFileNotFoundException.class, () -> {
			new PdfBook("978-3-16-148410-0", "title", "testEiOle.pdf");
		});
	}
	
	@Test
	void testValidateThrowsExceptionIfIsbnIsInvalid() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new PdfBook("978-3-16-148410-", "title", "test/test.pdf");
		});
	}

	private void printCurrentDir() {
		File f = new File(".");
		System.out.println("getAbsolutePath " + f.getAbsolutePath());
	}

	@Test
	void testCreateDocument() {
		Document d = book.createDocument();
		Assertions.assertEquals(d.get("pdfilename"), "test/test.pdf");
	}

	@Test
	void testGetPdfFile() {
		File file = book.getPdfFile();
		Assertions.assertTrue(file.exists());
	}

}
