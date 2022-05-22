package fi.my.pkg.dependents;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	private Book book;

	@BeforeEach
	void setUp() throws Exception {
		this.book = new Book(777, "978-952-264-186-1", "The Warlord of Mars");
	}

	@Test
	final void testHashCode() {
		Book book2 = new Book(777, "978-952-264-186-1", "The Warlord of Mars");
		Assertions.assertEquals(book.hashCode(), book2.hashCode());
		book2 = new Book(777, "978-952-264-186-1", "marsin sotajätti");
		Assertions.assertNotEquals(book.hashCode(), book2.hashCode());
	}

	@Test
	final void testGetIsbn() {
		Book book2 = new Book(888, "978-952-264-186-1", "The Warlord of Mars");
		Assertions.assertEquals(book.getIsbn(), book2.getIsbn());
	}

	@Test
	final void testGetId() {
		Book book2 = new Book(777, "978-952-264-186-1", "The Warlord of Mars");
		Assertions.assertEquals(book.getId(), book2.getId());
		book2 = new Book(778, "978-952-264-186-1", "The Warlord of Mars");
		Assertions.assertNotEquals(book.getId(), book2.getId());
	}

	@Test
	final void testGetTitle() {
		Assertions.assertEquals("The Warlord of Mars", book.getTitle().getTitle());
	}

	@Test
	final void testEqualsObject() {
		Book book2 = new Book(777, "978-952-264-186-1", "The Warlord of Mars");
		Assertions.assertEquals(book, book2);
		book2 = new Book("978-952-264-186-1", "marsin sotajätti");
		Assertions.assertNotEquals(book, book2);
	}

	@Test
	void testValidations() throws Exception {
		String msg = verifyException(1, "", "title");
		assertMessage("isbn was blank string", msg);

		msg = verifyException(1, null, "title");
		assertMessage("isbn was null", msg);

		msg = verifyException(1, "abc", "title");
		assertMessage("invalid isbn abc", msg);

		msg = verifyException(1, "978-952-264-186-1", null);
		assertMessage("title was null", msg);

		msg = verifyException(0, "978-952-264-186-1", "title");
		assertMessage("invalid id 0", msg);
	}

	private String verifyException(int id, String isbn, String title) {
		Throwable e = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Book(id, isbn, title);
		});
		return e.getMessage();
	}

	private void assertMessage(String expected, String message) {
		Assertions.assertEquals(expected, message);
	}

}
