package fi.my.pkg;

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
		this.book = new PdfBook(100, "978-3-16-148410-0", "test.pdf", "title");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	final void testValidate() {
		printCurrentDir();
		new PdfBook(100, "978-3-16-148410-0", "test.pdf", "title");
	}
	
	@Test
	final void testValidateThrowsExceptionIfFileIsNotFound() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new PdfBook(100, "978-3-16-148410-0", "testEiOle.pdf", "title");
		});
	}
	
	@Test
	final void testValidateThrowsExceptionIfIsbnIsInvalid() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new PdfBook(100, "978-3-16-148410-", "test.pdf", "title");
		});
	}

	private void printCurrentDir() {
		File f = new File(".");
		System.out.println("getAbsolutePath " + f.getAbsolutePath());
	}

	@Test
	final void testCreateDocument() {
		Document d = book.createDocument();
		Assertions.assertEquals(d.get("pdfilename"), "test.pdf");
	}

	@Test
	final void testGetPdfFile() {
		File file = book.getPdfFile();
		Assertions.assertTrue(file.exists());
	}

}
