package fi.my.pkg.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BisonTest {

	private static Bison bison;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		bison = new Bison();
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
	void testTitle() throws Exception {
		String title = bison.getTitle("9792149925986");
		Assertions.assertEquals(title, "The Torrents of Spring");
	}
	
}
