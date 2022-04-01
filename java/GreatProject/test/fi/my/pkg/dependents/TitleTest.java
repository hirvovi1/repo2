package fi.my.pkg.dependents;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TitleTest {

	private static final String VALID_TITLE = "The Gods of Mars";

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
	final void testInvalidTitle() {
		IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Title("");
		});
		Assertions.assertEquals("invalid title", e.getMessage());
	}

	@Test
	void testNullTitle() throws Exception {
		IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Title(null);
		});
		Assertions.assertEquals("invalid title", e.getMessage());
	}

	@Test
	void testValidTitle() throws Exception {
		Title t = new Title(VALID_TITLE);
		Assertions.assertEquals(new Title(VALID_TITLE), t);
		Assertions.assertEquals(new Title(VALID_TITLE).hashCode(), t.hashCode());
		Assertions.assertEquals(VALID_TITLE, t.getTitle());
	}
}
