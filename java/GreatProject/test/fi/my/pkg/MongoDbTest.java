package fi.my.pkg;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MongoDbTest {
	
	private Storage storage;
		
	@BeforeEach
	public void setUp() throws Exception {
		storage = new Storage("mongodb://localhost:27017/?retryWrites=false", "testdb");
		deleteAll();
	}
	
	private void deleteAll() {
		List<Item> books = storage.selectAll();
		for (Item item : books) {
			storage.delete(item.getId());
		}
	}

	@AfterEach
	public void tearDown() throws Exception {
		storage.disconnect();
	}
	
	@Test
	public void testAddOrUpdate() {
		Id id = new Id(3);
		storage.addOrUpdate(new ClassicBook(id, "978-3-16-148410-0"));
		List<Item> books = storage.selectAll();
		assertNotNull(books);
		assertEquals(1, books.size());
		assertEquals(id, books.get(0).getId());
	}
	
	@Test
	void testDelete() throws Exception {
		Id id = new Id(666);
		storage.addOrUpdate(new ClassicBook(id, "978-3-16-148410-0"));
		List<Item> books = storage.selectAll();
		assertEquals(1, books.size());
		storage.delete(id);
		books = storage.selectAll();
		assertEquals(0, books.size());
	}
	
	@Test
	void testSelectAll() throws Exception {
		add500Books();
		assertEquals(500, storage.selectAll().size());
	}

	private void add500Books() {
		for (int i = 0; i < 500; i++) {
			storage.addOrUpdate(new ClassicBook(i, "978-3-16-148410-0"));
		}
	}
	
}
