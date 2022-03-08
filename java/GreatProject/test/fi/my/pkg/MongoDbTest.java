package fi.my.pkg;

import java.util.List;

import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Ignore
public class MongoDbTest {

	private Storage storage;

	@BeforeEach
	public void setUp() throws Exception {
		storage = new Storage("mongodb://localhost:27017/?retryWrites=false", "testdb");
		deleteAll();
	}

	private void deleteAll() {
		List<Book> books = storage.selectClassicBooks();
		for (Book item : books) {
			storage.delete(item.getId());
		}
		books = storage.selectPdfBooks();
		for (Book item : books) {
			storage.delete(item.getId());
		}
	}

	@AfterEach
	public void tearDown() throws Exception {
		storage.disconnect();
	}

	@Test
	public void testAddOrUpdate() {
		int id = 3;
		ClassicBook b = new ClassicBook(id, "978-3-16-148410-0");
		TestDataUtil.createPages(b.getPages());
		storage.addOrUpdate(b);
		List<Book> books = storage.selectClassicBooks();
		Assertions.assertNotNull(books);
		Assertions.assertEquals(1, books.size());
		Assertions.assertEquals(id, books.get(0).getId().asLong());
	}

	@Test
	void testDelete() throws Exception {
		Id id = new Id(666);
		storage.addOrUpdate(new ClassicBook(id, "978-3-16-148410-0"));
		List<Book> books = storage.selectClassicBooks();
		Assertions.assertEquals(1, books.size());
		storage.delete(id);
		books = storage.selectClassicBooks();
		Assertions.assertEquals(0, books.size());
	}

	@Test
	void testSelectAll() throws Exception {
		TestDataUtil.add500Books(storage);
		Assertions.assertEquals(500, storage.selectClassicBooks().size());
	}

	@Test
	void testAddPdfBook() throws Exception {
		Id id = new Id(666);
		storage.addOrUpdate(new PdfBook(id, "978-3-16-148410-0", "test.pdf"));
		List<Book> books = storage.selectPdfBooks();
		Assertions.assertEquals(1, books.size());
		Assertions.assertEquals("978-3-16-148410-0", books.get(0).getIsbn());
		storage.delete(id);
		books = storage.selectClassicBooks();
		Assertions.assertEquals(0, books.size());
		books = storage.selectPdfBooks();
		Assertions.assertEquals(0, books.size());
	}
	
	@Test
	void testAdditions() throws Exception {
		List<String> isbnList = TestDataUtil.loadTestIsbnList();
		saveBooks(isbnList);
		saveBooks(isbnList);
	}

	private void saveBooks(List<String> isbnList) {
		int id = 1;
		for (String isbn : isbnList) {
			ClassicBook b = new ClassicBook(id++, isbn, "title");
			TestDataUtil.addTestPages(b.getPages());
			storage.addOrUpdate(b);
			System.out.println("saved: " + b.toString());
		}
	}
}
