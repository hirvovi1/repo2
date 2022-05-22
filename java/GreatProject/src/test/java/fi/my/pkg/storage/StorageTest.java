package fi.my.pkg.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import fi.my.pkg.dependents.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fi.my.pkg.TestDataUtil;

public class StorageTest {

	private static Storage storage;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		storage = new Storage(createConnectionString(), "testdb");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		storage.disconnect();
	}
	
	@BeforeEach
	public void setUp() throws Exception {
		storage.deleteAll();
		Assertions.assertTrue(storage.selectClassicBooks().isEmpty());
		Assertions.assertTrue(storage.selectPdfBooks().isEmpty());
		Assertions.assertTrue(storage.selectAudioBooks().isEmpty());
	}

	@AfterEach
	public void tearDown() throws Exception {
	}
	
	private static String createConnectionString() {
		String connectionString = 
				"mongodb+srv://user:8767224Leskinen@cluster0.ep9st.mongodb.net/myFirstDatabase?"
				+ "retryWrites=true&w=majority";
		try { 
			if (isLocalDatabaseUsed()) {
				connectionString =
					"mongodb://127.0.0.1/myFirstDatabase?retryWrites=false&w=majority";
				System.out.println("LOCAL DB IS USED *** ");
				System.out.println("connectionString: " + connectionString);
			}
		} catch (IOException e) {
		}
		return connectionString;
	}

	private static boolean isLocalDatabaseUsed() throws FileNotFoundException, IOException {
		return "true".equalsIgnoreCase((String) loadProperties().get("LOCAL_DB"));
	}

	private static Properties loadProperties() throws FileNotFoundException, IOException {
		Properties p = new Properties();
		p.load(new FileReader(new File("./test/testconfig.properties")));
		return p;
	}

	@Test
	public void testAddOrUpdate() throws Exception {
		ClassicBook b = new ClassicBook("978-3-16-148410-0", "title");
		TestDataUtil.createPages(b.getPages());
		storage.addOrUpdate(b);
		List<Book> books = storage.selectClassicBooks();
		Assertions.assertNotNull(books);
		Assertions.assertEquals(1, books.size());
	}

	@Test
	void testDelete() throws Exception {
		String isbn = "978-3-16-148410-0";
		storage.addOrUpdate(new ClassicBook(isbn , "title"));
		List<Book> books = storage.selectClassicBooks();
		Assertions.assertEquals(1, books.size());
		storage.delete(new Isbn(isbn));
		books = storage.selectClassicBooks();
		Assertions.assertEquals(0, books.size());
	}

	@Test
	void testSelectAll() throws Exception {
		final int howMany = 80;
		TestDataUtil.addClassicBooks(howMany , storage);
		Assertions.assertEquals(howMany, storage.selectClassicBooks().size());
	}

	@Test
	void testAddPdfBook() throws Exception {
		storage.addOrUpdate(new PdfBook("978-3-16-148410-0", "title", Fixture.PATH + "test.pdf"));
		List<Book> books = storage.selectPdfBooks();
		Assertions.assertEquals(1, books.size());
		Isbn isbn = books.get(0).getIsbn();
		Assertions.assertEquals("978-3-16-148410-0", isbn.getIsbn());
		storage.delete(isbn);
		books = storage.selectClassicBooks();
		Assertions.assertEquals(0, books.size());
		books = storage.selectPdfBooks();
		Assertions.assertEquals(0, books.size());
	}
	
	@Test
	void testAdditions() throws Exception {
		List<String> isbnList = TestDataUtil.loadTestIsbnList();
		saveBooks(isbnList);
		List<Book> books = storage.selectClassicBooks();
		for (Book book : books) {
			System.out.println(book.toString());
			Assertions.assertNotNull(book.getId());
		}
	}

	private void saveBooks(List<String> isbnList) {
		for (String isbn : isbnList) {
			ClassicBook b = new ClassicBook(isbn, "title");
			TestDataUtil.addTestPages(b.getPages());
			storage.addOrUpdate(b);
			System.out.println("saved: " + b.toString());
		}
	}
	
	@Test
	void testSelectionFindsCorrectTypes() throws Exception {
		TestDataUtil.addClassicBooks(3, storage);
		TestDataUtil.addPdfBooks(5, storage);
		TestDataUtil.addAudioBooks(7, storage);
		
		Assertions.assertEquals(3, storage.selectClassicBooks().size());
		Assertions.assertEquals(5, storage.selectPdfBooks().size());
		Assertions.assertEquals(7, storage.selectAudioBooks().size());
	}
	
	@Test
	void testloadId() throws Exception {
		IdUtil.INSTANCE.set(storage);
		int id = IdUtil.INSTANCE.next();
		Assertions.assertEquals(1, id);
		for (int i = 2; i <= 180; i++) {
			id = IdUtil.INSTANCE.next();
			Assertions.assertEquals(i, id);
		}
		IdUtil.INSTANCE.reset();
		id = IdUtil.INSTANCE.next();
		Assertions.assertEquals(201, id);
	}
}
