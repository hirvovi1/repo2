package fi.my.pkg.service.util;

import fi.my.pkg.dependents.Fixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BisonTest {

	private static Bison bison;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		bison = new Bison(Fixture.IMPORT_PATH);
	}

	@Test
	void testTitle() throws Exception {
		String title = bison.getTitle("9792149925986");
		Assertions.assertEquals(title, "The Torrents of Spring");
	}
	
}
