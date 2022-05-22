package fi.my.pkg.dependents;

import java.io.File;

import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class PdfBookTest {
	public static final String FILE_NAME = Fixture.PATH + "test.pdf";
	private PdfBook book;
	
	@BeforeEach
	void setUp() throws Exception {
		printCurrentDir();
		this.book = new PdfBook(1, "978-3-16-148410-0", "title", FILE_NAME);
	}

	@Test
	void testValidate() throws PdfFileNotFoundException {
		new PdfBook("978-3-16-148410-0", "title", FILE_NAME);
	}
	
	@Test
	public final void testValidateThrowsExceptionIfFileIsNotFound() {
		Assertions.assertThrows(PdfFileNotFoundException.class, () -> {
			new PdfBook("978-3-16-148410-0", "title", "testEiOle.pdf");
		});
	}
	
	@Test
	void testValidateThrowsExceptionIfIsbnIsInvalid() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new PdfBook("978-3-16-148410-", "title", "test" + File.separator + "test.pdf");
		});
	}

	private void printCurrentDir() {
		File f = new File("");
		System.out.println("getAbsolutePath " + f.getAbsolutePath());
	}

	@Test
	void testCreateDocument() {
		Document d = book.createDocument();
		Assertions.assertEquals(d.get("pdfilename"), Fixture.PATH + "test.pdf");
	}

	@Test
	void testGetPdfFile() {
		File file = book.getPdfFile();
		assertTrue(file.exists());
	}

}
